package eu.tooizi.fooddeliverycostcalculator.repositories;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WeatherConditions;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface WeatherConditionsRepository extends CrudRepository<WeatherConditions, UUID>
{
    @Query("select w from WeatherConditions w where w.region.id = ?1 order by w.timestamp DESC")
    Optional<WeatherConditions> findNewestByRegionId(@NonNull UUID id);

}
