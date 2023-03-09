package eu.tooizi.fooddeliverycostcalculator.domain;

import eu.tooizi.fooddeliverycostcalculator.domain.feestrategies.FeeStrategy;
import eu.tooizi.fooddeliverycostcalculator.services.exceptions.UndeliverableException;

public class DeliveryFee
{
    private double fee;

    public void applyFee(FeeStrategy rule) throws UndeliverableException
    {
        fee = rule.applyRule(fee);
    }

    public DeliveryFee(double fee)
    {
        this.fee = fee;
    }

    public DeliveryFee()
    {
    }

    public double getFee()
    {
        return fee;
    }

    public void setFee(double fee)
    {
        this.fee = fee;
    }
}
