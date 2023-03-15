package eu.tooizi.fooddeliverycostcalculator.DTOs.exceptions;

import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.AirTemperatureFeeRule;
import lombok.Getter;

/**
 * Exception thrown when a new {@link AirTemperatureFeeRule} overlaps with an existing one.
 */
public class AirTemperatureFeeRuleOverlapsException extends RuntimeException
{

    @Getter
    private final AirTemperatureFeeRule overlappingRule;

    /**
     * Constructor.
     *
     * @param message         the exception message
     * @param overlappingRule the overlapping rule
     */
    public AirTemperatureFeeRuleOverlapsException(String message, AirTemperatureFeeRule overlappingRule)
    {
        super(message);
        this.overlappingRule = overlappingRule;
    }
}
