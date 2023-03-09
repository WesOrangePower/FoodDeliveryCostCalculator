package eu.tooizi.fooddeliverycostcalculator.domain.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
public class WeatherConditions
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "station_wmo_code", nullable = false)
    private String stationWmoCode;

    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "weather_phenomenon_id", nullable = false)
    private WeatherPhenomenon weatherPhenomenon;

    @Column(name = "temperature_celsius")
    @Nullable
    private double temperatureCelsius;

    @Column(name = "wind_speed_mps")
    @Nullable
    private double windSpeedMps;

    /*
     * All args and no args constructors
     */

    public WeatherConditions(UUID id, LocalDateTime timestamp, Region region, WeatherPhenomenon weatherPhenomenon, double temperatureCelsius, double windSpeedMps)
    {
        this.id = id;
        this.timestamp = timestamp;
        this.region = region;
        this.weatherPhenomenon = weatherPhenomenon;
        this.temperatureCelsius = temperatureCelsius;
        this.windSpeedMps = windSpeedMps;
    }

    public WeatherConditions()
    {
    }

    /*
     * Getters and setters
     */

    public UUID getId()
    {
        return id;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }

    public String getStationWmoCode()
    {
        return stationWmoCode;
    }

    public void setStationWmoCode(String stationWmoCode)
    {
        this.stationWmoCode = stationWmoCode;
    }

    public LocalDateTime getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp)
    {
        this.timestamp = timestamp;
    }

    public Region getRegion()
    {
        return region;
    }

    public void setRegion(Region region)
    {
        this.region = region;
    }

    public WeatherPhenomenon getWeatherPhenomenon()
    {
        return weatherPhenomenon;
    }

    public void setWeatherPhenomenon(WeatherPhenomenon weatherPhenomenon)
    {
        this.weatherPhenomenon = weatherPhenomenon;
    }

    public double getTemperatureCelsius()
    {
        return temperatureCelsius;
    }

    public void setTemperatureCelsius(double temperatureCelsius)
    {
        this.temperatureCelsius = temperatureCelsius;
    }

    public double getWindSpeedMps()
    {
        return windSpeedMps;
    }

    public void setWindSpeedMps(double windSpeedMps)
    {
        this.windSpeedMps = windSpeedMps;
    }
}
