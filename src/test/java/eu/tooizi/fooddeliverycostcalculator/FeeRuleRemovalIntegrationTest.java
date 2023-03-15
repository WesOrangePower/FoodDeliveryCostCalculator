package eu.tooizi.fooddeliverycostcalculator;

import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.AirTemperatureFeeRule;
import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.WindSpeedFeeRule;
import eu.tooizi.fooddeliverycostcalculator.DTOs.responses.AirTemperatureFeeRulesResponse;
import eu.tooizi.fooddeliverycostcalculator.DTOs.responses.WindSpeedFeeRulesResponse;
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
public class FeeRuleRemovalIntegrationTest
{
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void givenSeededDatabase_whenRemoveAnyAirTemperatureRule_thenReturn11Rules()
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

    @Test
    public void givenSeededDatabase_WhenRemoveAnyWindSpeedRule_thenReturn5Rules()
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

        WindSpeedFeeRule rule = response.getBody().getRules().iterator().next();

        restTemplate.delete(url + "/" + rule.getId());

        response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                WindSpeedFeeRulesResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getRules().size()).isEqualTo(5);
    }

    @Test
    public void givenSeededDatabase_WhenRemoveAnyWeatherPhenomenonRule_thenReturn35Rules()
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

        WindSpeedFeeRule rule = response.getBody().getRules().iterator().next();

        restTemplate.delete(url + "/" + rule.getId());

        response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                WindSpeedFeeRulesResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getRules().size()).isEqualTo(35);
    }
}
