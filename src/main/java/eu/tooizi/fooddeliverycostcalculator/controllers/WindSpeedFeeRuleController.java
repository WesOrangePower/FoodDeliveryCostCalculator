package eu.tooizi.fooddeliverycostcalculator.controllers;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WindSpeedFeeRule;
import eu.tooizi.fooddeliverycostcalculator.domain.requests.WindSpeedFeeRuleRequest;
import eu.tooizi.fooddeliverycostcalculator.domain.responses.WindSpeedFeeRulesResponse;
import eu.tooizi.fooddeliverycostcalculator.services.WindSpeedFeeRuleService;
import org.springframework.web.bind.annotation.*;

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
    public WindSpeedFeeRulesResponse getAll()
    {
        return new WindSpeedFeeRulesResponse(
                windSpeedFeeRuleService.getWindSpeedFeeRules()
        );
    }

    @PostMapping("/wind-speed")
    public void create(@RequestBody WindSpeedFeeRuleRequest windSpeedFeeRuleRequest)
    {
        windSpeedFeeRuleService.addWindSpeedFeeRule(windSpeedFeeRuleRequest);
    }

    @DeleteMapping("/wind-speed/{id}")
    public void deleteWindSpeed(@PathVariable UUID id)
    {
        windSpeedFeeRuleService.deleteWindSpeedFeeRuleById(id);
    }

}
