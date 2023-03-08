package eu.tooizi.fooddeliverycostcalculator.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeliveryFeeController
{

    @GetMapping("/delivery-fee")
    public String getDeliveryFee(String city, String vehicleType)
    {
        return null;
    }
}
