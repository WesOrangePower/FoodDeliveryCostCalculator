package eu.tooizi.fooddeliverycostcalculator.domain.responses;

import org.springframework.http.HttpStatusCode;

public class DeliveryFeeResponseFactory
{
    public DeliveryFeeResponse successful(double fee)
    {
        DeliveryFeeResponse response = new DeliveryFeeResponse();
        response.setErrored(false);
        response.setDeliveryFee(fee);
        response.setResponseCode(HttpStatusCode.valueOf(200));

        return response;
    }

    public DeliveryFeeResponse failed(HttpStatusCode httpErrorCode, String errorMessage)
    {
        DeliveryFeeResponse response = new DeliveryFeeResponse();
        response.setErrored(true);
        response.setResponseCode(httpErrorCode);
        response.setErrorMessage(errorMessage);

        return response;
    }
}
