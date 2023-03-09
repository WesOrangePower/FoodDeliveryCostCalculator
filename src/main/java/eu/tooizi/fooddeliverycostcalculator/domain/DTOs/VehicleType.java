package eu.tooizi.fooddeliverycostcalculator.domain.models;

import jakarta.persistence.*;

@Entity
public class VehicleType
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    public VehicleType(Integer id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public VehicleType()
    {
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
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
