package eu.tooizi.fooddeliverycostcalculator.services;

import eu.tooizi.fooddeliverycostcalculator.domain.DeliveryFee;
import eu.tooizi.fooddeliverycostcalculator.domain.feestrategies.RegionalBaseFeeStrategy;
import eu.tooizi.fooddeliverycostcalculator.domain.feestrategies.VehicleTypeFeeStrategy;
import eu.tooizi.fooddeliverycostcalculator.domain.responses.DeliveryFeeResponse;
import eu.tooizi.fooddeliverycostcalculator.domain.responses.DeliveryUnavailableResponse;
import eu.tooizi.fooddeliverycostcalculator.domain.responses.PhenomenonCategoryFeeStrategy;
import eu.tooizi.fooddeliverycostcalculator.services.exceptions.UndeliverableException;
import org.springframework.stereotype.Service;

@Service
public class DeliveryFeeService
{
    public DeliveryFeeResponse getDeliveryFee(String region, String vehicleType)
    {
        DeliveryFee deliveryFee = new DeliveryFee(0);
        try
        {
            deliveryFee.applyFee(new RegionalBaseFeeStrategy(region));
            deliveryFee.applyFee(new VehicleTypeFeeStrategy(vehicleType));
            deliveryFee.applyFee(new PhenomenonCategoryFeeStrategy());
        } catch (UndeliverableException e)
        {
            return new DeliveryUnavailableResponse(e.getMessage());
        }
        return new DeliveryFeeResponse();
    }
}
