package eu.tooizi.fooddeliverycostcalculator.DTOs.domain;

/**
 * Fee rule interface for generic fee rules.
 */
public interface FeeRule
{
    /**
     * Getter for deliverable.
     *
     * @return Whether the rule prohibits delivery.
     */
    Boolean getDeliverable();

    /**
     * Getter for price difference.
     *
     * @return Price difference.
     */
    Double getPriceDifference();

    /**
     * Getter for region.
     *
     * @return Region.
     */
    Region getRegion();

    /**
     * Getter for vehicle type.
     *
     * @return Vehicle type.
     */
    VehicleType getVehicleType();
}

