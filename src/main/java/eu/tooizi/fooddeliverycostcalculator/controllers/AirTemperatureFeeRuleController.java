package eu.tooizi.fooddeliverycostcalculator.controllers;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.AirTemperatureFeeRule;
import eu.tooizi.fooddeliverycostcalculator.domain.requests.AirTemperatureFeeRuleRequest;
import eu.tooizi.fooddeliverycostcalculator.domain.responses.AirTemperatureFeeRulesResponse;
import eu.tooizi.fooddeliverycostcalculator.services.AirTemperatureFeeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Air temperature fee rule REST controller.
 */
@RestController
@RequestMapping("/api/fee-rule")
public class AirTemperatureFeeRuleController
{
    private final AirTemperatureFeeRuleService airTemperatureFeeRuleService;

    /**
     * Constructor.
     *
     * @param airTemperatureFeeRuleService Service for {@link AirTemperatureFeeRule}
     */
    public AirTemperatureFeeRuleController(@Autowired AirTemperatureFeeRuleService airTemperatureFeeRuleService)
    {
        this.airTemperatureFeeRuleService = airTemperatureFeeRuleService;
    }

    /**
     * Fetches a collection of all {@link AirTemperatureFeeRule} found in the database.
     *
     * @return Response of all air temperature fee rules.
     */
    @GetMapping("/air-temperature")
    public AirTemperatureFeeRulesResponse getAll()
    {
        return new AirTemperatureFeeRulesResponse(
                airTemperatureFeeRuleService.getAirTemperatureFeeRules()
        );
    }

    /**
     * Create a new {@link AirTemperatureFeeRule}
     *
     * @param airTemperatureFeeRuleRequest Request of a new air temperature fee rule.
     */
    @PostMapping("/air-temperature")
    public void create(@RequestBody AirTemperatureFeeRuleRequest airTemperatureFeeRuleRequest)
    {
        airTemperatureFeeRuleService.addAirTemperatureFeeRule(airTemperatureFeeRuleRequest);
    }

    /**
     * Delete a {@link AirTemperatureFeeRule}
     *
     * @param id ID of the air temperature fee rule to delete.
     */
    @DeleteMapping("/air-temperature/{id}")
    public void delete(@PathVariable UUID id)
    {
        airTemperatureFeeRuleService.deleteAirTemperatureFeeRuleById(id);
    }
}
