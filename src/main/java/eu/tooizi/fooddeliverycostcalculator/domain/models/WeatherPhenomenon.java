package eu.tooizi.fooddeliverycostcalculator.domain.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
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

    @OneToMany(mappedBy = "weatherPhenomenon", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Collection<WeatherConditions> weatherConditions = new ArrayList<>();

    public PhenomenonCategory getPhenomenonCategory() {return phenomenonCategory;}

    public void setPhenomenonCategory(PhenomenonCategory phenomenonCategory) {this.phenomenonCategory = phenomenonCategory;}

    public Collection<WeatherConditions> getWeatherConditions() {return weatherConditions;}

    public void setWeatherConditions(Collection<WeatherConditions> weatherConditions) {this.weatherConditions = weatherConditions;}
}
