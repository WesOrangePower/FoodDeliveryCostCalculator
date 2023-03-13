package eu.tooizi.fooddeliverycostcalculator;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.Region;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.VehicleType;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.weather.Observations;
import eu.tooizi.fooddeliverycostcalculator.repositories.RegionRepository;
import eu.tooizi.fooddeliverycostcalculator.repositories.VehicleTypeRepository;
import eu.tooizi.fooddeliverycostcalculator.services.WeatherStatusUpdateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.StreamSupport;

@SpringBootTest
class FoodDeliveryCostCalculatorApplicationTests
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

    @Test
    void testSmokeAllRestCombinations(@Autowired RegionRepository regionRepository,
                                      @Autowired VehicleTypeRepository vehicleTypeRepository)
            throws IOException, InterruptedException
    {
        final String urlTemplate = "http://localhost:8080/delivery-fee?region=%s&vehicleType=%s";
        HttpClient client = HttpClient.newHttpClient();

        List<Region> regions = StreamSupport.stream(regionRepository.findAll().spliterator(), false).toList();
        List<VehicleType> vehicleTypes = StreamSupport.stream(vehicleTypeRepository.findAll().spliterator(), false).toList();

        for (Region region : regions)
        {
            for (VehicleType vehicleType : vehicleTypes)
            {
                String url = String.format(urlTemplate, region.getName(), vehicleType.getName());
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                assert response.statusCode() == 200;
                assert !response.body().isEmpty();
            }
        }
    }
}
