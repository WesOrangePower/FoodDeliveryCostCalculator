package eu.tooizi.fooddeliverycostcalculator.repositories;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.VehicleType;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WeatherPhenomenon;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WeatherPhenomenonFeeRule;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface WeatherPhenomenonFeeRuleRepository extends CrudRepository<WeatherPhenomenonFeeRule, UUID>
{
    Optional<WeatherPhenomenonFeeRule> findByVehicleTypeAndWeatherPhenomenon(VehicleType vehicleType,
                                                                             WeatherPhenomenon weatherPhenomenon);

}
