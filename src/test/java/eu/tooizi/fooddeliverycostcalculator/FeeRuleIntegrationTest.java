package eu.tooizi.fooddeliverycostcalculator;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.AirTemperatureFeeRule;
import eu.tooizi.fooddeliverycostcalculator.domain.responses.AirTemperatureFeeRulesResponse;
import eu.tooizi.fooddeliverycostcalculator.domain.responses.WindSpeedFeeRulesResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FeeRuleIntegrationTest
{
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void givenFreshlySeededDatabase_whenGetAirTemperatureRules_thenReturn12Rules()
    {
        var url = "/api/fee-rule/air-temperature";

        ResponseEntity<AirTemperatureFeeRulesResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                AirTemperatureFeeRulesResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getRules().size()).isEqualTo(12);
    }

    @Test
    public void givenFreshlySeededDatabase_whenGetWindSpeedRules_thenReturn6Rules()
    {
        var url = "/api/fee-rule/wind-speed";

        ResponseEntity<WindSpeedFeeRulesResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                WindSpeedFeeRulesResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getRules().size()).isEqualTo(6);
    }

    @Test
    public void givenFreshlySeededDatabase_whenGetWeatherPhenomenonRules_thenReturn36Rules()
    {
        var url = "/api/fee-rule/weather-phenomenon";

        ResponseEntity<WindSpeedFeeRulesResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                WindSpeedFeeRulesResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getRules().size()).isEqualTo(36);
    }

    @Test
    public void givenFreshlySeededDatabase_whenRemoveAnyAirTemperatureRule_thenReturn11Rules()
    {
        var url = "/api/fee-rule/air-temperature";

        ResponseEntity<AirTemperatureFeeRulesResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                AirTemperatureFeeRulesResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getRules().size()).isEqualTo(12);

        AirTemperatureFeeRule rule = response.getBody().getRules().iterator().next();

        restTemplate.delete(url + "/" + rule.getId());

        response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                AirTemperatureFeeRulesResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getRules().size()).isEqualTo(11);
    }
}
