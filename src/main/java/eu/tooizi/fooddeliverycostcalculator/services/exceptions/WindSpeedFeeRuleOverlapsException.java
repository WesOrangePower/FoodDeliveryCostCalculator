package eu.tooizi.fooddeliverycostcalculator.services.exceptions;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.AirTemperatureFeeRule;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WindSpeedFeeRule;
import lombok.Getter;

public class WindSpeedFeeRuleOverlapsException extends RuntimeException
{

    @Getter
    private final WindSpeedFeeRule overlappingRule;

    public WindSpeedFeeRuleOverlapsException(String message, WindSpeedFeeRule overlappingRule)
    {
        super(message);
        this.overlappingRule = overlappingRule;
    }
}
