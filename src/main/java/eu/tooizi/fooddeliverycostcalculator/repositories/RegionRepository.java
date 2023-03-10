package eu.tooizi.fooddeliverycostcalculator.repositories;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.Region;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface RegionRepository extends CrudRepository<Region, UUID>
{
    @Query("select (count(r) > 0) from Region r where r.name = ?1")
    boolean existsName(String name);

    Optional<Region> findFirstByName(String name);
}
