package eu.tooizi.fooddeliverycostcalculator.repositories;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.AirTemperatureFeeRule;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.Region;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.VehicleType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface AirTemperatureFeeRuleRepository extends CrudRepository<AirTemperatureFeeRule, UUID>
{
    @Query("select a from AirTemperatureFeeRule a " +
            "where a.vehicleType = ?1" +
            "and a.region = ?2" +
            "and (?3 >= coalesce(a.lowerBound, -1000) and ?3 < coalesce(a.upperBound, 1000))")
    Optional<AirTemperatureFeeRule> findFirstByVehicleTypeAndRegionAndBound(VehicleType vehicleType,
                                                                            Region region,
                                                                            double airTemperature);

}
