package eu.tooizi.fooddeliverycostcalculator.controllers;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.AirTemperatureFeeRule;
import eu.tooizi.fooddeliverycostcalculator.services.AirTemperatureFeeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
public class FeeRuleController
{
    private final AirTemperatureFeeRuleService airTemperatureFeeRuleService;

    public FeeRuleController(@Autowired AirTemperatureFeeRuleService airTemperatureFeeRuleService)
    {
        this.airTemperatureFeeRuleService = airTemperatureFeeRuleService;
    }

    @GetMapping("/api/fee-rule/air-temperature")
    public Collection<AirTemperatureFeeRule> getAll()
    {
        return airTemperatureFeeRuleService.getAirTemperatureFeeRules();
    }

    @PostMapping("/api/fee-rule/air-temperature")
    public void create(@RequestParam(name = "air_rremperature_fee_rule") AirTemperatureFeeRule airTemperatureFeeRule)
    {
        airTemperatureFeeRuleService.addAirTemperatureFeeRule(airTemperatureFeeRule);
    }

    @DeleteMapping("/api/fee-rule/weather-phenomenon")
    public void delete(@RequestParam UUID id)
    {
        airTemperatureFeeRuleService.deleteAirTemperatureFeeRuleById(id);
    }
}
