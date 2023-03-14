package eu.tooizi.fooddeliverycostcalculator.domain.DTOs;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
public class PhenomenonCategory
{
    public static final String DEFAULT_NAME = "Clear or unknown";
    public static final String SLEET = "sleet";
    public static final String GLAZE = "glaze";
    public static final String THUNDER = "thunder";
    public static final String HAIL = "hail";
    public static final String RAIN = "rain";
    public static final String SNOW = "snow";
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(mappedBy = "phenomenonCategory", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Collection<WeatherPhenomenon> weatherPhenomena = new ArrayList<>();

    public PhenomenonCategory(UUID id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public PhenomenonCategory()
    {
    }

    public Collection<WeatherPhenomenon> getWeatherPhenomena() {return weatherPhenomena;}

    public void setWeatherPhenomena(Collection<WeatherPhenomenon> weatherPhenomena) {this.weatherPhenomena = weatherPhenomena;}

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
