package eu.tooizi.fooddeliverycostcalculator.DTOs.requests;

import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.WindSpeedFeeRule;
import eu.tooizi.fooddeliverycostcalculator.controllers.WindSpeedFeeRuleController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Request for {@link WindSpeedFeeRule} used in {@link WindSpeedFeeRuleController}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WindSpeedFeeRuleRequest
{

    private Double upperBound;
    private Double lowerBound;
    private Double priceDifference;
    private Boolean deliverable;
    private UUID regionId;
    private UUID vehicleTypeId;
}
