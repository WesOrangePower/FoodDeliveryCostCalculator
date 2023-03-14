package eu.tooizi.fooddeliverycostcalculator.repositories;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.AirTemperatureFeeRule;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.Region;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.VehicleType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface AirTemperatureFeeRuleRepository extends CrudRepository<AirTemperatureFeeRule, UUID>
{
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
