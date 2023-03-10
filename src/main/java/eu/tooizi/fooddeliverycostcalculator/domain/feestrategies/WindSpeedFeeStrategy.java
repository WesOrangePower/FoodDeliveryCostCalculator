package eu.tooizi.fooddeliverycostcalculator.domain.feestrategies;

import eu.tooizi.fooddeliverycostcalculator.services.exceptions.UndeliverableException;
import org.springframework.http.HttpStatusCode;

public class WindSpeedFeeStrategy implements FeeStrategy
{
    private final double windSpeedMps;
    private final String vehicleType;

    public WindSpeedFeeStrategy(double windSpeedMps, String vehicleType)
    {

        this.windSpeedMps = windSpeedMps;
        this.vehicleType = vehicleType;
    }

    @Override
    public double applyRule(double currentFee)
    {
        if (!vehicleType.equals("Bike")) {
            return currentFee;
        }

        if (windSpeedMps > 20d) {
            throw new UndeliverableException(HttpStatusCode.valueOf(200),
                    "Usage of selected vehicle type is forbidden"
            );
        }
        if (windSpeedMps > 10d)
        {
            return currentFee + .5;
        }
        return currentFee;
    }
}
