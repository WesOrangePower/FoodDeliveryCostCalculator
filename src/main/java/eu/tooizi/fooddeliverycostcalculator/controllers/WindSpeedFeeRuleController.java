package eu.tooizi.fooddeliverycostcalculator.controllers;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WindSpeedFeeRule;
import eu.tooizi.fooddeliverycostcalculator.services.WindSpeedFeeRuleService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/fee-rule")
public class WindSpeedFeeRuleController
{
    private final WindSpeedFeeRuleService windSpeedFeeRuleService;

    public WindSpeedFeeRuleController(WindSpeedFeeRuleService windSpeedFeeRuleService)
    {
        this.windSpeedFeeRuleService = windSpeedFeeRuleService;
    }

    @GetMapping("/wind-speed")
    public Collection<WindSpeedFeeRule> getAll()
    {
        //TODO: Circular
        return windSpeedFeeRuleService.getWindSpeedFeeRules();
    }

    @PostMapping("/wind-speed")
    public void create(@RequestParam(name = "wind_speed_fee_rule") WindSpeedFeeRule windSpeedFeeRule)
    {
        windSpeedFeeRuleService.addWindSpeedFeeRule(windSpeedFeeRule);
    }

    @DeleteMapping("/wind-speed")
    public void deleteWindSpeed(@RequestParam UUID id)
    {
        windSpeedFeeRuleService.deleteWindSpeedFeeRuleById(id);
    }

}
