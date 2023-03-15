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

    /**
     * Constructor.
     * Field is autowired.
     *
     * @param deliveryFeeService Service for calculating delivery fee.
     */
    public DeliveryFeeController(@Autowired DeliveryFeeService deliveryFeeService)
    {
        this.deliveryFeeService = deliveryFeeService;
    }

    /**
     * Get method for delivery-fee endpoint.
     * Calculates delivery fee based on region and vehicle type.
     * May fail if region or vehicle type is not found.
     * May fail if there is a rule that prohibits delivery.
     *
     * @param region      Region of delivery.
     * @param vehicleType Vehicle type of delivery.
     * @return Response of delivery fee.
     */
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
