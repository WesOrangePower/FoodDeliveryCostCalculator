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

/**
 * Service for managing weather phenomenon fee rules with CRUD-like methods.
 */
@Service
public class WeatherPhenomenonFeeRuleService
{
    private final WeatherPhenomenonFeeRuleRepository weatherPhenomenonFeeRuleRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final RegionRepository regionRepository;
    private final PhenomenonCategoryRepository phenomenonCategoryRepository;

    /**
     * Constructor.
     * All fields are autowired.
     *
     * @param weatherPhenomenonFeeRuleRepository Repository for weather phenomenon fee rules.
     * @param vehicleTypeRepository              Repository for vehicle types.
     * @param regionRepository                   Repository for regions.
     * @param phenomenonCategoryRepository       Repository for phenomenon categories.
     */
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

    /**
     * Fetches all weather phenomenon fee rules from the database.
     *
     * @return Collection of weather phenomenon fee rules.
     */
    public Collection<WeatherPhenomenonFeeRule> getWeatherPhenomenonFeeRules()
    {
        return StreamSupport.stream(weatherPhenomenonFeeRuleRepository.findAll().spliterator(), false)
                .toList();
    }

    /**
     * Creates a new weather phenomenon fee rule and saves it to the database.
     *
     * @param weatherPhenomenonFeeRuleRequest Request containing the data for the new weather phenomenon fee rule.
     */
    public void addWeatherPhenomenonFeeRule(WeatherPhenomenonFeeRuleRequest weatherPhenomenonFeeRuleRequest)
    {
        WeatherPhenomenonFeeRule weatherPhenomenonFeeRule = mapRequestToModel(weatherPhenomenonFeeRuleRequest);
        weatherPhenomenonFeeRuleRepository.save(weatherPhenomenonFeeRule);
    }

    /**
     * Deletes the weather phenomenon fee rule with the given id from the database.
     *
     * @param id ID of the weather phenomenon fee rule to delete.
     */
    public void deleteWeatherPhenomenonFeeRuleById(UUID id)
    {
        weatherPhenomenonFeeRuleRepository.deleteById(id);
    }


    /**
     * @param weatherPhenomenonFeeRuleRequest Request containing the data for the new weather phenomenon fee rule.
     * @return Weather phenomenon fee rule.
     * @throws VehicleNotFoundException            Vehicle type not found.
     * @throws RegionNotFoundException             Region not found.
     * @throws PhenomenonCategoryNotFoundException Phenomenon category not found.
     */
    private WeatherPhenomenonFeeRule mapRequestToModel(WeatherPhenomenonFeeRuleRequest weatherPhenomenonFeeRuleRequest)
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
