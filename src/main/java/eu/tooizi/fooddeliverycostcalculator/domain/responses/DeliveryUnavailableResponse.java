package eu.tooizi.fooddeliverycostcalculator.domain.responses;

public class DeliveryUnavailableResponse extends DeliveryFeeResponse
{
    private final String errorMessage;

    public DeliveryUnavailableResponse(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
}
