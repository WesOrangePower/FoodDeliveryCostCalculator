package eu.tooizi.fooddeliverycostcalculator.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherPhenomenonFeeRuleRequest
{
    private Double priceDifference;
    private Boolean deliverable;
    private UUID regionId;
    private UUID vehicleTypeId;
    private UUID phenomenonCategoryId;
}
