package eu.tooizi.fooddeliverycostcalculator.domain.DTOs;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleType
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "vehicleType", orphanRemoval = true)
    private Set<WindSpeedFeeRule> windSpeedFeeRules = new LinkedHashSet<>();

    @OneToMany(mappedBy = "vehicleType", orphanRemoval = true)
    private Set<AirTemperatureFeeRule> airTemperatureFeeRules = new LinkedHashSet<>();

    @OneToMany(mappedBy = "vehicleType", orphanRemoval = true)
    private Set<WeatherPhenomenonFeeRule> weatherPhenomenonFeeRules = new LinkedHashSet<>();
}
