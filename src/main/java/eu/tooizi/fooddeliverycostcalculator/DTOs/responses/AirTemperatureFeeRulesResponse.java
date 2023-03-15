package eu.tooizi.fooddeliverycostcalculator.DTOs.responses;

import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.AirTemperatureFeeRule;
import eu.tooizi.fooddeliverycostcalculator.controllers.AirTemperatureFeeRuleController;
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
