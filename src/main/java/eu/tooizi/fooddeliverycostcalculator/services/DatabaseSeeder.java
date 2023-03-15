package eu.tooizi.fooddeliverycostcalculator.services;

import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.*;
import eu.tooizi.fooddeliverycostcalculator.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service that seeds the base data at every start of the application.
 */
@Service
public class DatabaseSeeder
{
    private final RegionRepository regionRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final WeatherPhenomenonRepository weatherPhenomenonRepository;
    private final PhenomenonCategoryRepository phenomenonCategoryRepository;
    private final RegionalBaseFeeRepository regionalBaseFeeRepository;
    private final WeatherPhenomenonFeeRuleRepository weatherPhenomenonFeeRuleRepository;
    private final AirTemperatureFeeRuleService airTemperatureFeeRuleService;
    private final WindSpeedFeeRuleService windSpeedFeeRuleService;

    /**
     * Constructor.
     * all the repositories are autowired.
     *
     * @param regionRepository                   Repository for regions.
     * @param vehicleTypeRepository              Repository for vehicle types.
     * @param weatherPhenomenonRepository        Repository for weather phenomena.
     * @param phenomenonCategoryRepository       Repository for phenomenon categories.
     * @param regionalBaseFeeRepository          Repository for regional base fees.
     * @param weatherPhenomenonFeeRuleRepository Repository for weather phenomenon fee rules.
     * @param airTemperatureFeeRuleService       Service for air temperature fee rules.
     * @param windSpeedFeeRuleService            Service for wind speed fee rules.
     */
    public DatabaseSeeder(@Autowired RegionRepository regionRepository,
                          @Autowired VehicleTypeRepository vehicleTypeRepository,
                          @Autowired WeatherPhenomenonRepository weatherPhenomenonRepository,
                          @Autowired PhenomenonCategoryRepository phenomenonCategoryRepository,
                          @Autowired RegionalBaseFeeRepository regionalBaseFeeRepository,
                          @Autowired WeatherPhenomenonFeeRuleRepository weatherPhenomenonFeeRuleRepository,
                          @Autowired AirTemperatureFeeRuleService airTemperatureFeeRuleService,
                          @Autowired WindSpeedFeeRuleService windSpeedFeeRuleService)
    {
        this.regionRepository = regionRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.weatherPhenomenonRepository = weatherPhenomenonRepository;
        this.phenomenonCategoryRepository = phenomenonCategoryRepository;
        this.regionalBaseFeeRepository = regionalBaseFeeRepository;
        this.weatherPhenomenonFeeRuleRepository = weatherPhenomenonFeeRuleRepository;
        this.airTemperatureFeeRuleService = airTemperatureFeeRuleService;
        this.windSpeedFeeRuleService = windSpeedFeeRuleService;
    }

