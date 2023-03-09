package eu.tooizi.fooddeliverycostcalculator.domain.responses;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.PhenomenonCategory;
import eu.tooizi.fooddeliverycostcalculator.domain.feestrategies.FeeStrategy;

public class PhenomenonCategoryFeeStrategy implements FeeStrategy
{
    public PhenomenonCategoryFeeStrategy(PhenomenonCategory phenomenonCategory)
    {
    }

    @Override
    public double applyRule(double currentFee)
    {
        return 0;
    }
}
