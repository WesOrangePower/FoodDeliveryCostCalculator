package eu.tooizi.fooddeliverycostcalculator.services.exceptions;

public class VehicleNotFoundException extends RuntimeException
{
    public VehicleNotFoundException(String message)
    {
        super(message);
    }
}
