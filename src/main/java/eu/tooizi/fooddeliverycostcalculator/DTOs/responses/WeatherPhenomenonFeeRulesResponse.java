package eu.tooizi.fooddeliverycostcalculator.DTOs.responses;

import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.WeatherPhenomenonFeeRule;
import eu.tooizi.fooddeliverycostcalculator.controllers.WeatherPhenomenonFeeRuleController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * Response for {@link WeatherPhenomenonFeeRule} used in {@link WeatherPhenomenonFeeRuleController}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherPhenomenonFeeRulesResponse
{
    Collection<WeatherPhenomenonFeeRule> rules;
}
