package eu.tooizi.fooddeliverycostcalculator.repositories;

import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.AirTemperatureFeeRule;
import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.Region;
import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.VehicleType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for {@link AirTemperatureFeeRule}
 */
public interface AirTemperatureFeeRuleRepository extends CrudRepository<AirTemperatureFeeRule, UUID>
{
    /**
     * Fetches the first air temperature fee rule that matches the given vehicle type, region and air temperature.
     *
     * @param vehicleType    The vehicle type
     * @param region         The region
     * @param airTemperature The air temperature in °C
     * @return The first matching air temperature fee rule.
     */
    @Query("select a from AirTemperatureFeeRule a " +
            "where a.vehicleType = :vehicleType " +
            "and a.region = :region " +
            "and :airTemperature >= coalesce(a.lowerBound, -1000) " +
            "and :airTemperature < coalesce(a.upperBound, 1000)")
    Optional<AirTemperatureFeeRule> findFirstByVehicleTypeAndRegionAndBound(
            @Param("vehicleType") VehicleType vehicleType,
            @Param("region") Region region,
            @Param("airTemperature") double airTemperature
    );

}
