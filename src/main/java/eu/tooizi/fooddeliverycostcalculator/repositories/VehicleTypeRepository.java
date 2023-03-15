package eu.tooizi.fooddeliverycostcalculator.repositories;

import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.VehicleType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for {@link VehicleType}
 */
public interface VehicleTypeRepository extends CrudRepository<VehicleType, UUID>
{
    /**
     * Fetches the first vehicle type that matches the given name
     *
     * @param name The name
     * @return The first vehicle type that matches the given name
     */
    Optional<VehicleType> findFirstByName(String name);
}
