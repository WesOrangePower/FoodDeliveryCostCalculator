package eu.tooizi.fooddeliverycostcalculator.DTOs.weather;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "observations")
public class Observations
{
    @JacksonXmlProperty(localName = "station")
    @JacksonXmlElementWrapper(useWrapping = false)
    public List<Station> stations;
    @JacksonXmlProperty(isAttribute = true)
    public int timestamp;
    public String text;

}
