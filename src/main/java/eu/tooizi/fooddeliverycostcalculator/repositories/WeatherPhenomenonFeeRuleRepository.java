package eu.tooizi.fooddeliverycostcalculator.repositories;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.PhenomenonCategory;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.Region;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.VehicleType;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WeatherPhenomenonFeeRule;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface WeatherPhenomenonFeeRuleRepository extends CrudRepository<WeatherPhenomenonFeeRule, UUID>
{
    Optional<WeatherPhenomenonFeeRule> findByVehicleTypeAndRegionAndPhenomenonCategory(VehicleType vehicleType,
                                                                                       Region region,
                                                                                       PhenomenonCategory weatherPhenomenon);

}
