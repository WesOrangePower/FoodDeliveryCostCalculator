package eu.tooizi.fooddeliverycostcalculator.controllers;

import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.AirTemperatureFeeRule;
import eu.tooizi.fooddeliverycostcalculator.DTOs.requests.AirTemperatureFeeRuleRequest;
import eu.tooizi.fooddeliverycostcalculator.DTOs.responses.AirTemperatureFeeRulesResponse;
import eu.tooizi.fooddeliverycostcalculator.services.AirTemperatureFeeRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Fetches a collection of all air temperature fee rules found in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched all air temperature fee rules."),
            @ApiResponse(responseCode = "500", description = "Internal server error."),
    })
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
    @Operation(summary = "Create a new air temperature fee rule.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created a new air temperature fee rule."),
            @ApiResponse(responseCode = "500", description = "Internal server error or fee rule already exists."),
    })
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
    @Operation(summary = "Delete an air temperature fee rule.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted an air temperature fee rule."),
            @ApiResponse(responseCode = "500", description = "Internal server error."),
    })
    public void delete(@PathVariable UUID id)
    {
        airTemperatureFeeRuleService.deleteAirTemperatureFeeRuleById(id);
    }
}
