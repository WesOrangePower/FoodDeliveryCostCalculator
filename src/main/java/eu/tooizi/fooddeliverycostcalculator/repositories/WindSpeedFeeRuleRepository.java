package eu.tooizi.fooddeliverycostcalculator.repositories;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.Region;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.VehicleType;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WindSpeedFeeRule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface WindSpeedFeeRuleRepository extends CrudRepository<WindSpeedFeeRule, UUID>
{
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
