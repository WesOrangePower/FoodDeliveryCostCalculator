package eu.tooizi.fooddeliverycostcalculator.DTOs.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

/**
 * Intermediate response for delivery fee.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryFeeResponseIntermediate
{
    double deliveryFee;
    boolean errored;
    HttpStatusCode responseCode;
    String errorMessage;
}
