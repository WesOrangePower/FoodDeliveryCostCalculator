package eu.tooizi.fooddeliverycostcalculator.services.exceptions;

/**
 * Exception that marks that a requested phenomenon category was not found in the database.
 */
public class PhenomenonCategoryNotFoundException extends RuntimeException
{
    /**
     * Constructor matching super
     *
     * @param message Message to be passed to the super constructor.
     */
    public PhenomenonCategoryNotFoundException(String message)
    {
        super(message);
    }
}
