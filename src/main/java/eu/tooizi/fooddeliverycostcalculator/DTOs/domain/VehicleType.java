package eu.tooizi.fooddeliverycostcalculator.DTOs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Vehicle type entity.
 */
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
    @JsonIgnore
    private Set<WindSpeedFeeRule> windSpeedFeeRules = new LinkedHashSet<>();

    @OneToMany(mappedBy = "vehicleType", orphanRemoval = true)
    @JsonIgnore
    private Set<AirTemperatureFeeRule> airTemperatureFeeRules = new LinkedHashSet<>();

    @OneToMany(mappedBy = "vehicleType", orphanRemoval = true)
    @JsonIgnore
    private Set<WeatherPhenomenonFeeRule> weatherPhenomenonFeeRules = new LinkedHashSet<>();
}
