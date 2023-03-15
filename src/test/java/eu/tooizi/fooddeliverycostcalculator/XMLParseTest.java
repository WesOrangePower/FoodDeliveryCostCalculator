package eu.tooizi.fooddeliverycostcalculator;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import eu.tooizi.fooddeliverycostcalculator.DTOs.weather.Observations;
import eu.tooizi.fooddeliverycostcalculator.services.WeatherStatusUpdateService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootTest
class XMLParseTest
{

    @Test
    void testXmlMap()
    {
        URI uri;
        try
        {
            uri = URI.create(WeatherStatusUpdateService.WEATHER_RESOURCE_URL);
        } catch (Exception e)
        {
            throw new IllegalStateException("Invalid URL: " + WeatherStatusUpdateService.WEATHER_RESOURCE_URL);
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response;
        try
        {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String xml = response.body();
            XmlMapper mapper = new XmlMapper();
            Observations observations = mapper.readValue(xml, Observations.class);
            assert observations != null;
        } catch (IOException | InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

}
