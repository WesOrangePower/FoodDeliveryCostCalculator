package eu.tooizi.fooddeliverycostcalculator.repositories;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.Region;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WeatherConditions;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for {@link WeatherConditions}
 */
public interface WeatherConditionsRepository extends CrudRepository<WeatherConditions, UUID>
{
    /**
     * Fetches the latest weather conditions for the given region
     *
     * @param region The region
     * @return The latest weather conditions for the given region
     */
    Optional<WeatherConditions> findFirstByRegionOrderByTimestampDesc(Region region);
}
