package eu.tooizi.fooddeliverycostcalculator.domain.feestrategies;

public class AirTemperatureFeeStrategy implements FeeStrategy
{
    private final double temperatureCelsius;
    private final String vehicleType;

    public AirTemperatureFeeStrategy(double temperatureCelsius, String vehicleType)
    {
        this.temperatureCelsius = temperatureCelsius;
        this.vehicleType = vehicleType;
    }

    @Override
    public double applyRule(double currentFee)
    {
        if (vehicleType.equals("Car"))
        {
            return currentFee;
        }

        double addition = 0d;
        if (temperatureCelsius < -10d)
        {
            addition += 1d;
        } else if (temperatureCelsius >= -10d && temperatureCelsius <= 0d)
        {
            addition += .5;
        }

        return currentFee += addition;
    }
}
