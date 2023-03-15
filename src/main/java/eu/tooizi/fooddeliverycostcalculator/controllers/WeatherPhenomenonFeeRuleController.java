package eu.tooizi.fooddeliverycostcalculator.controllers;

import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.WeatherPhenomenonFeeRule;
import eu.tooizi.fooddeliverycostcalculator.DTOs.requests.WeatherPhenomenonFeeRuleRequest;
import eu.tooizi.fooddeliverycostcalculator.DTOs.responses.WeatherPhenomenonFeeRulesResponse;
import eu.tooizi.fooddeliverycostcalculator.services.WeatherPhenomenonFeeRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Weather phenomenon fee rule REST controller.
 */
@RestController
@RequestMapping("/api/fee-rule")
public class WeatherPhenomenonFeeRuleController
{
    private final WeatherPhenomenonFeeRuleService weatherPhenomenonFeeRuleService;

    /**
     * Constructor.
     * Field is autowired.
     *
     * @param weatherPhenomenonFeeRuleService Service for {@link WeatherPhenomenonFeeRule}
     */
    public WeatherPhenomenonFeeRuleController(@Autowired WeatherPhenomenonFeeRuleService weatherPhenomenonFeeRuleService)
    {
        this.weatherPhenomenonFeeRuleService = weatherPhenomenonFeeRuleService;
    }

    /**
     * Fetches all weather phenomenon fee rules.
     *
     * @return Response of all weather phenomenon fee rules.
     */
    @GetMapping("/weather-phenomenon")
    @Operation(summary = "Fetches a collection of all weather phenomenon fee rules found in the database.")
    public WeatherPhenomenonFeeRulesResponse getAll()
    {
        return new WeatherPhenomenonFeeRulesResponse(
                weatherPhenomenonFeeRuleService.getWeatherPhenomenonFeeRules()
        );
    }

    /**
     * Creates a new weather phenomenon fee rule.
     *
     * @param weatherPhenomenonFeeRuleRequest Request of a new weather phenomenon fee rule.
     */
    @PostMapping("/weather-phenomenon")
    @Operation(summary = "Create a new weather phenomenon fee rule.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created a new weather phenomenon fee rule."),
            @ApiResponse(responseCode = "500", description = "Internal server error."),
    })
    public void create(@RequestBody WeatherPhenomenonFeeRuleRequest weatherPhenomenonFeeRuleRequest)
    {
        weatherPhenomenonFeeRuleService.addWeatherPhenomenonFeeRule(weatherPhenomenonFeeRuleRequest);
    }

    /**
     * Delete a weather phenomenon fee rule.
     *
     * @param id ID of the weather phenomenon fee rule to delete.
     */
    @DeleteMapping("/weather-phenomenon/{id}")
    @Operation(summary = "Delete a weather phenomenon fee rule.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted a weather phenomenon fee rule."),
            @ApiResponse(responseCode = "500", description = "Internal server error."),
    })
    public void delete(@PathVariable UUID id)
    {
        weatherPhenomenonFeeRuleService.deleteWeatherPhenomenonFeeRuleById(id);
    }
}
