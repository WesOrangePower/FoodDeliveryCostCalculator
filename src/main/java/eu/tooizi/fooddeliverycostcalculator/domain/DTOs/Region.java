package eu.tooizi.fooddeliverycostcalculator.domain.DTOs;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class Region
{
    @OneToMany(mappedBy = "region", orphanRemoval = true)
    private final Set<AirTemperatureFeeRule> airTemperatureFeeRules = new LinkedHashSet<>();
    @OneToMany(mappedBy = "region", orphanRemoval = true)
    private final Set<WeatherPhenomenonFeeRule> weatherPhenomenonFeeRules = new LinkedHashSet<>();
    @OneToMany(mappedBy = "region", orphanRemoval = true)
    private final Set<WindSpeedFeeRule> windSpeedFeeRules = new LinkedHashSet<>();
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "region", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Collection<RegionalBaseFee> regionalBaseFees = new ArrayList<>();
    @OneToMany(mappedBy = "region", orphanRemoval = true)
    private Collection<WeatherConditions> weatherConditions = new ArrayList<>();
    @Column(name = "weather_station_name")
    private String weatherStationName;

    public Region(UUID id, String name, Collection<RegionalBaseFee> regionalBaseFees, Collection<WeatherConditions> weatherConditions)
    {
        this.id = id;
        this.name = name;
        this.regionalBaseFees = regionalBaseFees;
        this.weatherConditions = weatherConditions;
    }

    public Region()
    {
    }

    public UUID getId()
    {
        return id;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Collection<RegionalBaseFee> getRegionalBaseFees()
    {
        return regionalBaseFees;
    }

    public void setRegionalBaseFees(Collection<RegionalBaseFee> regionalBaseFees)
    {
        this.regionalBaseFees = regionalBaseFees;
    }

    public Collection<WeatherConditions> getWeatherConditions()
    {
        return weatherConditions;
    }

    public void setWeatherConditions(Collection<WeatherConditions> weatherConditions)
    {
        this.weatherConditions = weatherConditions;
    }

    public String getWeatherStationName()
    {
        return weatherStationName;
    }

    public void setWeatherStationName(String weatherStationName)
    {
        this.weatherStationName = weatherStationName;
    }
}
