package eu.tooizi.fooddeliverycostcalculator.DTOs.weather;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "station")
public class Station
{
    private String airpressure;
    private String windspeedmax;
    private String uvindex;
    private String visibility;
    private Double airtemperature;
    private Double latitude;
    private String phenomenon;
    private String winddirection;
    private Double precipitations;
    private String relativehumidity;
    @JacksonXmlProperty(localName = "waterlevel_eh2000")
    private String eh2000;
    private String waterlevel;
    private String wmocode;
    private String watertemperature;
    private String name;
    private Double windspeed;
    private Double longitude;
}
