package eu.tooizi.fooddeliverycostcalculator.controllers;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WindSpeedFeeRule;
import eu.tooizi.fooddeliverycostcalculator.services.WindSpeedFeeRuleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.UUID;

@Controller
public class WindSpeedFeeRuleController
{
    private final WindSpeedFeeRuleService windSpeedFeeRuleService;

    public WindSpeedFeeRuleController(WindSpeedFeeRuleService windSpeedFeeRuleService)
    {
        this.windSpeedFeeRuleService = windSpeedFeeRuleService;
    }

    @GetMapping("/api/fee-rules/wind-speed")
    public Collection<WindSpeedFeeRule> getAll()
    {
        return windSpeedFeeRuleService.getWindSpeedFeeRules();
    }

    @PostMapping("/api/fee-rule/wind-speed")
    public void create(@RequestParam(name = "wind_speed_fee_rule") WindSpeedFeeRule windSpeedFeeRule)
    {
        windSpeedFeeRuleService.addWindSpeedFeeRule(windSpeedFeeRule);
    }

    @DeleteMapping("/api/fee-rule/wind-speed")
    public void deleteWindSpeed(@RequestParam UUID id)
    {
        windSpeedFeeRuleService.deleteWindSpeedFeeRuleById(id);
    }

}
