package eu.tooizi.fooddeliverycostcalculator.domain.feestrategies;


import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.VehicleType;

public class VehicleTypeFeeStrategy implements FeeStrategy{

    public VehicleTypeFeeStrategy(VehicleType vehicleType)
    {
    }

    @Override
    public double applyRule(double currentFee)
    {
        return 0;
    }
}
