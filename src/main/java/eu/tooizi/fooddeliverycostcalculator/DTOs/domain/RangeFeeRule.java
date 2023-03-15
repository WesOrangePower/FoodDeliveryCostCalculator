package eu.tooizi.fooddeliverycostcalculator.DTOs.domain;

/**
 * Range fee rule interface.
 * Used for rules that have a lower and upper bound, both of which are optional.
 */
public interface RangeFeeRule extends FeeRule
{
    /**
     * Value for unset lower bound null-coalescing.
     */
    double LOWEST_BOUND = -1000;
    /**
     * Value for unset upper bound null-coalescing.
     */
    double HIGHEST_BOUND = 1000;

    /**
     * Getter for lower bound.
     *
     * @return Lower bound. Nullable.
     */
    Double getLowerBound();

    /**
     * Getter for upper bound.
     *
     * @return Upper bound. Nullable.
     */
    Double getUpperBound();
}
