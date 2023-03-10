package eu.tooizi.fooddeliverycostcalculator.repositories;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.RegionalBaseFee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface RegionalBaseFeeRepository extends CrudRepository<RegionalBaseFee, UUID>
{
    @Query("select r from RegionalBaseFee r where r.region.id = ?1 and r.vehicleType.id = ?2")
    Optional<RegionalBaseFee> FindFirstByRegionIdAndVehicleTypeId(UUID regionId, UUID vehicleTypeId);

}
