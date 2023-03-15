package eu.tooizi.fooddeliverycostcalculator.controllers;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WindSpeedFeeRule;
import eu.tooizi.fooddeliverycostcalculator.domain.requests.WindSpeedFeeRuleRequest;
import eu.tooizi.fooddeliverycostcalculator.domain.responses.WindSpeedFeeRulesResponse;
import eu.tooizi.fooddeliverycostcalculator.services.WindSpeedFeeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Wind speed fee rule REST controller.
 */
@RestController
@RequestMapping("/api/fee-rule")
public class WindSpeedFeeRuleController
{
    private final WindSpeedFeeRuleService windSpeedFeeRuleService;

    /**
     * Constructor.
     * Field is autowired.
     *
     * @param windSpeedFeeRuleService Service for {@link WindSpeedFeeRule}
     */
    public WindSpeedFeeRuleController(@Autowired WindSpeedFeeRuleService windSpeedFeeRuleService)
    {
        this.windSpeedFeeRuleService = windSpeedFeeRuleService;
    }

    /**
     * Get all wind speed fee rules.
     *
     * @return Response of all wind speed fee rules.
     */
    @GetMapping("/wind-speed")
    public WindSpeedFeeRulesResponse getAll()
    {
        return new WindSpeedFeeRulesResponse(
                windSpeedFeeRuleService.getWindSpeedFeeRules()
        );
    }

    /**
     * Create a new wind speed fee rule.
     *
     * @param windSpeedFeeRuleRequest Request of a new wind speed fee rule.
     */
    @PostMapping("/wind-speed")
    public void create(@RequestBody WindSpeedFeeRuleRequest windSpeedFeeRuleRequest)
    {
        windSpeedFeeRuleService.addWindSpeedFeeRule(windSpeedFeeRuleRequest);
    }

    /**
     * Update a wind speed fee rule.
     *
     * @param id ID of the wind speed fee rule to update.
     */
    @DeleteMapping("/wind-speed/{id}")
    public void deleteWindSpeed(@PathVariable UUID id)
    {
        windSpeedFeeRuleService.deleteWindSpeedFeeRuleById(id);
    }

}
