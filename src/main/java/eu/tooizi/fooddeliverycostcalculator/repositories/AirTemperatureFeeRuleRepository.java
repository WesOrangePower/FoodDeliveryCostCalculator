package eu.tooizi.fooddeliverycostcalculator.repositories;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.AirTemperatureFeeRule;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.VehicleType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface AirTemperatureFeeRuleRepository extends CrudRepository<AirTemperatureFeeRule, UUID>
{
    @Query("select a from AirTemperatureFeeRule a " +
            "where a.vehicleType = ?1 and (?2 >= coalesce(a.lowerBound, -1000) and ?2 < coalesce(a.upperBound, 1000))")
    Optional<AirTemperatureFeeRule> findFirstByVehicleTypeAndBound(VehicleType vehicleType,
                                                                   double airTemperature);

}
