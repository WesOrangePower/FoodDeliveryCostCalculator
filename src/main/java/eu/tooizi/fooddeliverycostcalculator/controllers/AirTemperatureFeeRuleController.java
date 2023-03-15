package eu.tooizi.fooddeliverycostcalculator.controllers;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.AirTemperatureFeeRule;
import eu.tooizi.fooddeliverycostcalculator.services.AirTemperatureFeeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/fee-rule")
public class AirTemperatureFeeRuleController
{
    private final AirTemperatureFeeRuleService airTemperatureFeeRuleService;

    public AirTemperatureFeeRuleController(@Autowired AirTemperatureFeeRuleService airTemperatureFeeRuleService)
    {
        this.airTemperatureFeeRuleService = airTemperatureFeeRuleService;
    }

    @GetMapping("/air-temperature")
    public Collection<AirTemperatureFeeRule> getAll()
    {
        //TODO: Circular
        return airTemperatureFeeRuleService.getAirTemperatureFeeRules();
    }

    @PostMapping("/air-temperature")
    public void create(@RequestParam(name = "air_temperature_fee_rule") AirTemperatureFeeRule airTemperatureFeeRule)
    {
        airTemperatureFeeRuleService.addAirTemperatureFeeRule(airTemperatureFeeRule);
    }

    @DeleteMapping("/air-temperature")
    public void delete(@RequestParam UUID id)
    {
        airTemperatureFeeRuleService.deleteAirTemperatureFeeRuleById(id);
    }
}
