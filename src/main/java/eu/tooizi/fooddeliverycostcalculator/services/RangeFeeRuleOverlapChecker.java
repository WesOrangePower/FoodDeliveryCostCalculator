package eu.tooizi.fooddeliverycostcalculator.services;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.RangeFeeRule;

import java.util.Optional;

public class RangeFeeRuleOverlapChecker<T extends RangeFeeRule>
{
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

    public boolean isOverlapping(T newRule, T existingRule)
    {
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

        return existingLower <= newUpper && newLower <= existingUpper;
    }
}
