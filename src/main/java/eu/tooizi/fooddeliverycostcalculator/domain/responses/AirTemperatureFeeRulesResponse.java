package eu.tooizi.fooddeliverycostcalculator.domain.responses;

import eu.tooizi.fooddeliverycostcalculator.controllers.AirTemperatureFeeRuleController;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.AirTemperatureFeeRule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * Response for {@link AirTemperatureFeeRule} used in {@link AirTemperatureFeeRuleController}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirTemperatureFeeRulesResponse
{
    Collection<AirTemperatureFeeRule> rules;
}
