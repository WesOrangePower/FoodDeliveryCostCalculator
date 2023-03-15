package eu.tooizi.fooddeliverycostcalculator.repositories;

import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.Region;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for {@link Region}
 */
public interface RegionRepository extends CrudRepository<Region, UUID>
{
    /**
     * Fetches the first region that matches the given name
     *
     * @param name The name
     * @return The first region that matches the given name
     */
    Optional<Region> findFirstByName(String name);
}
