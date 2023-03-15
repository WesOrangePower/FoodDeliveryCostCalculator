package eu.tooizi.fooddeliverycostcalculator.domain.DTOs;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Regional base fee for a vehicle type
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegionalBaseFee
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vehicle_type_id", nullable = false)
    private VehicleType vehicleType;

    @Column(name = "base_fee", nullable = false)
    private double baseFee;

    @ManyToOne( optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;
}
