package eu.tooizi.fooddeliverycostcalculator.domain.responses;

import eu.tooizi.fooddeliverycostcalculator.controllers.WeatherPhenomenonFeeRuleController;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WeatherPhenomenonFeeRule;
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
