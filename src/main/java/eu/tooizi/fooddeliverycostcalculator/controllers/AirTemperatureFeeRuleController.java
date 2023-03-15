package eu.tooizi.fooddeliverycostcalculator.controllers;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.AirTemperatureFeeRule;
import eu.tooizi.fooddeliverycostcalculator.domain.requests.AirTemperatureFeeRuleRequest;
import eu.tooizi.fooddeliverycostcalculator.domain.responses.AirTemperatureFeeRulesResponse;
import eu.tooizi.fooddeliverycostcalculator.services.AirTemperatureFeeRuleService;
import eu.tooizi.fooddeliverycostcalculator.services.exceptions.AirTemperatureFeeRuleOverlapsException;
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
    public AirTemperatureFeeRulesResponse getAll()
    {
        return new AirTemperatureFeeRulesResponse(
                airTemperatureFeeRuleService.getAirTemperatureFeeRules()
        );
    }

    @PostMapping("/air-temperature")
    public void create(@RequestBody AirTemperatureFeeRuleRequest airTemperatureFeeRuleRequest)
    {
        airTemperatureFeeRuleService.addAirTemperatureFeeRule(airTemperatureFeeRuleRequest);
    }

    @DeleteMapping("/air-temperature/{id}")
    public void delete(@PathVariable UUID id)
    {
        airTemperatureFeeRuleService.deleteAirTemperatureFeeRuleById(id);
    }
}
