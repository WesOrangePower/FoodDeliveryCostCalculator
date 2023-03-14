package eu.tooizi.fooddeliverycostcalculator.services.exceptions;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.AirTemperatureFeeRule;
import lombok.Getter;

public class AirTemperatureFeeRuleOverlapsException extends RuntimeException
{

    @Getter
    private final AirTemperatureFeeRule overlappingRule;

    public AirTemperatureFeeRuleOverlapsException(String message, AirTemperatureFeeRule overlappingRule)
    {
        super(message);
        this.overlappingRule = overlappingRule;
    }
}