    /**
     * Seeds the database with the data specified in the requirements.
     */
    public void seed()
    {
        /*
         * Vehicle types
         */
        VehicleType car = new VehicleType();
        car.setName("Car");

        VehicleType scooter = new VehicleType();
        scooter.setName("Scooter");

        VehicleType bike = new VehicleType();
        bike.setName("Bike");

        List<VehicleType> vehicles = List.of(car, scooter, bike);
        vehicleTypeRepository.saveAll(vehicles);


        /*
         * Regions
         */

        Region tallinn = new Region();
        tallinn.setName("Tallinn");
        tallinn.setWeatherStationName("Tallinn-Harku");

        Region tartu = new Region();
        tartu.setName("Tartu");
        tartu.setWeatherStationName("Tartu-Tõravere");

        Region paernu = new Region();
        paernu.setName("Pärnu");
        paernu.setWeatherStationName("Pärnu");

        regionRepository.saveAll(List.of(tallinn, tartu, paernu));
        makeRegionalBaseFees(4d, vehicles, tallinn);
        makeRegionalBaseFees(3.5, vehicles, tartu);
        makeRegionalBaseFees(3d, vehicles, paernu);


        /*
         * Weather phenomena
         */

        PhenomenonCategory snow = new PhenomenonCategory();
        snow.setName(PhenomenonCategory.SNOW);

        PhenomenonCategory sleet = new PhenomenonCategory();
        sleet.setName(PhenomenonCategory.SLEET);

        PhenomenonCategory rain = new PhenomenonCategory();
        rain.setName(PhenomenonCategory.RAIN);

        PhenomenonCategory glaze = new PhenomenonCategory();
        glaze.setName(PhenomenonCategory.GLAZE);

        PhenomenonCategory hail = new PhenomenonCategory();
        hail.setName(PhenomenonCategory.HAIL);

        PhenomenonCategory thunder = new PhenomenonCategory();
        thunder.setName(PhenomenonCategory.THUNDER);

        PhenomenonCategory clearOrOther = new PhenomenonCategory();
        clearOrOther.setName(PhenomenonCategory.DEFAULT_NAME);

        phenomenonCategoryRepository.saveAll(List.of(snow, sleet, rain, glaze, hail, thunder, clearOrOther));


        Map<String, PhenomenonCategory> phenomena = new HashMap<>();
        phenomena.put("Clear", clearOrOther);
        phenomena.put("Few clouds", clearOrOther);
        phenomena.put("Variable clouds", clearOrOther);
        phenomena.put("Cloudy with clear spells ", clearOrOther);
        phenomena.put("Overcast", clearOrOther);
        phenomena.put("Light snow shower", rain);
        phenomena.put("Moderate snow shower", rain);
        phenomena.put("Heavy snow shower", rain);
        phenomena.put("Light shower", rain);
        phenomena.put("Moderate shower", rain);
        phenomena.put("Heavy shower", rain);
        phenomena.put("Light rain", rain);
        phenomena.put("Moderate rain", rain);
        phenomena.put("Heavy rain", rain);
        phenomena.put("Glaze", glaze);
        phenomena.put("Light sleet", sleet);
        phenomena.put("Moderate sleet", sleet);
        phenomena.put("Light snowfall", snow);
        phenomena.put("Moderate snowfall", snow);
        phenomena.put("Heavy snowfall", snow);
        phenomena.put("Blowing snow", snow);
        phenomena.put("Drifting snow", snow);
        phenomena.put("Hail", hail);
        phenomena.put("Mist", clearOrOther);
        phenomena.put("Fog", clearOrOther);
        phenomena.put("Thunder", thunder);
        phenomena.put("Thunderstorm", thunder);

        for (String phenomenonName : phenomena.keySet())
        {
            WeatherPhenomenon phenomenon = new WeatherPhenomenon();
            phenomenon.setName(phenomenonName);
            phenomenon.setPhenomenonCategory(phenomena.get(phenomenonName));
            weatherPhenomenonRepository.save(phenomenon);
        }


        /*
         * Fee rules
         */

        for (Region region : List.of(tallinn, tartu, paernu))
        {
            for (VehicleType vehicle : List.of(scooter, bike))
            {
                // Phenomenon fee rules
                for (PhenomenonCategory category : List.of(glaze, hail, thunder))
                {
                    WeatherPhenomenonFeeRule noBikesOrScootersInExtremeWeather = new WeatherPhenomenonFeeRule();
                    noBikesOrScootersInExtremeWeather.setVehicleType(vehicle);
                    noBikesOrScootersInExtremeWeather.setPhenomenonCategory(category);
                    noBikesOrScootersInExtremeWeather.setDeliverable(false);
                    noBikesOrScootersInExtremeWeather.setRegion(region);
                    weatherPhenomenonFeeRuleRepository.save(noBikesOrScootersInExtremeWeather);
                }

                WeatherPhenomenonFeeRule bikesOrScootersInRain = new WeatherPhenomenonFeeRule();
                bikesOrScootersInRain.setVehicleType(vehicle);
                bikesOrScootersInRain.setPhenomenonCategory(rain);
                bikesOrScootersInRain.setPriceDifference(.5);
                bikesOrScootersInRain.setRegion(region);
                weatherPhenomenonFeeRuleRepository.save(bikesOrScootersInRain);

                for (PhenomenonCategory category : List.of(sleet, snow))
                {
                    WeatherPhenomenonFeeRule bikesOrScootersInSnowOrSleet = new WeatherPhenomenonFeeRule();
                    bikesOrScootersInSnowOrSleet.setVehicleType(vehicle);
                    bikesOrScootersInSnowOrSleet.setPhenomenonCategory(category);
                    bikesOrScootersInSnowOrSleet.setPriceDifference(1d);
                    bikesOrScootersInSnowOrSleet.setRegion(region);
                    weatherPhenomenonFeeRuleRepository.save(bikesOrScootersInSnowOrSleet);
                }

                // Air temperature fee rules
                AirTemperatureFeeRule bikesOrScootersInChillyWeather = new AirTemperatureFeeRule();
                bikesOrScootersInChillyWeather.setVehicleType(vehicle);
                bikesOrScootersInChillyWeather.setLowerBound(-10d);
                bikesOrScootersInChillyWeather.setUpperBound(0d);
                bikesOrScootersInChillyWeather.setPriceDifference(.5);
                bikesOrScootersInChillyWeather.setRegion(region);
                airTemperatureFeeRuleService.addAirTemperatureFeeRule(bikesOrScootersInChillyWeather);

                AirTemperatureFeeRule bikesOrScootersInColdWeather = new AirTemperatureFeeRule();
                bikesOrScootersInColdWeather.setVehicleType(vehicle);
                bikesOrScootersInColdWeather.setUpperBound(-10d);
                bikesOrScootersInColdWeather.setPriceDifference(1d);
                bikesOrScootersInColdWeather.setRegion(region);
                airTemperatureFeeRuleService.addAirTemperatureFeeRule(bikesOrScootersInColdWeather);

            }
            // Wind speed fee rules
            WindSpeedFeeRule bikesInModerateWind = new WindSpeedFeeRule();
            bikesInModerateWind.setVehicleType(bike);
            bikesInModerateWind.setLowerBound(10d);
            bikesInModerateWind.setUpperBound(20d);
            bikesInModerateWind.setRegion(region);
            bikesInModerateWind.setPriceDifference(.5);
            windSpeedFeeRuleService.addWindSpeedFeeRule(bikesInModerateWind);

            WindSpeedFeeRule noBikesInStrongWind = new WindSpeedFeeRule();
            noBikesInStrongWind.setVehicleType(bike);
            noBikesInStrongWind.setLowerBound(20d);
            noBikesInStrongWind.setRegion(region);
            noBikesInStrongWind.setDeliverable(false);
            windSpeedFeeRuleService.addWindSpeedFeeRule(noBikesInStrongWind);
        }

    }

    private void makeRegionalBaseFees(double carFee, List<VehicleType> vehicles, Region region)
    {
        for (int i = 0; i < vehicles.size(); i++)
        {
            RegionalBaseFee fee = new RegionalBaseFee();
            fee.setVehicleType(vehicles.get(i));
            fee.setBaseFee(carFee - (i * .5));
            fee.setRegion(region);
            regionalBaseFeeRepository.save(fee);
        }
    }
}
