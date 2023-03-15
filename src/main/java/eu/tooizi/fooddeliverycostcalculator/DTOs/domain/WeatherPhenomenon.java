package eu.tooizi.fooddeliverycostcalculator.DTOs.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Weather phenomenon entity.
 */
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

    @ManyToOne()
    @JoinColumn(name = "phenomenon_category_id")
    private PhenomenonCategory phenomenonCategory;

    @OneToMany(mappedBy = "weatherPhenomenon")
    private Collection<WeatherConditions> weatherConditions = new ArrayList<>();
}
