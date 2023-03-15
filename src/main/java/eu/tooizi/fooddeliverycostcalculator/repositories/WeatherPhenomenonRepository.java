package eu.tooizi.fooddeliverycostcalculator.repositories;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WeatherPhenomenon;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for {@link WeatherPhenomenon}
 */
public interface WeatherPhenomenonRepository extends CrudRepository<WeatherPhenomenon, UUID>
{
    /**
     * Finds a weather phenomenon by its name
     *
     * @param name The name of the weather phenomenon
     * @return The weather phenomenon
     */
    Optional<WeatherPhenomenon> findByName(String name);
}
