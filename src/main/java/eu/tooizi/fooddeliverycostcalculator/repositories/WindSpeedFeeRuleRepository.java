package eu.tooizi.fooddeliverycostcalculator.repositories;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.VehicleType;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WindSpeedFeeRule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface WindSpeedFeeRuleRepository extends CrudRepository<WindSpeedFeeRule, UUID>
{
    @Query("select w from WindSpeedFeeRule w " +
            "where w.vehicleType = ?1 and (?2 >= coalesce(w.lowerBound, -1000) and ?2 < coalesce(w.upperBound, 1000))")
    Optional<WindSpeedFeeRule> findFirstByVehicleTypeAndBound(VehicleType vehicleType,
                                                              Double windSpeedMps);
}
