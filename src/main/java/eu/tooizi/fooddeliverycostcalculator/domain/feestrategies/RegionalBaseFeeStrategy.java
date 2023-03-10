package eu.tooizi.fooddeliverycostcalculator.domain.feestrategies;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.RegionalBaseFee;
import eu.tooizi.fooddeliverycostcalculator.services.exceptions.UndeliverableException;
import org.springframework.http.HttpStatusCode;

public class RegionalBaseFeeStrategy implements FeeStrategy
{
    private final RegionalBaseFee regionalBaseFee;

    public RegionalBaseFeeStrategy(RegionalBaseFee regionalBaseFee)
    {
        if (regionalBaseFee == null) {
            throw new UndeliverableException(HttpStatusCode.valueOf(404), "Specified region not found.");
        }

        this.regionalBaseFee = regionalBaseFee;
    }

    @Override
    public double applyRule(double currentFee)
    {
        return currentFee + regionalBaseFee.getBaseFee();
    }
}
