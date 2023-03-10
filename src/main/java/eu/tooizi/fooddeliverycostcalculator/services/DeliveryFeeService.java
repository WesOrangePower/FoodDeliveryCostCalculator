package eu.tooizi.fooddeliverycostcalculator.services;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.*;
import eu.tooizi.fooddeliverycostcalculator.domain.DeliveryFee;
import eu.tooizi.fooddeliverycostcalculator.domain.feestrategies.AirTemperatureFeeStrategy;
import eu.tooizi.fooddeliverycostcalculator.domain.feestrategies.RegionalBaseFeeStrategy;
import eu.tooizi.fooddeliverycostcalculator.domain.feestrategies.WindSpeedFeeStrategy;
import eu.tooizi.fooddeliverycostcalculator.domain.responses.DeliveryFeeResponse;
import eu.tooizi.fooddeliverycostcalculator.domain.responses.DeliveryFeeResponseFactory;
import eu.tooizi.fooddeliverycostcalculator.domain.responses.PhenomenonCategoryFeeStrategy;
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

    public DeliveryFeeService(@Autowired RegionRepository regionRepository,
                              @Autowired VehicleTypeRepository vehicleTypeRepository,
                              @Autowired WeatherConditionsRepository weatherConditionsRepository,
                              @Autowired PhenomenonCategoryRepository phenomenonCategoryRepository,
                              @Autowired RegionalBaseFeeRepository regionalBaseFeeRepository)
    {
        this.regionRepository = regionRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.weatherConditionsRepository = weatherConditionsRepository;
        this.phenomenonCategoryRepository = phenomenonCategoryRepository;
        this.regionalBaseFeeRepository = regionalBaseFeeRepository;
    }

    public DeliveryFeeResponse getDeliveryFee(String region, String vehicleType)
    {
        DeliveryFeeResponseFactory responseFactory = new DeliveryFeeResponseFactory();
        DeliveryFee deliveryFee = new DeliveryFee(0);
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

            RegionalBaseFee regionalBaseFee = regionalBaseFeeRepository.FindFirstByRegionIdAndVehicleTypeId(
                            foundRegion.getId(),
                            foundVehicleType.getId()
                    )
                    .orElseThrow(
                            () -> new UndeliverableException(HttpStatusCode.valueOf(404), "Cannot find base fee for supplied vehicle")
                    );

            WeatherConditions latestConditions = weatherConditionsRepository.findNewestByRegionId(foundRegion.getId())
                    .orElseThrow(
                            () -> new UndeliverableException(HttpStatusCode.valueOf(404), "No data on weather in supplied region")
                    );

            Optional<WeatherPhenomenon> phenomenon = Optional.ofNullable(latestConditions.getWeatherPhenomenon());

            PhenomenonCategory phenomenonCategory = phenomenon.isPresent()
                                                    ? phenomenon.get().getPhenomenonCategory()
                                                    : phenomenonCategoryRepository.findDefault();


            deliveryFee.applyFee(new RegionalBaseFeeStrategy(regionalBaseFee));
            deliveryFee.applyFee(new PhenomenonCategoryFeeStrategy(phenomenonCategory));
            deliveryFee.applyFee(new AirTemperatureFeeStrategy(latestConditions.getTemperatureCelsius(), vehicleType));
            deliveryFee.applyFee(new WindSpeedFeeStrategy(latestConditions.getWindSpeedMps(), vehicleType));
        } catch (UndeliverableException e)
        {
            return responseFactory.failed(e.getStatusCode(), e.getMessage());
        }
        return responseFactory.successful(deliveryFee.getFee());
    }
}
