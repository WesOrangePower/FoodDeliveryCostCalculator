package eu.tooizi.fooddeliverycostcalculator.repositories;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.VehicleType;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface VehicleTypeRepository extends CrudRepository<VehicleType, UUID>
{
}
