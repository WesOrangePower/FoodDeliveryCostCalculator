package eu.tooizi.fooddeliverycostcalculator.repositories;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.Region;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.RegionalBaseFee;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.VehicleType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface RegionalBaseFeeRepository extends CrudRepository<RegionalBaseFee, UUID>
{
    Optional<RegionalBaseFee> findFirstByRegionAndVehicleType(Region region, VehicleType vehicleType);
}
