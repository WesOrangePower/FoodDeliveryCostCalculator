package eu.tooizi.fooddeliverycostcalculator;

import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.Region;
import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.VehicleType;
import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.WeatherConditions;
import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.WeatherPhenomenon;
import eu.tooizi.fooddeliverycostcalculator.DTOs.responses.DeliveryFeeResponse;
import eu.tooizi.fooddeliverycostcalculator.repositories.RegionRepository;
import eu.tooizi.fooddeliverycostcalculator.repositories.VehicleTypeRepository;
import eu.tooizi.fooddeliverycostcalculator.repositories.WeatherConditionsRepository;
import eu.tooizi.fooddeliverycostcalculator.repositories.WeatherPhenomenonRepository;
import eu.tooizi.fooddeliverycostcalculator.services.DeliveryFeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class DeliveryFeeIntegrationTest
{
    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    WeatherConditionsRepository weatherConditionsRepository;
    @Autowired
    RegionRepository regionRepository;
    @Autowired
    WeatherPhenomenonRepository weatherPhenomenonRepository;
    @Autowired
    VehicleTypeRepository vehicleTypeRepository;

    @Test
    public void givenRegionIsTallinnAndVehicleTypeIsCar_whenGetDeliveryFee_thenReturnDeliveryFeeResponse()
    {
        var car = "Car";
        var region = "Tallinn";
        var url = "/api/delivery-fee?region=" + region + "&vehicle_type=" + car;

        ResponseEntity<DeliveryFeeResponse> response = restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                DeliveryFeeResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDeliveryFee()).isGreaterThanOrEqualTo(3d);
    }

    @Test
    public void givenExtremeWeatherAndRegionIsTallinnAndVehicleTypeIsBike_whenGetDeliveryFee_thenReturnErroredDeliveryFeeResponse()
    {
        insertExtremeWeatherInTallinn();

        var bike = "Bike";
        var region = "Tallinn";
        var url = "/api/delivery-fee?region=" + region + "&vehicle_type=" + bike;

        ResponseEntity<DeliveryFeeResponse> response = restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                DeliveryFeeResponse.class);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo(DeliveryFeeService.UNDELIVERABLE_ERROR_MESSAGE);
    }

    @Test
    public void givenExtremeWeatherAndRegionIsTallinnAndVehicleTypeIsScooter_whenGetDeliveryFee_thenReturnErroredDeliveryFeeResponse()
    {
        insertExtremeWeatherInTallinn();

        var scooter = "Scooter";
        var region = "Tallinn";
        var url = "/api/delivery-fee?region=" + region + "&vehicle_type=" + scooter;

        ResponseEntity<DeliveryFeeResponse> response = restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                DeliveryFeeResponse.class);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo(DeliveryFeeService.UNDELIVERABLE_ERROR_MESSAGE);
    }

    @Test
    public void testSmokeAllRestCombinations()
    {
        final String urlTemplate = "/api/delivery-fee?region=%s&vehicle_type=%s";

        List<Region> regions = StreamSupport.stream(regionRepository.findAll().spliterator(), false).toList();
        List<VehicleType> vehicleTypes = StreamSupport.stream(vehicleTypeRepository.findAll().spliterator(), false).toList();

        for (Region region : regions)
        {
            for (VehicleType vehicleType : vehicleTypes)
            {
                String url = String.format(urlTemplate, region.getName(), vehicleType.getName());

                ResponseEntity<DeliveryFeeResponse> response = restTemplate.exchange(url,
                        HttpMethod.GET,
                        null,
                        DeliveryFeeResponse.class);
                assertThat(response.getBody()).isNotNull();
                assertThat(response.getBody().getMessage().length()).isGreaterThan(0);
            }
        }
    }

    private void insertExtremeWeatherInTallinn()
    {
        Region tallinn = regionRepository.findFirstByName("Tallinn")
                .orElseThrow();
        WeatherPhenomenon thunder = weatherPhenomenonRepository.findByName("Thunder")
                .orElseThrow();

        WeatherConditions extremeWeather = new WeatherConditions();
        extremeWeather.setRegion(tallinn);
        extremeWeather.setStationWmoCode("4308");
        extremeWeather.setWeatherPhenomenon(thunder);
        extremeWeather.setTimestamp(Instant.now().plus(1, ChronoUnit.HOURS));
        extremeWeather.setTemperatureCelsius(-25d);
        extremeWeather.setWindSpeedMps(25d);
        weatherConditionsRepository.save(extremeWeather);
    }

}
