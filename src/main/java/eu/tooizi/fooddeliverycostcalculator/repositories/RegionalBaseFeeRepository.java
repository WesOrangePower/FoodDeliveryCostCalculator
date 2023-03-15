package eu.tooizi.fooddeliverycostcalculator.repositories;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.Region;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.RegionalBaseFee;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.VehicleType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for {@link RegionalBaseFee}
 */
public interface RegionalBaseFeeRepository extends CrudRepository<RegionalBaseFee, UUID>
{
    /**
     * Fetches the first regional base fee that matches the given region and vehicle type.
     *
     * @param region      The region
     * @param vehicleType The vehicle type
     * @return The first regional base fee that matches the given region and vehicle type
     */
    Optional<RegionalBaseFee> findFirstByRegionAndVehicleType(Region region, VehicleType vehicleType);
}
