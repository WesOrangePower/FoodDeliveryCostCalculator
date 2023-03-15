package eu.tooizi.fooddeliverycostcalculator.domain.DTOs;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherConditions
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    @Column(name = "station_wmo_code", nullable = false)
    private String stationWmoCode;

    @ManyToOne(optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @ManyToOne()
    @JoinColumn(name = "weather_phenomenon_id")
    private WeatherPhenomenon weatherPhenomenon;

    @Column(name = "temperature_celsius")
    @Nullable
    private double temperatureCelsius;

    @Column(name = "wind_speed_mps")
    @Nullable
    private double windSpeedMps;
}
