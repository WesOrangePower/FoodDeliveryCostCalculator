package eu.tooizi.fooddeliverycostcalculator.services;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.*;
import eu.tooizi.fooddeliverycostcalculator.domain.responses.DeliveryFeeResponse;
import eu.tooizi.fooddeliverycostcalculator.domain.responses.DeliveryFeeResponseFactory;
import eu.tooizi.fooddeliverycostcalculator.repositories.*;
import eu.tooizi.fooddeliverycostcalculator.services.exceptions.UndeliverableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeliveryFeeService
{
    private final RegionRepository regionRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final WeatherConditionsRepository weatherConditionsRepository;
    private final PhenomenonCategoryRepository phenomenonCategoryRepository;
    private final RegionalBaseFeeRepository regionalBaseFeeRepository;
    private final AirTemperatureFeeRuleRepository airTemperatureFeeRuleRepository;
    private final WindSpeedFeeRuleRepository windSpeedFeeRuleRepository;
    private final WeatherPhenomenonFeeRuleRepository weatherPhenomenonFeeRuleRepository;

    public DeliveryFeeService(@Autowired RegionRepository regionRepository,
                              @Autowired VehicleTypeRepository vehicleTypeRepository,
                              @Autowired WeatherConditionsRepository weatherConditionsRepository,
                              @Autowired PhenomenonCategoryRepository phenomenonCategoryRepository,
                              @Autowired RegionalBaseFeeRepository regionalBaseFeeRepository,
                              @Autowired AirTemperatureFeeRuleRepository airTemperatureFeeRuleRepository,
                              @Autowired WindSpeedFeeRuleRepository windSpeedFeeRuleRepository,
                              @Autowired WeatherPhenomenonFeeRuleRepository weatherPhenomenonFeeRuleRepository)
    {
        this.regionRepository = regionRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.weatherConditionsRepository = weatherConditionsRepository;
        this.phenomenonCategoryRepository = phenomenonCategoryRepository;
        this.regionalBaseFeeRepository = regionalBaseFeeRepository;
        this.airTemperatureFeeRuleRepository = airTemperatureFeeRuleRepository;
        this.windSpeedFeeRuleRepository = windSpeedFeeRuleRepository;
        this.weatherPhenomenonFeeRuleRepository = weatherPhenomenonFeeRuleRepository;
    }

    public DeliveryFeeResponse getDeliveryFee(String region, String vehicleType)
    {
        DeliveryFeeResponseFactory responseFactory = new DeliveryFeeResponseFactory();
        double deliveryFee;
        try
        {
            Region foundRegion = regionRepository.findFirstByName(region)
                    .orElseThrow(
                            () -> new UndeliverableException(HttpStatusCode.valueOf(404), "Region not found")
                    );

            VehicleType foundVehicleType = vehicleTypeRepository.findFirstByName(vehicleType)
                    .orElseThrow(
                            () -> new UndeliverableException(HttpStatusCode.valueOf(404), "VehicleType not found")
                    );

            RegionalBaseFee regionalBaseFee = regionalBaseFeeRepository.findFirstByRegionAndVehicleType(
                            foundRegion,
                            foundVehicleType
                    )
                    .orElseThrow(
                            () -> new UndeliverableException(HttpStatusCode.valueOf(404), "Cannot find base fee for supplied vehicle")
                    );

            WeatherConditions latestConditions = weatherConditionsRepository.findNewestByRegionId(foundRegion.getId())
                    .orElseThrow(
                            () -> new UndeliverableException(HttpStatusCode.valueOf(404), "No data on weather in supplied region")
                    );

            Optional<AirTemperatureFeeRule> airTemperatureFeeRuleOptional = airTemperatureFeeRuleRepository
                    .findFirstByVehicleTypeAndRegionAndBound(foundVehicleType, foundRegion, latestConditions.getTemperatureCelsius());

            Optional<WindSpeedFeeRule> windSpeedFeeRuleOptional = windSpeedFeeRuleRepository
                    .findFirstByVehicleTypeAndRegionAndBound(foundVehicleType, foundRegion, latestConditions.getWindSpeedMps());

            Optional<WeatherPhenomenon> phenomenon = Optional.ofNullable(latestConditions.getWeatherPhenomenon());

            PhenomenonCategory phenomenonCategory = phenomenon.isPresent()
                    ? phenomenon.get().getPhenomenonCategory()
                    : phenomenonCategoryRepository.findDefault();

            Optional<WeatherPhenomenonFeeRule> weatherPhenomenonFeeRule = weatherPhenomenonFeeRuleRepository
                    .findByVehicleTypeAndRegionAndPhenomenonCategory(foundVehicleType, foundRegion, phenomenonCategory);


            deliveryFee = regionalBaseFee.getBaseFee();
            deliveryFee += applyFee(airTemperatureFeeRuleOptional);
            deliveryFee += applyFee(windSpeedFeeRuleOptional);
            deliveryFee += applyFee(weatherPhenomenonFeeRule);
        } catch (UndeliverableException e)
        {
            return responseFactory.failed(e.getStatusCode(), e.getMessage());
        }
        return responseFactory.successful(deliveryFee);
    }

    double applyFee(FeeRule rule) throws UndeliverableException
    {
        if (!rule.getDeliverable())
        {
            throw new UndeliverableException(
                    HttpStatusCode.valueOf(200),
                    "Usage of selected vehicle type is forbidden"
            );
        }
        return rule.getPriceDifference();
    }

    double applyFee(Optional<? extends FeeRule> ruleOptional)
    {
        return ruleOptional
                .map(this::applyFee)
                .orElse(0d);
    }
}
