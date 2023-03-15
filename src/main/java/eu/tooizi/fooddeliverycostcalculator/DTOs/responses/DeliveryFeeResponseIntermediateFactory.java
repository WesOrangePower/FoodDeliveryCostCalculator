package eu.tooizi.fooddeliverycostcalculator.DTOs.responses;

import org.springframework.http.HttpStatusCode;

/**
 * Factory for {@link DeliveryFeeResponseIntermediate}
 */
public class DeliveryFeeResponseIntermediateFactory
{
    /**
     * Creates a successful response.
     *
     * @param fee Delivery fee.
     * @return Intermediate of delivery fee response.
     */
    public DeliveryFeeResponseIntermediate successful(double fee)
    {
        DeliveryFeeResponseIntermediate response = new DeliveryFeeResponseIntermediate();
        response.setErrored(false);
        response.setDeliveryFee(fee);
        response.setResponseCode(HttpStatusCode.valueOf(200));

        return response;
    }

    /**
     * Creates a failed response.
     *
     * @param httpErrorCode HTTP error code.
     * @param errorMessage  Error message.
     * @return Intermediate of delivery fee response.
     */
    public DeliveryFeeResponseIntermediate failed(HttpStatusCode httpErrorCode, String errorMessage)
    {
        DeliveryFeeResponseIntermediate response = new DeliveryFeeResponseIntermediate();
        response.setErrored(true);
        response.setResponseCode(httpErrorCode);
        response.setErrorMessage(errorMessage);

        return response;
    }
}
