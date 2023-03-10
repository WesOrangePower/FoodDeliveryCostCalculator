package eu.tooizi.fooddeliverycostcalculator.repositories;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.VehicleType;

import org.springframework.data.repository.CrudRepository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public interface VehicleTypeRepository extends CrudRepository<VehicleType, UUID>
{
    Optional<VehicleType> findFirstByName(String name);
}
