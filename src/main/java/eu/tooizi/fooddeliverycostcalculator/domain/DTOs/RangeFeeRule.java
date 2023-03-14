package eu.tooizi.fooddeliverycostcalculator.domain.DTOs;

public interface RangeFeeRule extends FeeRule
{
    Double getLowerBound();

    Double getUpperBound();

    double LOWEST_BOUND = -1000;
    double HIGHEST_BOUND = 1000;
}
