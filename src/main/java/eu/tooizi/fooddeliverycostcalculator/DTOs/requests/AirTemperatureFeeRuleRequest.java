package eu.tooizi.fooddeliverycostcalculator.DTOs.requests;

import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.WeatherPhenomenonFeeRule;
import eu.tooizi.fooddeliverycostcalculator.controllers.WeatherPhenomenonFeeRuleController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Request for {@link WeatherPhenomenonFeeRule} used in {@link WeatherPhenomenonFeeRuleController}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirTemperatureFeeRuleRequest
{
    private Double upperBound;
    private Double lowerBound;
    private Double priceDifference;
    private Boolean deliverable;
    private UUID regionId;
    private UUID vehicleTypeId;
}
