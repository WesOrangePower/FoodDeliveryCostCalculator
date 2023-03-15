package eu.tooizi.fooddeliverycostcalculator.domain.responses;

import eu.tooizi.fooddeliverycostcalculator.controllers.WindSpeedFeeRuleController;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WindSpeedFeeRule;
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
