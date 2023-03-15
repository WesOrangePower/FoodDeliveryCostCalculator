package eu.tooizi.fooddeliverycostcalculator.domain.requests;

import eu.tooizi.fooddeliverycostcalculator.controllers.WeatherPhenomenonFeeRuleController;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WeatherPhenomenonFeeRule;
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
