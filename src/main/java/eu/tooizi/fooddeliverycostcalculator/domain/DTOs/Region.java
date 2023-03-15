package eu.tooizi.fooddeliverycostcalculator.domain.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Region
{
    @OneToMany(mappedBy = "region", orphanRemoval = true)
    @JsonIgnore
    private final Set<AirTemperatureFeeRule> airTemperatureFeeRules = new LinkedHashSet<>();
    @OneToMany(mappedBy = "region", orphanRemoval = true)
    @JsonIgnore
    private final Set<WeatherPhenomenonFeeRule> weatherPhenomenonFeeRules = new LinkedHashSet<>();
    @OneToMany(mappedBy = "region", orphanRemoval = true)
    @JsonIgnore
    private final Set<WindSpeedFeeRule> windSpeedFeeRules = new LinkedHashSet<>();
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "region", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private Collection<RegionalBaseFee> regionalBaseFees = new ArrayList<>();
    @OneToMany(mappedBy = "region", orphanRemoval = true)
    @JsonIgnore
    private Collection<WeatherConditions> weatherConditions = new ArrayList<>();
    @Column(name = "weather_station_name")
    private String weatherStationName;
}
