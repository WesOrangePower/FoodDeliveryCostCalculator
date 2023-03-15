package eu.tooizi.fooddeliverycostcalculator.domain.DTOs;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Wind speed fee business rule.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WindSpeedFeeRule implements RangeFeeRule
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne( optional = false)
    @JoinColumn(name = "vehicle_type_id", nullable = false)
    private VehicleType vehicleType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @Column(name = "lower_bound")
    @Nullable
    private Double lowerBound;

    @Column(name = "upper_bound")
    @Nullable
    private Double upperBound;

    @Column(name = "deliverable", nullable = false)
    private Boolean deliverable = true;

    @Column(name = "price_difference")
    @Nullable
    private Double priceDifference;
}
