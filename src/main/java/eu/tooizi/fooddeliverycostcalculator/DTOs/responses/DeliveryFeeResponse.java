package eu.tooizi.fooddeliverycostcalculator.DTOs.responses;

import eu.tooizi.fooddeliverycostcalculator.controllers.DeliveryFeeController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response used in {@link DeliveryFeeController}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryFeeResponse
{
    String message;
    double deliveryFee;
}
