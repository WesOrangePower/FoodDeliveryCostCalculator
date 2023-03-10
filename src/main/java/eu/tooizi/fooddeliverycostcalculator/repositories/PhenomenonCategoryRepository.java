package eu.tooizi.fooddeliverycostcalculator.repositories;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.PhenomenonCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PhenomenonCategoryRepository extends CrudRepository<PhenomenonCategory, UUID>
{
    @Query("select p from PhenomenonCategory p where p.name = \"" + PhenomenonCategory.DEFAULT_NAME + "\"")
    PhenomenonCategory findDefault();
}
