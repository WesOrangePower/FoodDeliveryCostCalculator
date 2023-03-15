package eu.tooizi.fooddeliverycostcalculator.services;

import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.WindSpeedFeeRule;
import eu.tooizi.fooddeliverycostcalculator.DTOs.exceptions.RegionNotFoundException;
import eu.tooizi.fooddeliverycostcalculator.DTOs.exceptions.VehicleNotFoundException;
import eu.tooizi.fooddeliverycostcalculator.DTOs.exceptions.WindSpeedFeeRuleOverlapsException;
import eu.tooizi.fooddeliverycostcalculator.DTOs.requests.WindSpeedFeeRuleRequest;
import eu.tooizi.fooddeliverycostcalculator.repositories.RegionRepository;
import eu.tooizi.fooddeliverycostcalculator.repositories.VehicleTypeRepository;
import eu.tooizi.fooddeliverycostcalculator.repositories.WindSpeedFeeRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.StreamSupport;

/**
 * Service for managing wind speed fee rules with CRUD-like methods.
 */
@Service
public class WindSpeedFeeRuleService
{
    private final WindSpeedFeeRuleRepository windSpeedFeeRuleRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final RegionRepository regionRepository;

    /**
     * Constructor.
     * All fields are autowired.
     *
     * @param windSpeedFeeRuleRepository Repository for wind speed fee rules.
     * @param vehicleTypeRepository      Repository for vehicle types.
     * @param regionRepository           Repository for regions.
     */
    public WindSpeedFeeRuleService(@Autowired WindSpeedFeeRuleRepository windSpeedFeeRuleRepository,
                                   @Autowired VehicleTypeRepository vehicleTypeRepository,
                                   @Autowired RegionRepository regionRepository)
    {
        this.windSpeedFeeRuleRepository = windSpeedFeeRuleRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.regionRepository = regionRepository;
    }

    /**
     * Fetches all wind speed fee rules from the database.
     *
     * @return Collection of wind speed fee rules.
     */
    public Collection<WindSpeedFeeRule> getWindSpeedFeeRules()
    {
        return StreamSupport.stream(windSpeedFeeRuleRepository.findAll().spliterator(), false)
                .toList();
    }

    /**
     * Creates a new wind speed fee rule and saves it to the database.
     *
     * @param windSpeedFeeRuleRequest Request containing the data for the new wind speed fee rule.
     * @throws VehicleNotFoundException          If the specified vehicle type id does not exist in the database.
     * @throws RegionNotFoundException           If the specified region id does not exist in the database.
     * @throws WindSpeedFeeRuleOverlapsException If wind speed ranges overlap with an existing rule.
     */
    public void addWindSpeedFeeRule(WindSpeedFeeRuleRequest windSpeedFeeRuleRequest)
    {
        WindSpeedFeeRule windSpeedFeeRule = mapRequestToModel(windSpeedFeeRuleRequest);
        addWindSpeedFeeRule(windSpeedFeeRule);
    }


    /**
     * Saves the wind speed fee rule to the database.
     *
     * @param windSpeedFeeRule Wind speed fee rule to save.
     * @throws WindSpeedFeeRuleOverlapsException If wind speed ranges overlap with an existing rule.
     */
    public void addWindSpeedFeeRule(WindSpeedFeeRule windSpeedFeeRule)
    {
        RangeFeeRuleOverlapChecker<WindSpeedFeeRule> overlapChecker = new RangeFeeRuleOverlapChecker<>();

        overlapChecker.findOverlap(windSpeedFeeRule, windSpeedFeeRuleRepository.findAll())
                .ifPresent(overlappingFeeRule -> {
                    throw new WindSpeedFeeRuleOverlapsException("Added rule overlaps with and existing one.",
                            overlappingFeeRule);
                });

        windSpeedFeeRuleRepository.save(windSpeedFeeRule);
    }

    /**
     * Deletes the wind speed fee rule with the specified id from the database.
     *
     * @param id ID of the wind speed fee rule to delete.
     */
    public void deleteWindSpeedFeeRuleById(UUID id)
    {
        windSpeedFeeRuleRepository.deleteById(id);
    }

    /**
     * @param windSpeedFeeRuleRequest Request containing the data for the new wind speed fee rule.
     * @return Wind speed fee rule model.
     * @throws VehicleNotFoundException If the specified vehicle type id does not exist in the database.
     * @throws RegionNotFoundException  If the specified region id does not exist in the database.
     */
    private WindSpeedFeeRule mapRequestToModel(WindSpeedFeeRuleRequest windSpeedFeeRuleRequest)
    {
        WindSpeedFeeRule model = new WindSpeedFeeRule();

        model.setPriceDifference(windSpeedFeeRuleRequest.getPriceDifference());
        model.setVehicleType(vehicleTypeRepository.findById(windSpeedFeeRuleRequest.getVehicleTypeId())
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle type not found."))
        );
        model.setRegion(regionRepository.findById(windSpeedFeeRuleRequest.getRegionId())
                .orElseThrow(() -> new RegionNotFoundException("Region not found."))
        );
        model.setLowerBound(windSpeedFeeRuleRequest.getLowerBound());
        model.setUpperBound(windSpeedFeeRuleRequest.getUpperBound());

        return model;
    }
}
