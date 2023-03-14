package eu.tooizi.fooddeliverycostcalculator.controllers;

import eu.tooizi.fooddeliverycostcalculator.domain.responses.DeliveryFeeResponse;
import eu.tooizi.fooddeliverycostcalculator.domain.responses.DeliveryFeeResponseIntermediate;
import eu.tooizi.fooddeliverycostcalculator.services.DeliveryFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for delivery-fee endpoint.
 */
@RestController
public class DeliveryFeeController
{

    private final DeliveryFeeService deliveryFeeService;

    public DeliveryFeeController(@Autowired DeliveryFeeService deliveryFeeService)
    {
        this.deliveryFeeService = deliveryFeeService;
    }

    @GetMapping("/api/delivery-fee")
    public ResponseEntity<DeliveryFeeResponse> getDeliveryFee(@RequestParam String region,
                                                              @RequestParam(name = "vehicle_type") String vehicleType)
    {
        DeliveryFeeResponseIntermediate intermediate = deliveryFeeService.getDeliveryFee(region, vehicleType);

        if (intermediate.isErrored())
        {
            return ResponseEntity.status(intermediate.getResponseCode())
                    .body(new DeliveryFeeResponse(intermediate.getErrorMessage(), Double.NaN));
        }
        return ResponseEntity.ok(new DeliveryFeeResponse("Ok", intermediate.getDeliveryFee()));
    }
}
