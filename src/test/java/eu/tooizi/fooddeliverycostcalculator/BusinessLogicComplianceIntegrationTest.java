package eu.tooizi.fooddeliverycostcalculator;

import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.WeatherConditions;
import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.WeatherPhenomenon;
import eu.tooizi.fooddeliverycostcalculator.repositories.PhenomenonCategoryRepository;
import eu.tooizi.fooddeliverycostcalculator.repositories.RegionRepository;
import eu.tooizi.fooddeliverycostcalculator.repositories.WeatherConditionsRepository;
import eu.tooizi.fooddeliverycostcalculator.repositories.WeatherPhenomenonRepository;
import eu.tooizi.fooddeliverycostcalculator.services.DeliveryFeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static eu.tooizi.fooddeliverycostcalculator.DTOs.domain.PhenomenonCategory.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class BusinessLogicComplianceIntegrationTest
{
    @Autowired
    DeliveryFeeService deliveryFeeService;

    @Autowired
    WeatherConditionsRepository weatherConditionsRepository;

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    WeatherPhenomenonRepository weatherPhenomenonRepository;

    @Autowired
    PhenomenonCategoryRepository phenomenonCategoryRepository;

    @Test
    public void givenRegionIsTallinnAndEachVehicle_whenWeatherIsBland_thenTotalFeeIsRBF()
    {
        var region = "Tallinn";
        insertBlandWeatherConditions(region);
        Map<String, Double> vehicleTypeToFee = calculateForEachVehicle(region);

        assertThat(vehicleTypeToFee.get("Car")).isEqualTo(4);
        assertThat(vehicleTypeToFee.get("Scooter")).isEqualTo(3.5);
        assertThat(vehicleTypeToFee.get("Bike")).isEqualTo(3);
    }

    @Test
    public void givenRegionIsTartuAndEachVehicle_whenWeatherIsBland_thenTotalFeeIsRBF()
    {
        var region = "Tartu";
        insertBlandWeatherConditions(region);
        Map<String, Double> vehicleTypeToFee = calculateForEachVehicle(region);

        assertThat(vehicleTypeToFee.get("Car")).isEqualTo(3.5);
        assertThat(vehicleTypeToFee.get("Scooter")).isEqualTo(3);
        assertThat(vehicleTypeToFee.get("Bike")).isEqualTo(2.5);
    }


    @Test
    public void givenRegionIsPaernuAndEachVehicle_whenWeatherIsBland_thenTotalFeeIsRBF()
    {
        var region = "Pärnu";
        insertBlandWeatherConditions(region);
        Map<String, Double> vehicleTypeToFee = calculateForEachVehicle(region);

        assertThat(vehicleTypeToFee.get("Car")).isEqualTo(3);
        assertThat(vehicleTypeToFee.get("Scooter")).isEqualTo(2.5);
        assertThat(vehicleTypeToFee.get("Bike")).isEqualTo(2);
    }

    @Test
    public void givenRegionIsTallinnAndEachVehicle_whenWeatherIsChilly_thenTotalFeeIsRBFForCarAndHalfAEuroMoreForScooterAndBike()
    {
        var region = "Tallinn";
        insertWeatherWithCustomAirTemperature(region, -5d);
        calculateForEachVehicle(region);

        Map<String, Double> vehicleTypeToFee = calculateForEachVehicle(region);

        assertThat(vehicleTypeToFee.get("Car")).isEqualTo(4);
        assertThat(vehicleTypeToFee.get("Scooter")).isEqualTo(3.5 + .5);
        assertThat(vehicleTypeToFee.get("Bike")).isEqualTo(3 + .5);
    }

    @Test
    public void givenRegionIsTartuAndEachVehicle_whenWeatherIsChilly_thenTotalFeeIsRBFForCarAndHalfAEuroMoreForScooterAndBike()
    {
        var region = "Tartu";
        insertWeatherWithCustomAirTemperature(region, -5d);
        calculateForEachVehicle(region);

        Map<String, Double> vehicleTypeToFee = calculateForEachVehicle(region);

        assertThat(vehicleTypeToFee.get("Car")).isEqualTo(3.5);
        assertThat(vehicleTypeToFee.get("Scooter")).isEqualTo(3 + .5);
        assertThat(vehicleTypeToFee.get("Bike")).isEqualTo(2.5 + .5);
    }

    @Test
    public void givenRegionIsPaernuAndEachVehicle_whenWeatherIsChilly_thenTotalFeeIsRBFForCarAndHalfAEuroMoreForScooterAndBike()
    {
        var region = "Pärnu";
        insertWeatherWithCustomAirTemperature(region, -5d);
        calculateForEachVehicle(region);

        Map<String, Double> vehicleTypeToFee = calculateForEachVehicle(region);

        assertThat(vehicleTypeToFee.get("Car")).isEqualTo(3);
        assertThat(vehicleTypeToFee.get("Scooter")).isEqualTo(2.5 + .5);
        assertThat(vehicleTypeToFee.get("Bike")).isEqualTo(2 + .5);
    }


    @Test
    public void givenRegionIsTallinnAndEachVehicle_whenWeatherIsBitter_thenTotalFeeIsRBFForCarAndAEuroMoreForScooterAndBike()
    {
        var region = "Tallinn";
        insertWeatherWithCustomAirTemperature(region, -15d);
        calculateForEachVehicle(region);

        Map<String, Double> vehicleTypeToFee = calculateForEachVehicle(region);

        assertThat(vehicleTypeToFee.get("Car")).isEqualTo(4d);
        assertThat(vehicleTypeToFee.get("Scooter")).isEqualTo(3.5 + 1d);
        assertThat(vehicleTypeToFee.get("Bike")).isEqualTo(3d + 1d);
    }

    @Test
    public void givenRegionIsTartuAndEachVehicle_whenWeatherIsBitter_thenTotalFeeIsRBFForCarAndAEuroMoreForScooterAndBike()
    {
        var region = "Tartu";
        insertWeatherWithCustomAirTemperature(region, -15d);
        calculateForEachVehicle(region);

        Map<String, Double> vehicleTypeToFee = calculateForEachVehicle(region);

        assertThat(vehicleTypeToFee.get("Car")).isEqualTo(3.5);
        assertThat(vehicleTypeToFee.get("Scooter")).isEqualTo(3 + 1);
        assertThat(vehicleTypeToFee.get("Bike")).isEqualTo(2.5 + 1);
    }

    @Test
    public void givenRegionIsPaernuAndEachVehicle_whenWeatherIsBitter_thenTotalFeeIsRBFForCarAndAEuroMoreForScooterAndBike()
    {
        var region = "Pärnu";
        insertWeatherWithCustomAirTemperature(region, -15d);
        calculateForEachVehicle(region);

        Map<String, Double> vehicleTypeToFee = calculateForEachVehicle(region);

        assertThat(vehicleTypeToFee.get("Car")).isEqualTo(3);
        assertThat(vehicleTypeToFee.get("Scooter")).isEqualTo(2.5 + 1);
        assertThat(vehicleTypeToFee.get("Bike")).isEqualTo(2 + 1);
    }

    @Test
    public void givenRegionIsTallinnAndEachVehicle_whenWeatherIsWindy_thenTotalFeeIsRBFForCarAndScooterAndHalfAEuroMoreForBike()
    {
        var region = "Tallinn";
        insertWeatherWithCustomWindSpeed(region, 15d);
        calculateForEachVehicle(region);

        Map<String, Double> vehicleTypeToFee = calculateForEachVehicle(region);

        assertThat(vehicleTypeToFee.get("Car")).isEqualTo(4d);
        assertThat(vehicleTypeToFee.get("Scooter")).isEqualTo(3.5);
        assertThat(vehicleTypeToFee.get("Bike")).isEqualTo(3d + .5);
    }

    @Test
    public void givenRegionIsTartuAndEachVehicle_whenWeatherIsWindy_thenTotalFeeIsRBFForCarAndScooterAndHalfAEuroMoreForBike()
    {
        var region = "Tartu";
        insertWeatherWithCustomWindSpeed(region, 15d);
        calculateForEachVehicle(region);

        Map<String, Double> vehicleTypeToFee = calculateForEachVehicle(region);

        assertThat(vehicleTypeToFee.get("Car")).isEqualTo(3.5);
        assertThat(vehicleTypeToFee.get("Scooter")).isEqualTo(3);
        assertThat(vehicleTypeToFee.get("Bike")).isEqualTo(2.5 + .5);
    }

    @Test
    public void givenRegionIsPaernuAndEachVehicle_whenWeatherIsWindy_thenTotalFeeIsRBFForCarAndScooterAndHalfAEuroMoreForBike()
    {
        var region = "Pärnu";
        insertWeatherWithCustomWindSpeed(region, 15d);
        calculateForEachVehicle(region);

        Map<String, Double> vehicleTypeToFee = calculateForEachVehicle(region);

        assertThat(vehicleTypeToFee.get("Car")).isEqualTo(3);
        assertThat(vehicleTypeToFee.get("Scooter")).isEqualTo(2.5);
        assertThat(vehicleTypeToFee.get("Bike")).isEqualTo(2 + .5);
    }

    @Test
    public void givenRegionIsTallinnAndEachVehicle_whenWeatherIsStormy_thenTotalFeeIsRBFForCarAndScooterAndBikeIsUnavailable()
    {
        var region = "Tallinn";
        insertWeatherWithCustomWindSpeed(region, 25d);
        calculateForEachVehicle(region);

        Map<String, Double> vehicleTypeToFee = calculateForEachVehicle(region);

        assertThat(vehicleTypeToFee.get("Car")).isEqualTo(4d);
        assertThat(vehicleTypeToFee.get("Scooter")).isEqualTo(3.5);
        assertThat(vehicleTypeToFee.get("Bike")).isEqualTo(0d);
    }


    @Test
    public void givenRegionIsTartuAndEachVehicle_whenWeatherIsStormy_thenTotalFeeIsRBFForCarAndScooterAndBikeIsUnavailable()
    {
        var region = "Tartu";
        insertWeatherWithCustomWindSpeed(region, 25d);
        calculateForEachVehicle(region);

        Map<String, Double> vehicleTypeToFee = calculateForEachVehicle(region);

        assertThat(vehicleTypeToFee.get("Car")).isEqualTo(3.5);
        assertThat(vehicleTypeToFee.get("Scooter")).isEqualTo(3d);
        assertThat(vehicleTypeToFee.get("Bike")).isEqualTo(0d);
    }

    @Test
    public void givenRegionIsPaernuAndEachVehicle_whenWeatherIsStormy_thenTotalFeeIsRBFForCarAndScooterAndBikeIsUnavailable()
    {
        var region = "Pärnu";
        insertWeatherWithCustomWindSpeed(region, 25d);
        calculateForEachVehicle(region);

        Map<String, Double> vehicleTypeToFee = calculateForEachVehicle(region);

        assertThat(vehicleTypeToFee.get("Car")).isEqualTo(3);
        assertThat(vehicleTypeToFee.get("Scooter")).isEqualTo(2.5);
        assertThat(vehicleTypeToFee.get("Bike")).isEqualTo(0d);
    }

    @Test
    public void givenEachRegionAndEachVehicle_whenPhenomenonCategoryIsSnowOrSleet_thenTotalFeeIsRBFForCarAndAEuroMoreForScooterAndBike()
    {
        var regionToReducedPrice = Map.of("Tallinn", 0d, "Tartu", 0.5, "Pärnu", 1d);
        for (String region : regionToReducedPrice.keySet())
        {
            for (String category : List.of(SNOW, SLEET))
            {
                for (var weatherPhenomenon : getWeatherPhenomenaForCategory(category))
                {
                    insertWeatherWithCustomWeatherPhenomenon(region, weatherPhenomenon.getName());

                    Map<String, Double> vehicleTypeToFee = calculateForEachVehicle(region);

                    assertThat(vehicleTypeToFee.get("Car")).isEqualTo(4d - regionToReducedPrice.get(region));
                    assertThat(vehicleTypeToFee.get("Scooter")).isEqualTo(3.5 + 1 - regionToReducedPrice.get(region));
                    assertThat(vehicleTypeToFee.get("Bike")).isEqualTo(3d + 1 - regionToReducedPrice.get(region));
                }
            }
        }
    }

    @Test
    public void givenEachRegionAndEachVehicle_whenPhenomenonCategoryIsRain_thenTotalFeeIsRBFForCarAndHalfAEuroMoreForScooterAndBike()
    {
        var regionToReducedPrice = Map.of("Tallinn", 0d, "Tartu", 0.5, "Pärnu", 1d);
        for (String region : regionToReducedPrice.keySet())
        {
            for (var weatherPhenomenon : getWeatherPhenomenaForCategory(RAIN))
            {
                insertWeatherWithCustomWeatherPhenomenon(region, weatherPhenomenon.getName());

                Map<String, Double> vehicleTypeToFee = calculateForEachVehicle(region);

                assertThat(vehicleTypeToFee.get("Car")).isEqualTo(4d - regionToReducedPrice.get(region));
                assertThat(vehicleTypeToFee.get("Scooter")).isEqualTo(3.5 + .5 - regionToReducedPrice.get(region));
                assertThat(vehicleTypeToFee.get("Bike")).isEqualTo(3d + .5 - regionToReducedPrice.get(region));
            }
        }
    }
    @Test
    public void givenEachRegionAndEachVehicle_whenPhenomenonCategoryIsGlazeOrHailOrThunder_thenTotalFeeIsRBFForCarAndScooterAndBikeAreUnavailable()
    {
        var regionToReducedPrice = Map.of("Tallinn", 0d, "Tartu", 0.5, "Pärnu", 1d);
        for (String region : regionToReducedPrice.keySet())
        {
            for (String category : List.of(GLAZE, HAIL, THUNDER))
            {
                for (var weatherPhenomenon : getWeatherPhenomenaForCategory(category))
                {
                    insertWeatherWithCustomWeatherPhenomenon(region, weatherPhenomenon.getName());

                    Map<String, Double> vehicleTypeToFee = calculateForEachVehicle(region);

                    assertThat(vehicleTypeToFee.get("Car")).isEqualTo(4d - regionToReducedPrice.get(region));
                    assertThat(vehicleTypeToFee.get("Scooter")).isEqualTo(0d);
                    assertThat(vehicleTypeToFee.get("Bike")).isEqualTo(0d);
                }
            }
        }
    }

    private Map<String, Double> calculateForEachVehicle(String region)
    {
        var vehicleTypes = List.of("Car", "Scooter", "Bike");

        Map<String, Double> vehicleTypeToFee = new HashMap<>();

        for (var vehicleType : vehicleTypes)
        {
            var deliveryFeeResponse = deliveryFeeService.getDeliveryFee(region, vehicleType);
            vehicleTypeToFee.put(vehicleType, deliveryFeeResponse.getDeliveryFee());
        }
        return vehicleTypeToFee;
    }

    private Collection<WeatherPhenomenon> getWeatherPhenomenaForCategory(String categoryName)
    {
        var category = phenomenonCategoryRepository.findFirstByName(categoryName).orElseThrow();
        return weatherPhenomenonRepository.findAllByPhenomenonCategoryIs(category);
    }

    private void insertBlandWeatherConditions(String region)
    {
        var weatherConditions = new WeatherConditions();
        weatherConditions.setStationWmoCode("4308");
        weatherConditions.setRegion(regionRepository.findFirstByName(region).orElseThrow());
        weatherConditions.setTemperatureCelsius(10);
        weatherConditions.setWindSpeedMps(5);
        weatherConditions.setWeatherPhenomenon(weatherPhenomenonRepository.findByName("Clear").orElseThrow());
        weatherConditions.setTimestamp(Instant.now().plus(1, ChronoUnit.HOURS));
        weatherConditionsRepository.save(weatherConditions);
    }

    private void insertWeatherWithCustomAirTemperature(String region, Double temperatureCelsius)
    {
        var weatherConditions = new WeatherConditions();
        weatherConditions.setStationWmoCode("4308");
        weatherConditions.setRegion(regionRepository.findFirstByName(region).orElseThrow());
        weatherConditions.setTemperatureCelsius(temperatureCelsius);
        weatherConditions.setWindSpeedMps(5);
        weatherConditions.setWeatherPhenomenon(weatherPhenomenonRepository.findByName("Clear").orElseThrow());
        weatherConditions.setTimestamp(Instant.now().plus(1, ChronoUnit.HOURS));
        weatherConditionsRepository.save(weatherConditions);
    }

    private void insertWeatherWithCustomWindSpeed(String region, Double windSpeed)
    {
        var weatherConditions = new WeatherConditions();
        weatherConditions.setStationWmoCode("4308");
        weatherConditions.setRegion(regionRepository.findFirstByName(region).orElseThrow());
        weatherConditions.setTemperatureCelsius(10);
        weatherConditions.setWindSpeedMps(windSpeed);
        weatherConditions.setWeatherPhenomenon(weatherPhenomenonRepository.findByName("Clear").orElseThrow());
        weatherConditions.setTimestamp(Instant.now().plus(1, ChronoUnit.HOURS));
        weatherConditionsRepository.save(weatherConditions);
    }

    private void insertWeatherWithCustomWeatherPhenomenon(String region, String weatherPhenomenon)
    {
        var weatherConditions = new WeatherConditions();
        weatherConditions.setStationWmoCode("4308");
        weatherConditions.setRegion(regionRepository.findFirstByName(region).orElseThrow());
        weatherConditions.setTemperatureCelsius(10);
        weatherConditions.setWindSpeedMps(5);
        weatherConditions.setWeatherPhenomenon(weatherPhenomenonRepository.findByName(weatherPhenomenon).orElseThrow());
        weatherConditions.setTimestamp(Instant.now().plus(1, ChronoUnit.HOURS));
        weatherConditionsRepository.save(weatherConditions);
    }
}
