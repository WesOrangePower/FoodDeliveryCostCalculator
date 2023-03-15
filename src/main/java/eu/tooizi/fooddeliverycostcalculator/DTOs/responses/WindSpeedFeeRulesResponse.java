package eu.tooizi.fooddeliverycostcalculator.DTOs.responses;

import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.WindSpeedFeeRule;
import eu.tooizi.fooddeliverycostcalculator.controllers.WindSpeedFeeRuleController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * Response for {@link WindSpeedFeeRule} used in {@link WindSpeedFeeRuleController}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WindSpeedFeeRulesResponse
{
    Collection<WindSpeedFeeRule> rules;
}
