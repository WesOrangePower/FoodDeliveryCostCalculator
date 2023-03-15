package eu.tooizi.fooddeliverycostcalculator.DTOs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Entity for categorization of weather phenomena.
 */
@SuppressWarnings("MissingJavadoc")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhenomenonCategory
{
    /**
     * Names of the categories. Use the constants instead of magic strings.
     */
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

    @OneToMany(mappedBy = "phenomenonCategory", orphanRemoval = true)
    @JsonIgnore
    private Collection<WeatherPhenomenon> weatherPhenomena = new ArrayList<>();
}
