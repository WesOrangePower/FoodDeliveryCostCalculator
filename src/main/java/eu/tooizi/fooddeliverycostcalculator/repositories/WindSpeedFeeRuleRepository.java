package eu.tooizi.fooddeliverycostcalculator.repositories;

import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.Region;
import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.VehicleType;
import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.WindSpeedFeeRule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for {@link WindSpeedFeeRule}
 */
public interface WindSpeedFeeRuleRepository extends CrudRepository<WindSpeedFeeRule, UUID>
{
    /**
     * Fetches the first wind speed fee rule that matches the given vehicle type, region and wind speed.
     *
     * @param vehicleType  The vehicle type.
     * @param region       The region.
     * @param windSpeedMps The wind speed in m/s.
     * @return The first matching wind speed fee rule.
     */
    @Query("select w from WindSpeedFeeRule w " +
            "where w.vehicleType = :vehicleType " +
            "and w.region = :region " +
            "and :windSpeed >= coalesce(w.lowerBound, -1000) " +
            "and :windSpeed < coalesce(w.upperBound, 1000)")
    Optional<WindSpeedFeeRule> findFirstByVehicleTypeAndRegionAndBound(
            @Param("vehicleType") VehicleType vehicleType,
            @Param("region") Region region,
            @Param("windSpeed") Double windSpeedMps
    );
}
