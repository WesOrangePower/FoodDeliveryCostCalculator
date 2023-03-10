package eu.tooizi.fooddeliverycostcalculator.domain.responses;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.PhenomenonCategory;
import eu.tooizi.fooddeliverycostcalculator.domain.feestrategies.FeeStrategy;
import eu.tooizi.fooddeliverycostcalculator.services.exceptions.UndeliverableException;
import org.springframework.http.HttpStatusCode;

public class PhenomenonCategoryFeeStrategy implements FeeStrategy
{
    private final PhenomenonCategory phenomenonCategory;

    public PhenomenonCategoryFeeStrategy(PhenomenonCategory phenomenonCategory)
    {
        this.phenomenonCategory = phenomenonCategory;
    }

    @Override
    public double applyRule(double currentFee)
    {
        String name = phenomenonCategory.getName();
        if (name.equals(PhenomenonCategory.RAIN)) {
            return currentFee + .5;
        }
        if (name.equals(PhenomenonCategory.SNOW) ||
                name.equals(PhenomenonCategory.SLEET)) {
            return currentFee + 1;
        }
        if (name.equals(PhenomenonCategory.GLAZE) ||
            name.equals(PhenomenonCategory.HAIL) ||
            name.equals(PhenomenonCategory.THUNDER)) {
            throw new UndeliverableException(
                    HttpStatusCode.valueOf(200),
                    "Usage of selected vehicle type is forbidden"
            );
        }
        return currentFee;
    }
}
