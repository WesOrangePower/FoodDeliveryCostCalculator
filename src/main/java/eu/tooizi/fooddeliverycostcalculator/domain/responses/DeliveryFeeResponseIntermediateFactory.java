package eu.tooizi.fooddeliverycostcalculator.domain.responses;

import org.springframework.http.HttpStatusCode;

public class DeliveryFeeResponseIntermediateFactory
{
    public DeliveryFeeResponseIntermediate successful(double fee)
    {
        DeliveryFeeResponseIntermediate response = new DeliveryFeeResponseIntermediate();
        response.setErrored(false);
        response.setDeliveryFee(fee);
        response.setResponseCode(HttpStatusCode.valueOf(200));

        return response;
    }

    public DeliveryFeeResponseIntermediate failed(HttpStatusCode httpErrorCode, String errorMessage)
    {
        DeliveryFeeResponseIntermediate response = new DeliveryFeeResponseIntermediate();
        response.setErrored(true);
        response.setResponseCode(httpErrorCode);
        response.setErrorMessage(errorMessage);

        return response;
    }
}
