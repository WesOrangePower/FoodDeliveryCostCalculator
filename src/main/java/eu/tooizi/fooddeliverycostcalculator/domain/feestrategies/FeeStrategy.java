package eu.tooizi.fooddeliverycostcalculator.domain.feestrategies;

/**
 * Strategy interface for fee business rules
 */
public interface FeeStrategy
{
    public double applyRule(double currentFee);
}
