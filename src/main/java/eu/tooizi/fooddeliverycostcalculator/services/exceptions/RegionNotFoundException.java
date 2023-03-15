package eu.tooizi.fooddeliverycostcalculator.services.exceptions;

public class RegionNotFoundException extends RuntimeException
{

    public RegionNotFoundException(String message)
    {
        super(message);
    }
}
