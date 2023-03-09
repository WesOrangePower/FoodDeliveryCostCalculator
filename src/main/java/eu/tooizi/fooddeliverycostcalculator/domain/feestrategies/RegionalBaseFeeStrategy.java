package eu.tooizi.fooddeliverycostcalculator.domain.feestrategies;

import eu.tooizi.fooddeliverycostcalculator.domain.RegionalBaseFee;
import eu.tooizi.fooddeliverycostcalculator.services.exceptions.UndeliverableException;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class RegionalBaseFeeStrategy implements FeeStrategy
{

    private final String region;

    public RegionalBaseFeeStrategy(String region)
    {
        this.region = region;
    }

    @Override
    public double applyRule(double currentFee)
    {
        return currentFee + region.getRegionalBaseFee();

        throw new UndeliverableException(HttpStatusCode.valueOf(404), "Specified region not found.");
    }
}
