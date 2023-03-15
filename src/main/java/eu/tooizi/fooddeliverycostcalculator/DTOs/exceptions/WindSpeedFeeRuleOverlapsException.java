package eu.tooizi.fooddeliverycostcalculator.DTOs.exceptions;

import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.WindSpeedFeeRule;
import lombok.Getter;

/**
 * Exception that marks that a wind speed fee rule overlaps with another one.
 */
public class WindSpeedFeeRuleOverlapsException extends RuntimeException
{

    @Getter
    private final WindSpeedFeeRule overlappingRule;

    /**
     * Constructor.
     *
     * @param message         Message for the exception.
     * @param overlappingRule The overlapping rule.
     */
    public WindSpeedFeeRuleOverlapsException(String message, WindSpeedFeeRule overlappingRule)
    {
        super(message);
        this.overlappingRule = overlappingRule;
    }
}
