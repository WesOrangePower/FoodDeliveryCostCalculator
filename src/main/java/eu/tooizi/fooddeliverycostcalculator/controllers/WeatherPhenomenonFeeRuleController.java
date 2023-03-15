package eu.tooizi.fooddeliverycostcalculator.controllers;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WeatherPhenomenonFeeRule;
import eu.tooizi.fooddeliverycostcalculator.domain.requests.WeatherPhenomenonFeeRuleRequest;
import eu.tooizi.fooddeliverycostcalculator.domain.responses.WeatherPhenomenonFeeRulesResponse;
import eu.tooizi.fooddeliverycostcalculator.services.WeatherPhenomenonFeeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Weather phenomenon fee rule REST controller.
 */
@RestController
@RequestMapping("/api/fee-rule")
public class WeatherPhenomenonFeeRuleController
{
    private final WeatherPhenomenonFeeRuleService weatherPhenomenonFeeRuleService;

    /**
     * Constructor.
     * Field is autowired.
     *
     * @param weatherPhenomenonFeeRuleService Service for {@link WeatherPhenomenonFeeRule}
     */
    public WeatherPhenomenonFeeRuleController(@Autowired WeatherPhenomenonFeeRuleService weatherPhenomenonFeeRuleService)
    {
        this.weatherPhenomenonFeeRuleService = weatherPhenomenonFeeRuleService;
    }

    /**
     * Fetches all weather phenomenon fee rules.
     *
     * @return Response of all weather phenomenon fee rules.
     */
    @GetMapping("/weather-phenomenon")
    public WeatherPhenomenonFeeRulesResponse getAll()
    {
        return new WeatherPhenomenonFeeRulesResponse(
                weatherPhenomenonFeeRuleService.getWeatherPhenomenonFeeRules()
        );
    }

    /**
     * Creates a new weather phenomenon fee rule.
     *
     * @param weatherPhenomenonFeeRuleRequest Request of a new weather phenomenon fee rule.
     */
    @PostMapping("/weather-phenomenon")
    public void create(@RequestBody WeatherPhenomenonFeeRuleRequest weatherPhenomenonFeeRuleRequest)
    {
        weatherPhenomenonFeeRuleService.addWeatherPhenomenonFeeRule(weatherPhenomenonFeeRuleRequest);
    }

    /**
     * Updates a weather phenomenon fee rule.
     *
     * @param id ID of the weather phenomenon fee rule to update.
     */
    @DeleteMapping("/weather-phenomenon/{id}")
    public void delete(@PathVariable UUID id)
    {
        weatherPhenomenonFeeRuleService.deleteWeatherPhenomenonFeeRuleById(id);
    }
}
