package eu.tooizi.fooddeliverycostcalculator.domain.DTOs;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherPhenomenon
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "phenomenon_category_id")
    private PhenomenonCategory phenomenonCategory;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "weatherPhenomenon")
    private Collection<WeatherConditions> weatherConditions = new ArrayList<>();

    public Collection<WeatherConditions> getWeatherConditions()
    {
        return weatherConditions;
    }

    public void setWeatherConditions(Collection<WeatherConditions> weatherConditions)
    {
        this.weatherConditions = weatherConditions;
    }
}
