package eu.tooizi.fooddeliverycostcalculator.domain.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryFeeResponse
{
    double deliveryFee;
    boolean errored;
    HttpStatusCode responseCode;
    String errorMessage;
}
