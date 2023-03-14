package eu.tooizi.fooddeliverycostcalculator.domain.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryFeeResponse
{
    String message;
    double deliveryFee;
}
