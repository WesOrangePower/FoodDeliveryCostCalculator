package eu.tooizi.fooddeliverycostcalculator.domain.DTOs;

public interface FeeRule
{
    Boolean getDeliverable();

    Double getPriceDifference();

    Region getRegion();

    VehicleType getVehicleType();
}

