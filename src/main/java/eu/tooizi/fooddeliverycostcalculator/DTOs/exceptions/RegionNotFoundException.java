package eu.tooizi.fooddeliverycostcalculator.DTOs.exceptions;

/**
 * Exception that marks that a region was not found in the database.
 */
public class RegionNotFoundException extends RuntimeException
{

    /**
     * Constructor.
     *
     * @param message The message to be shown when the exception is thrown.
     */
    public RegionNotFoundException(String message)
    {
        super(message);
    }
}
