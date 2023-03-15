package eu.tooizi.fooddeliverycostcalculator.controllers;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WeatherPhenomenonFeeRule;
import eu.tooizi.fooddeliverycostcalculator.domain.responses.WeatherPhenomenonFeeRulesResponse;
import eu.tooizi.fooddeliverycostcalculator.services.WeatherPhenomenonFeeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/fee-rule")
public class WeatherPhenomenonFeeRuleController
{
    private final WeatherPhenomenonFeeRuleService weatherPhenomenonFeeRuleService;

    public WeatherPhenomenonFeeRuleController(@Autowired WeatherPhenomenonFeeRuleService weatherPhenomenonFeeRuleService)
    {
        this.weatherPhenomenonFeeRuleService = weatherPhenomenonFeeRuleService;
    }

    @GetMapping("/weather-phenomenon")
    public WeatherPhenomenonFeeRulesResponse getAll()
    {
        return new WeatherPhenomenonFeeRulesResponse(
                weatherPhenomenonFeeRuleService.getWeatherPhenomenonFeeRules()
        );
    }

    @PostMapping("/weather-phenomenon")
    public void create(WeatherPhenomenonFeeRule weatherPhenomenonFeeRule)
    {
        weatherPhenomenonFeeRuleService.addWeatherPhenomenonFeeRule(weatherPhenomenonFeeRule);
    }

    @DeleteMapping("/weather-phenomenon")
    public void delete(@RequestParam UUID id)
    {
        weatherPhenomenonFeeRuleService.deleteWeatherPhenomenonFeeRuleById(id);
    }
}
