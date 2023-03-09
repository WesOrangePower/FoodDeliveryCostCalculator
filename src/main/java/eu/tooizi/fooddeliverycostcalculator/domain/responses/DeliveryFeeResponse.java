package eu.tooizi.fooddeliverycostcalculator.domain.responses;

public class DeliveryFeeResponse
{
    private double deliveryFee;

    public DeliveryFeeResponse()
    {
    }

    public DeliveryFeeResponse(double deliveryFee)
    {
        this.deliveryFee = deliveryFee;
    }

    public double getDeliveryFee()
    {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee)
    {
        this.deliveryFee = deliveryFee;
    }
}
