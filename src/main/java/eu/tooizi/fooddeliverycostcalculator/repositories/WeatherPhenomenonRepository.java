package eu.tooizi.fooddeliverycostcalculator.repositories;

import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.PhenomenonCategory;
import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.WeatherPhenomenon;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
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

    /**
     * Finds all weather phenomena by their category
     *
     * @param category The category of the weather phenomena
     * @return The weather phenomena
     */
    Collection<WeatherPhenomenon> findAllByPhenomenonCategoryIs(PhenomenonCategory category);
}
