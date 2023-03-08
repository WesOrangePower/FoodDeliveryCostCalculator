package eu.tooizi.fooddeliverycostcalculator.services;

import eu.tooizi.fooddeliverycostcalculator.domain.models.Region;
import eu.tooizi.fooddeliverycostcalculator.domain.models.WeatherConditions;
import eu.tooizi.fooddeliverycostcalculator.repositories.RegionRepository;
import eu.tooizi.fooddeliverycostcalculator.repositories.WeatherConditionsRepository;
import eu.tooizi.fooddeliverycostcalculator.repositories.WeatherPhenomenonRepository;
import eu.tooizi.fooddeliverycostcalculator.scheduling.WeatherSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class WeatherStatusUpdateService
{
    public static final String WEATHER_RESOURCE_URL = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";
    private final URI WeatherResourceUri;

    private static final Logger log = LoggerFactory.getLogger(WeatherSchedule.class);
    private final RegionRepository regionRepository;
    private final WeatherPhenomenonRepository weatherPhenomenonRepository;
    private final WeatherConditionsRepository weatherConditionsRepository;


    public WeatherStatusUpdateService(@Autowired RegionRepository regionRepository,
                                      @Autowired WeatherPhenomenonRepository weatherPhenomenonRepository,
                                      @Autowired WeatherConditionsRepository weatherConditionsRepository)
    {
        this.regionRepository = regionRepository;
        this.weatherPhenomenonRepository = weatherPhenomenonRepository;
        this.weatherConditionsRepository = weatherConditionsRepository;

        try
        {
            this.WeatherResourceUri = URI.create(WEATHER_RESOURCE_URL);
        } catch (Exception e)
        {
            throw new IllegalStateException("Invalid URL: " + WEATHER_RESOURCE_URL);
        }
    }

    public void updateWeatherStatus()
    {
        Document weatherData = fetchWeatherData();
        Map<String, Region> stationNames = getStationNames();
        List<WeatherConditions> weatherConditions = parseWeatherData(weatherData, stationNames);

        weatherConditionsRepository.saveAll(weatherConditions);
    }

    private Map<String, Region> getStationNames()
    {
        Map<String, Region> stationNames = new HashMap<>();

        for (Region region : regionRepository.findAll())
        {
            stationNames.put(region.getWeatherStationName(), region);
        }

        return stationNames;
    }

    private List<WeatherConditions> parseWeatherData(Document weatherData, Map<String, Region> stationNames)
    {
        NodeList stations = weatherData.getElementsByTagName("station");

        List<WeatherConditions> weatherConditions = new ArrayList<>();

        for (int i = 0; i < stations.getLength(); i++)
        {
            Node station = stations.item(i);

            var nameOptional = findValueByTag(station.getChildNodes(), "name");

            if (nameOptional.isEmpty() || !stationNames.containsKey(nameOptional.get()))
            {
                continue;
            }

            // parameter name -> value
            Map<String, String> nameToValue = new HashMap<>();

            for (String name : new String[]{"name", "windspeed", "wmocode", "phenomenon", "airtemperature"})
            {
                var valueOptional = findValueByTag(station.getChildNodes(), name);
                if (valueOptional.isEmpty())
                {
                    continue;
                }

                nameToValue.put(name, valueOptional.get());
            }
            WeatherConditions conditions = new WeatherConditions();

            conditions.setRegion(stationNames.get(nameToValue.get("name")));
            conditions.setWindSpeedMps(Double.parseDouble(nameToValue.get("windspeed")));
            conditions.setWeatherPhenomenon(weatherPhenomenonRepository.findByName(nameToValue.get("phenomenon")).orElse(null));
            conditions.setTemperatureCelsius(Double.parseDouble(nameToValue.get("airtemperature")));
            conditions.setTimestamp(LocalDateTime.now());
            conditions.setStationWmoCode(nameToValue.get("wmocode"));

            weatherConditions.add(conditions);
        }
        return weatherConditions;
    }

    private Document fetchWeatherData()
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response;
        try
        {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(WeatherResourceUri)
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e)
        {
            log.error("Error while fetching weather data", e);
            throw new RuntimeException(e);
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder;

        try
        {
            builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(response.body())));
        } catch (ParserConfigurationException | IOException | SAXException e)
        {
            log.error("Error while parsing weather data", e);
            throw new RuntimeException(e);
        }
    }

    private static Optional<String> findValueByTag(NodeList nodeList, String tagName)
    {
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node node = nodeList.item(i);
            if (!node.getNodeName().equals(tagName))
            {
                continue;
            }
            if (node.getNodeType() != Node.ELEMENT_NODE || node.getChildNodes().getLength() == 0)
            {
                return Optional.empty();
            }
            Node firstChild = node.getFirstChild();
            if (firstChild.getNodeType() != Node.TEXT_NODE || firstChild.getNodeValue() == null)
            {
                return Optional.empty();
            }
            return Optional.of(firstChild.getNodeValue());
        }
        return Optional.empty();
    }


}
