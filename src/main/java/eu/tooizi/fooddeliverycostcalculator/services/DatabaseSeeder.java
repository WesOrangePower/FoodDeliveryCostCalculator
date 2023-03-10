package eu.tooizi.fooddeliverycostcalculator.services;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.*;
import eu.tooizi.fooddeliverycostcalculator.repositories.PhenomenonCategoryRepository;
import eu.tooizi.fooddeliverycostcalculator.repositories.RegionRepository;
import eu.tooizi.fooddeliverycostcalculator.repositories.VehicleTypeRepository;
import eu.tooizi.fooddeliverycostcalculator.repositories.WeatherPhenomenonRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DatabaseSeeder
{
    private final RegionRepository regionRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final WeatherPhenomenonRepository weatherPhenomenonRepository;
    private final PhenomenonCategoryRepository phenomenonCategoryRepository;

    public DatabaseSeeder(RegionRepository regionRepository,
                          VehicleTypeRepository vehicleTypeRepository,
                          WeatherPhenomenonRepository weatherPhenomenonRepository,
                          PhenomenonCategoryRepository phenomenonCategoryRepository)
    {
        this.regionRepository = regionRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.weatherPhenomenonRepository = weatherPhenomenonRepository;
        this.phenomenonCategoryRepository = phenomenonCategoryRepository;
    }

    /**
     * Seeds the database with the data specified in the requirements.
     */
    public void seed()
    {
        VehicleType car = new VehicleType();
        car.setName("Car");

        VehicleType scooter = new VehicleType();
        scooter.setName("Scooter");

        VehicleType bike = new VehicleType();
        bike.setName("Bike");

        List<VehicleType> vehicles = List.of(car, scooter, bike);
        vehicleTypeRepository.saveAll(vehicles);

        Region tallinn = new Region();
        tallinn.setName("Tallinn");
        tallinn.setWeatherStationName("Tallinn-Harku");
        tallinn.setRegionalBaseFees(makeRegionalBaseFees(3d, vehicles));

        Region tartu = new Region();
        tartu.setName("Tartu");
        tartu.setWeatherStationName("Tartu-Tõravere");
        tartu.setRegionalBaseFees(makeRegionalBaseFees(2.5, vehicles));

        Region paernu = new Region();
        paernu.setName("Pärnu");
        paernu.setWeatherStationName("Pärnu");
        paernu.setRegionalBaseFees(makeRegionalBaseFees(2d, vehicles));

        regionRepository.saveAll(List.of(tallinn, tartu, paernu));

        PhenomenonCategory snow = new PhenomenonCategory();
        snow.setName("Snow");

        PhenomenonCategory sleet = new PhenomenonCategory();
        sleet.setName("Sleet");

        PhenomenonCategory rain = new PhenomenonCategory();
        rain.setName("Rain");

        PhenomenonCategory glaze = new PhenomenonCategory();
        glaze.setName("Glaze");

        PhenomenonCategory hail = new PhenomenonCategory();
        hail.setName("Hail");

        PhenomenonCategory thunder = new PhenomenonCategory();
        thunder.setName("Thunder");

        PhenomenonCategory clearOrOther = new PhenomenonCategory();
        clearOrOther.setName("Clear or other");

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
    }

    private Collection<RegionalBaseFee> makeRegionalBaseFees(double bikeFee, List<VehicleType> vehicles)
    {
        List<RegionalBaseFee> fees = new ArrayList<>();
        for (int i = 0; i < vehicles.size(); i++)
        {
            RegionalBaseFee fee = new RegionalBaseFee();
            fee.setVehicleType(vehicles.get(i));
            fee.setBaseFee(bikeFee + (i * .5));
            fees.add(fee);
        }
        return fees;
    }

}
