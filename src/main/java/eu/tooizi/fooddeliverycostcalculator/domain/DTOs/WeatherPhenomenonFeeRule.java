package eu.tooizi.fooddeliverycostcalculator.domain.DTOs;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherPhenomenonFeeRule implements FeeRule
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vehicle_type_id", nullable = false)
    private VehicleType vehicleType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @ManyToOne(optional = false)
    @JoinColumn(name = "phenomenon_category_id", nullable = false)
    private PhenomenonCategory phenomenonCategory;

    @Column(name = "deliverable", nullable = false)
    private Boolean deliverable = true;

    @Column(name = "price_difference")
    @Nullable
    private Double priceDifference;
}