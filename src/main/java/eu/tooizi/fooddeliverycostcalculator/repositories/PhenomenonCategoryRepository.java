package eu.tooizi.fooddeliverycostcalculator.repositories;

import eu.tooizi.fooddeliverycostcalculator.DTOs.domain.PhenomenonCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for {@link PhenomenonCategory}
 */
public interface PhenomenonCategoryRepository extends CrudRepository<PhenomenonCategory, UUID>
{
    /**
     * Fetches the default phenomenon category
     *
     * @return The default phenomenon category
     */
    @Query("select p from PhenomenonCategory p where p.name = \"" + PhenomenonCategory.DEFAULT_NAME + "\"")
    PhenomenonCategory findDefault();

    Optional<PhenomenonCategory> findFirstByName(String category);
}
