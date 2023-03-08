package eu.tooizi.fooddeliverycostcalculator.repositories;

import eu.tooizi.fooddeliverycostcalculator.domain.models.WeatherConditions;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface WeatherConditionsRepository extends CrudRepository<WeatherConditions, UUID>
{
}
