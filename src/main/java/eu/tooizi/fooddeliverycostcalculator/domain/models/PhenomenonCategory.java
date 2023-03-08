package eu.tooizi.fooddeliverycostcalculator.domain.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
public class PhenomenonCategory
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(mappedBy = "phenomenonCategory", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Collection<WeatherPhenomenon> weatherPhenomena = new ArrayList<>();

    public Collection<WeatherPhenomenon> getWeatherPhenomena() {return weatherPhenomena;}

    public void setWeatherPhenomena(Collection<WeatherPhenomenon> weatherPhenomena) {this.weatherPhenomena = weatherPhenomena;}


    public PhenomenonCategory(UUID id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public PhenomenonCategory()
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
}
