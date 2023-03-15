package eu.tooizi.fooddeliverycostcalculator.services;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.AirTemperatureFeeRule;
import eu.tooizi.fooddeliverycostcalculator.domain.requests.AirTemperatureFeeRuleRequest;
import eu.tooizi.fooddeliverycostcalculator.repositories.AirTemperatureFeeRuleRepository;
import eu.tooizi.fooddeliverycostcalculator.repositories.RegionRepository;
import eu.tooizi.fooddeliverycostcalculator.repositories.VehicleTypeRepository;
import eu.tooizi.fooddeliverycostcalculator.services.exceptions.AirTemperatureFeeRuleOverlapsException;
import eu.tooizi.fooddeliverycostcalculator.services.exceptions.RegionNotFoundException;
import eu.tooizi.fooddeliverycostcalculator.services.exceptions.VehicleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.StreamSupport;

/**
 * Service for managing air temperature fee rules with CRUD-like methods.
 */
@Service
public class AirTemperatureFeeRuleService
{
    private final AirTemperatureFeeRuleRepository airTemperatureFeeRuleRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final RegionRepository regionRepository;

    /**
     * Constructor.
     * All fields are autowired.
     *
     * @param airTemperatureFeeRuleRepository Repository for air temperature fee rules.
     * @param vehicleTypeRepository           Repository for vehicle types.
     * @param regionRepository                Repository for regions.
     */
    public AirTemperatureFeeRuleService(
            @Autowired AirTemperatureFeeRuleRepository airTemperatureFeeRuleRepository,
            @Autowired VehicleTypeRepository vehicleTypeRepository,
            @Autowired RegionRepository regionRepository)
    {
        this.airTemperatureFeeRuleRepository = airTemperatureFeeRuleRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.regionRepository = regionRepository;
    }

    /**
     * Fetches all air temperature fee rules from the database.
     *
     * @return Collection of air temperature fee rules.
     */
    public Collection<AirTemperatureFeeRule> getAirTemperatureFeeRules()
    {
        return StreamSupport.stream(airTemperatureFeeRuleRepository.findAll().spliterator(), false)
                .toList();
    }

    /**
     * Creates a new air temperature fee rule and saves it to the database.
     *
     * @param airTemperatureFeeRuleRequest Request containing the data for the new air temperature fee rule.
     * @throws VehicleNotFoundException If the specified vehicle type id does not exist in the database.
     * @throws RegionNotFoundException  If the specified region id does not exist in the database.
     */
    public void addAirTemperatureFeeRule(AirTemperatureFeeRuleRequest airTemperatureFeeRuleRequest)
    {
        AirTemperatureFeeRule airTemperatureFeeRule = mapRequestToModel(airTemperatureFeeRuleRequest);

        addAirTemperatureFeeRule(airTemperatureFeeRule);
    }

    /**
     * Saves an air temperature fee rule to the database.
     *
     * @param airTemperatureFeeRule Air temperature fee rule to save.
     * @throws AirTemperatureFeeRuleOverlapsException If the air temperature fee rule overlaps with an existing one.
     */
    public void addAirTemperatureFeeRule(AirTemperatureFeeRule airTemperatureFeeRule)
    {

        RangeFeeRuleOverlapChecker<AirTemperatureFeeRule> overlapChecker = new RangeFeeRuleOverlapChecker<>();

        Iterable<AirTemperatureFeeRule> existingRules = airTemperatureFeeRuleRepository.findAll();

        overlapChecker.findOverlap(airTemperatureFeeRule, existingRules)
                .ifPresent(overlappingFeeRule -> {
                    throw new AirTemperatureFeeRuleOverlapsException("Added rule overlaps with and existing one.",
                            overlappingFeeRule);
                });

        airTemperatureFeeRuleRepository.save(airTemperatureFeeRule);
    }

    /**
     * Deletes an air temperature fee rule with the specified id from the database.
     *
     * @param id Id of the air temperature fee rule to delete.
     */
    public void deleteAirTemperatureFeeRuleById(UUID id)
    {
        airTemperatureFeeRuleRepository.deleteById(id);
    }

    /**
     * Maps an air temperature fee rule request to an air temperature fee rule model.
     *
     * @param airTemperatureFeeRuleRequest Request to map.
     * @return Mapped air temperature fee rule model.
     * @throws VehicleNotFoundException If the specified vehicle type id does not exist in the database.
     * @throws RegionNotFoundException  If the specified region id does not exist in the database.
     */
    private AirTemperatureFeeRule mapRequestToModel(AirTemperatureFeeRuleRequest airTemperatureFeeRuleRequest)
    {
        AirTemperatureFeeRule model = new AirTemperatureFeeRule();

        model.setPriceDifference(airTemperatureFeeRuleRequest.getPriceDifference());
        model.setVehicleType(vehicleTypeRepository.findById(airTemperatureFeeRuleRequest.getVehicleTypeId())
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle type not found."))
        );
        model.setRegion(regionRepository.findById(airTemperatureFeeRuleRequest.getRegionId())
                .orElseThrow(() -> new RegionNotFoundException("Region not found."))
        );
        model.setLowerBound(airTemperatureFeeRuleRequest.getLowerBound());
        model.setUpperBound(airTemperatureFeeRuleRequest.getUpperBound());

        return model;
    }
}
