package eu.tooizi.fooddeliverycostcalculator.controllers;

import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.WindSpeedFeeRule;
import eu.tooizi.fooddeliverycostcalculator.DTOs.requests.WindSpeedFeeRuleRequest;
import eu.tooizi.fooddeliverycostcalculator.DTOs.responses.WindSpeedFeeRulesResponse;
import eu.tooizi.fooddeliverycostcalculator.services.WindSpeedFeeRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Fetches a collection of all wind speed fee rules found in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched a collection of all wind speed fee rules."),
            @ApiResponse(responseCode = "500", description = "Internal server error."),
    })
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
    @Operation(summary = "Create a new wind speed fee rule.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created a new wind speed fee rule."),
            @ApiResponse(responseCode = "500", description = "Internal server error or fee rule already exists."),
    })
    public void create(@RequestBody WindSpeedFeeRuleRequest windSpeedFeeRuleRequest)
    {
        windSpeedFeeRuleService.addWindSpeedFeeRule(windSpeedFeeRuleRequest);
    }

    /**
     * Delete a wind speed fee rule.
     *
     * @param id ID of the wind speed fee rule to delete.
     */
    @DeleteMapping("/wind-speed/{id}")
    @Operation(summary = "Delete a wind speed fee rule.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted a wind speed fee rule."),
            @ApiResponse(responseCode = "500", description = "Internal server error."),
    }
    )
    public void delete(@PathVariable UUID id)
    {
        windSpeedFeeRuleService.deleteWindSpeedFeeRuleById(id);
    }

}
