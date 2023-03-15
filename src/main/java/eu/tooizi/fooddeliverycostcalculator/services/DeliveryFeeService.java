package eu.tooizi.fooddeliverycostcalculator.services;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.*;
import eu.tooizi.fooddeliverycostcalculator.domain.responses.DeliveryFeeResponseIntermediate;
import eu.tooizi.fooddeliverycostcalculator.domain.responses.DeliveryFeeResponseIntermediateFactory;
import eu.tooizi.fooddeliverycostcalculator.repositories.*;
import eu.tooizi.fooddeliverycostcalculator.services.exceptions.UndeliverableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for calculating delivery fee.
 * Main logic is here.
 */
@Service
public class DeliveryFeeService
{

    /**
     * Message that should be returned on undeliverable request.
     */
    public static final String UNDELIVERABLE_ERROR_MESSAGE = "Usage of selected vehicle type is forbidden";
    private final RegionRepository regionRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final WeatherConditionsRepository weatherConditionsRepository;
    private final PhenomenonCategoryRepository phenomenonCategoryRepository;
    private final RegionalBaseFeeRepository regionalBaseFeeRepository;
    private final AirTemperatureFeeRuleRepository airTemperatureFeeRuleRepository;
    private final WindSpeedFeeRuleRepository windSpeedFeeRuleRepository;
    private final WeatherPhenomenonFeeRuleRepository weatherPhenomenonFeeRuleRepository;

    /**
     * Constructor for service.
     * All repositories are autowired.
     *
     * @param regionRepository                   Repository for regions
     * @param vehicleTypeRepository              Repository for vehicle types
     * @param weatherConditionsRepository        Repository for weather conditions
     * @param phenomenonCategoryRepository       Repository for phenomenon categories
     * @param regionalBaseFeeRepository          Repository for regional base fees
     * @param airTemperatureFeeRuleRepository    Repository for air temperature fee rules
     * @param windSpeedFeeRuleRepository         Repository for wind speed fee rules
     * @param weatherPhenomenonFeeRuleRepository Repository for weather phenomenon fee rules
     */
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

    /**
     * Calculates delivery fee for given region and vehicle type.
     *
     * @param region      Name of the region
     * @param vehicleType Name of the vehicle type
     * @return Delivery fee response
     */
    public DeliveryFeeResponseIntermediate getDeliveryFee(String region, String vehicleType)
    {
        DeliveryFeeResponseIntermediateFactory responseFactory = new DeliveryFeeResponseIntermediateFactory();
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

            WeatherConditions latestConditions = weatherConditionsRepository.findFirstByRegionOrderByTimestampDesc(
                            foundRegion
                    )
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
            return responseFactory.failed(e.getStatusCode(), e.getReason());
        }
        return responseFactory.successful(deliveryFee);
    }

    /**
     * Applies fee rule to delivery fee.
     *
     * @param rule Fee rule
     * @return Price difference
     * @throws UndeliverableException If rule prohibits delivery
     */
    double applyFee(FeeRule rule)
    {
        if (!rule.getDeliverable())
        {
            throw new UndeliverableException(
                    HttpStatusCode.valueOf(200),
                    UNDELIVERABLE_ERROR_MESSAGE
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
