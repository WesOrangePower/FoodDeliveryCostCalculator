package eu.tooizi.fooddeliverycostcalculator.repositories;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.PhenomenonCategory;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.Region;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.VehicleType;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WeatherPhenomenonFeeRule;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for {@link WeatherPhenomenonFeeRule}
 */
public interface WeatherPhenomenonFeeRuleRepository extends CrudRepository<WeatherPhenomenonFeeRule, UUID>
{
    /**
     * Fetches the first weather phenomenon fee rule that matches the given vehicle type, region and weather phenomenon category.
     *
     * @param vehicleType        The vehicle type
     * @param region             The region
     * @param phenomenonCategory The weather phenomenon category
     * @return The first matching weather phenomenon fee rule.
     */
    Optional<WeatherPhenomenonFeeRule> findByVehicleTypeAndRegionAndPhenomenonCategory(VehicleType vehicleType,
                                                                                       Region region,
                                                                                       PhenomenonCategory phenomenonCategory);

}
