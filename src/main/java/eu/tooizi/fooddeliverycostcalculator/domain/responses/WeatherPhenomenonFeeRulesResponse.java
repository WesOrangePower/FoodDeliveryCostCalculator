package eu.tooizi.fooddeliverycostcalculator.domain.responses;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WeatherPhenomenonFeeRule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherPhenomenonFeeRulesResponse
{
    Collection<WeatherPhenomenonFeeRule> rules;
}
