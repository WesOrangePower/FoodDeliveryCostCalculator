package eu.tooizi.fooddeliverycostcalculator.services;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WeatherPhenomenonFeeRule;
import eu.tooizi.fooddeliverycostcalculator.domain.requests.WeatherPhenomenonFeeRuleRequest;
import eu.tooizi.fooddeliverycostcalculator.repositories.PhenomenonCategoryRepository;
import eu.tooizi.fooddeliverycostcalculator.repositories.RegionRepository;
import eu.tooizi.fooddeliverycostcalculator.repositories.VehicleTypeRepository;
import eu.tooizi.fooddeliverycostcalculator.repositories.WeatherPhenomenonFeeRuleRepository;
import eu.tooizi.fooddeliverycostcalculator.services.exceptions.PhenomenonCategoryNotFoundException;
import eu.tooizi.fooddeliverycostcalculator.services.exceptions.RegionNotFoundException;
import eu.tooizi.fooddeliverycostcalculator.services.exceptions.VehicleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
public class WeatherPhenomenonFeeRuleService
{
    private final WeatherPhenomenonFeeRuleRepository weatherPhenomenonFeeRuleRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final RegionRepository regionRepository;
    private final PhenomenonCategoryRepository phenomenonCategoryRepository;

    public WeatherPhenomenonFeeRuleService(@Autowired WeatherPhenomenonFeeRuleRepository weatherPhenomenonFeeRuleRepository,
                                           @Autowired VehicleTypeRepository vehicleTypeRepository,
                                           @Autowired RegionRepository regionRepository,
                                           @Autowired PhenomenonCategoryRepository phenomenonCategoryRepository)
    {

        this.weatherPhenomenonFeeRuleRepository = weatherPhenomenonFeeRuleRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.regionRepository = regionRepository;
        this.phenomenonCategoryRepository = phenomenonCategoryRepository;
    }

    public Collection<WeatherPhenomenonFeeRule> getWeatherPhenomenonFeeRules()
    {
        return StreamSupport.stream(weatherPhenomenonFeeRuleRepository.findAll().spliterator(), false)
                .toList();
    }

    public void addWeatherPhenomenonFeeRule(WeatherPhenomenonFeeRuleRequest weatherPhenomenonFeeRuleRequest)
    {
        WeatherPhenomenonFeeRule weatherPhenomenonFeeRule = mapRequestToModel(weatherPhenomenonFeeRuleRequest);
        weatherPhenomenonFeeRuleRepository.save(weatherPhenomenonFeeRule);
    }

    public void addWeatherPhenomenonFeeRule(WeatherPhenomenonFeeRule weatherPhenomenonFeeRule)
    {
        weatherPhenomenonFeeRuleRepository.save(weatherPhenomenonFeeRule);
    }

    public void deleteWeatherPhenomenonFeeRuleById(UUID id)
    {
        weatherPhenomenonFeeRuleRepository.deleteById(id);
    }


    private WeatherPhenomenonFeeRule mapRequestToModel(WeatherPhenomenonFeeRuleRequest weatherPhenomenonFeeRuleRequest)
            throws VehicleNotFoundException, RegionNotFoundException, PhenomenonCategoryNotFoundException
    {
        WeatherPhenomenonFeeRule model = new WeatherPhenomenonFeeRule();

        model.setPriceDifference(weatherPhenomenonFeeRuleRequest.getPriceDifference());
        model.setVehicleType(vehicleTypeRepository.findById(weatherPhenomenonFeeRuleRequest.getVehicleTypeId())
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle type not found."))
        );
        model.setRegion(regionRepository.findById(weatherPhenomenonFeeRuleRequest.getRegionId())
                .orElseThrow(() -> new RegionNotFoundException("Region not found."))
        );
        model.setPhenomenonCategory(phenomenonCategoryRepository.findById(weatherPhenomenonFeeRuleRequest.getPhenomenonCategoryId())
                .orElseThrow(() -> new PhenomenonCategoryNotFoundException("Phenomenon category not found."))
        );

        return model;
    }
}
