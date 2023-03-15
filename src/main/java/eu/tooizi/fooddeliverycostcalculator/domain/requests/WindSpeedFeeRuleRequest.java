package eu.tooizi.fooddeliverycostcalculator.domain.requests;

import eu.tooizi.fooddeliverycostcalculator.controllers.WindSpeedFeeRuleController;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WindSpeedFeeRule;
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
