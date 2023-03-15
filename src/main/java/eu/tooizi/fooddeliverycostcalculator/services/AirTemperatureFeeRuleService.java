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

@Service
public class AirTemperatureFeeRuleService
{
    private final AirTemperatureFeeRuleRepository airTemperatureFeeRuleRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final RegionRepository regionRepository;

    public AirTemperatureFeeRuleService(
            @Autowired AirTemperatureFeeRuleRepository airTemperatureFeeRuleRepository,
            @Autowired VehicleTypeRepository vehicleTypeRepository,
            @Autowired RegionRepository regionRepository)
    {
        this.airTemperatureFeeRuleRepository = airTemperatureFeeRuleRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.regionRepository = regionRepository;
    }

    public Collection<AirTemperatureFeeRule> getAirTemperatureFeeRules()
    {
        return StreamSupport.stream(airTemperatureFeeRuleRepository.findAll().spliterator(), false)
                .toList();
    }

    public void addAirTemperatureFeeRule(AirTemperatureFeeRuleRequest airTemperatureFeeRuleRequest)
            throws VehicleNotFoundException, RegionNotFoundException
    {
        AirTemperatureFeeRule airTemperatureFeeRule = mapRequestToModel(airTemperatureFeeRuleRequest);

        addAirTemperatureFeeRule(airTemperatureFeeRule);
    }

    public void addAirTemperatureFeeRule(AirTemperatureFeeRule airTemperatureFeeRule)
            throws AirTemperatureFeeRuleOverlapsException
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

    public void deleteAirTemperatureFeeRuleById(UUID id)
    {
        airTemperatureFeeRuleRepository.deleteById(id);
    }

    private AirTemperatureFeeRule mapRequestToModel(AirTemperatureFeeRuleRequest airTemperatureFeeRuleRequest)
            throws VehicleNotFoundException, RegionNotFoundException
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
