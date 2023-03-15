package eu.tooizi.fooddeliverycostcalculator.services;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.RangeFeeRule;

import java.util.Optional;

/**
 * Utility class for checking if two {@link RangeFeeRule} objects overlap.
 *
 * @param <T> The type of the {@link RangeFeeRule} objects.
 */
public class RangeFeeRuleOverlapChecker<T extends RangeFeeRule>
{
    /**
     * Checks if a new {@link RangeFeeRule} overlaps with any of the existing rules.
     *
     * @param newRule       The new rule.
     * @param existingRules The existing rules.
     * @return The existing rule that overlaps with the new rule, if any.
     */
    public Optional<T> findOverlap(T newRule, Iterable<T> existingRules)
    {
        for (T existingRule : existingRules)
        {
            if (isOverlapping(newRule, existingRule))
            {
                return Optional.of(existingRule);
            }
        }
        return Optional.empty();
    }

    /**
     * Checks if two {@link RangeFeeRule} objects overlap.
     *
     * @param newRule      The new rule.
     * @param existingRule The existing rule.
     * @return True if the rules overlap, false otherwise.
     */
    public boolean isOverlapping(T newRule, T existingRule)
    {
        boolean regionsAreEqual = newRule.getRegion().getId().equals(existingRule.getRegion().getId());
        boolean vehicleTypesAreEqual = newRule.getVehicleType().getId().equals(existingRule.getVehicleType().getId());

        if (!regionsAreEqual || !vehicleTypesAreEqual)
        {
            return false;
        }

        var newLower = newRule.getLowerBound() != null
                ? newRule.getLowerBound()
                : RangeFeeRule.LOWEST_BOUND;
        var newUpper = newRule.getUpperBound() != null
                ? newRule.getUpperBound()
                : RangeFeeRule.HIGHEST_BOUND;

        var existingLower = existingRule.getLowerBound() != null
                ? existingRule.getLowerBound()
                : RangeFeeRule.LOWEST_BOUND;
        var existingUpper = existingRule.getUpperBound() != null
                ? existingRule.getUpperBound()
                : RangeFeeRule.HIGHEST_BOUND;

        return existingLower < newUpper && newLower < existingUpper;
    }
}
