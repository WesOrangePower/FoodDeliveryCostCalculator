package eu.tooizi.fooddeliverycostcalculator.domain.DTOs;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class RegionalBaseFee
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vehicle_type_id", nullable = false)
    private VehicleType vehicleType;

    @Column(name = "base_fee", nullable = false)
    private double baseFee;

    @ManyToOne( optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;


    public RegionalBaseFee(UUID id, VehicleType vehicleType, double baseFee)
    {
        this.id = id;
        this.vehicleType = vehicleType;
        this.baseFee = baseFee;
    }

    public RegionalBaseFee()
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

    public double getBaseFee()
    {
        return baseFee;
    }

    public void setBaseFee(double baseFee)
    {
        this.baseFee = baseFee;
    }

    public Region getRegion() {return region;}

    public void setRegion(Region region) {this.region = region;}

    public VehicleType getVehicleType() {return vehicleType;}

    public void setVehicleType(VehicleType vehicleType) {this.vehicleType = vehicleType;}
}
