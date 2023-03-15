package eu.tooizi.fooddeliverycostcalculator;

import eu.tooizi.fooddeliverycostcalculator.DTOs.requests.AirTemperatureFeeRuleRequest;
import eu.tooizi.fooddeliverycostcalculator.DTOs.requests.WeatherPhenomenonFeeRuleRequest;
import eu.tooizi.fooddeliverycostcalculator.DTOs.requests.WindSpeedFeeRuleRequest;
import eu.tooizi.fooddeliverycostcalculator.DTOs.responses.AirTemperatureFeeRulesResponse;
import eu.tooizi.fooddeliverycostcalculator.DTOs.responses.WindSpeedFeeRulesResponse;
import eu.tooizi.fooddeliverycostcalculator.repositories.PhenomenonCategoryRepository;
import eu.tooizi.fooddeliverycostcalculator.repositories.RegionRepository;
import eu.tooizi.fooddeliverycostcalculator.repositories.VehicleTypeRepository;
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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class FeeRuleCreateIntegrationTest
{
    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    RegionRepository regionRepository;
    @Autowired
    VehicleTypeRepository vehicleTypeRepository;
    @Autowired
    PhenomenonCategoryRepository phenomenonCategoryRepository;

    @Test
    public void givenSeededDatabase_whenCreateNewAndGetAirTemperatureRules_thenReturn13Rules()
    {
        var url = "/api/fee-rule/air-temperature";

        var rule = new AirTemperatureFeeRuleRequest();
        rule.setDeliverable(false);
        rule.setRegionId(regionRepository.findFirstByName("Tallinn").orElseThrow().getId());
        rule.setVehicleTypeId(vehicleTypeRepository.findFirstByName("Car").orElseThrow().getId());
        rule.setUpperBound(-30d);
        rule.setPriceDifference(0d);

        restTemplate.postForEntity(url, rule, AirTemperatureFeeRuleRequest.class);

        ResponseEntity<AirTemperatureFeeRulesResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                AirTemperatureFeeRulesResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getRules().size()).isEqualTo(13);
    }

    @Test
    public void givenSeededDatabase_whenCreateNewAndGetWindSpeedRules_thenReturn7Rules()
    {
        var url = "/api/fee-rule/wind-speed";

        var rule = new WindSpeedFeeRuleRequest();
        rule.setDeliverable(false);
        rule.setRegionId(regionRepository.findFirstByName("Tallinn").orElseThrow().getId());
        rule.setVehicleTypeId(vehicleTypeRepository.findFirstByName("Car").orElseThrow().getId());
        rule.setLowerBound(30d);
        rule.setPriceDifference(0d);

        restTemplate.postForEntity(url, rule, WindSpeedFeeRuleRequest.class);

        ResponseEntity<WindSpeedFeeRulesResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                WindSpeedFeeRulesResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getRules().size()).isEqualTo(7);
    }

    @Test
    public void givenSeededDatabase_whenCreateNewAndGetWeatherPhenomenonRules_thenReturn37Rules()
    {
        var url = "/api/fee-rule/weather-phenomenon";

        var rule = new WeatherPhenomenonFeeRuleRequest();
        rule.setDeliverable(true);
        rule.setRegionId(regionRepository.findFirstByName("Tallinn").orElseThrow().getId());
        rule.setVehicleTypeId(vehicleTypeRepository.findFirstByName("Car").orElseThrow().getId());
        rule.setPhenomenonCategoryId(phenomenonCategoryRepository.findDefault().getId());
        rule.setPriceDifference(2d);

        restTemplate.postForEntity(url, rule, WeatherPhenomenonFeeRuleRequest.class);

        ResponseEntity<WindSpeedFeeRulesResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                WindSpeedFeeRulesResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getRules().size()).isEqualTo(37);
    }
}
