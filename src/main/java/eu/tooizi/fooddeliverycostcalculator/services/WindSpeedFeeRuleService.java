package eu.tooizi.fooddeliverycostcalculator.services;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WindSpeedFeeRule;
import eu.tooizi.fooddeliverycostcalculator.domain.requests.WindSpeedFeeRuleRequest;
import eu.tooizi.fooddeliverycostcalculator.repositories.RegionRepository;
import eu.tooizi.fooddeliverycostcalculator.repositories.VehicleTypeRepository;
import eu.tooizi.fooddeliverycostcalculator.repositories.WindSpeedFeeRuleRepository;
import eu.tooizi.fooddeliverycostcalculator.services.exceptions.RegionNotFoundException;
import eu.tooizi.fooddeliverycostcalculator.services.exceptions.VehicleNotFoundException;
import eu.tooizi.fooddeliverycostcalculator.services.exceptions.WindSpeedFeeRuleOverlapsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
public class WindSpeedFeeRuleService
{
    private final WindSpeedFeeRuleRepository windSpeedFeeRuleRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final RegionRepository regionRepository;

    public WindSpeedFeeRuleService(@Autowired WindSpeedFeeRuleRepository windSpeedFeeRuleRepository,
                                   @Autowired VehicleTypeRepository vehicleTypeRepository,
                                   @Autowired RegionRepository regionRepository)
    {
        this.windSpeedFeeRuleRepository = windSpeedFeeRuleRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.regionRepository = regionRepository;
    }

    public Collection<WindSpeedFeeRule> getWindSpeedFeeRules()
    {
        return StreamSupport.stream(windSpeedFeeRuleRepository.findAll().spliterator(), false)
                .toList();
    }

    public void addWindSpeedFeeRule(WindSpeedFeeRuleRequest windSpeedFeeRuleRequest)
            throws VehicleNotFoundException, RegionNotFoundException
    {
        WindSpeedFeeRule windSpeedFeeRule = mapRequestToModel(windSpeedFeeRuleRequest);
        addWindSpeedFeeRule(windSpeedFeeRule);
    }


    public void addWindSpeedFeeRule(WindSpeedFeeRule windSpeedFeeRule)
            throws WindSpeedFeeRuleOverlapsException
    {
        RangeFeeRuleOverlapChecker<WindSpeedFeeRule> overlapChecker = new RangeFeeRuleOverlapChecker<>();

        overlapChecker.findOverlap(windSpeedFeeRule, windSpeedFeeRuleRepository.findAll())
                .ifPresent(overlappingFeeRule -> {
                    throw new WindSpeedFeeRuleOverlapsException("Added rule overlaps with and existing one.",
                            overlappingFeeRule);
                });

        windSpeedFeeRuleRepository.save(windSpeedFeeRule);
    }

    public void deleteWindSpeedFeeRuleById(UUID id)
    {
        windSpeedFeeRuleRepository.deleteById(id);
    }

    private WindSpeedFeeRule mapRequestToModel(WindSpeedFeeRuleRequest windSpeedFeeRuleRequest)
            throws VehicleNotFoundException, RegionNotFoundException
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
