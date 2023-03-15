package eu.tooizi.fooddeliverycostcalculator.services.exceptions;

/**
 * Exception that marks that a requested vehicle type was not found in the database.
 */
public class VehicleNotFoundException extends RuntimeException
{
    /**
     * Constructor matching super
     *
     * @param message Message to be passed to the super constructor.
     */
    public VehicleNotFoundException(String message)
    {
        super(message);
    }
}
