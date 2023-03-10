package eu.tooizi.fooddeliverycostcalculator.repositories;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WeatherPhenomenon;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface WeatherPhenomenonRepository extends CrudRepository<WeatherPhenomenon, UUID>
{
    Optional<WeatherPhenomenon> findByName(String name);
}
