package eu.tooizi.fooddeliverycostcalculator.services;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.Region;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WeatherConditions;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WeatherPhenomenon;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.weather.Observations;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.weather.Station;
import eu.tooizi.fooddeliverycostcalculator.repositories.RegionRepository;
import eu.tooizi.fooddeliverycostcalculator.repositories.WeatherConditionsRepository;
import eu.tooizi.fooddeliverycostcalculator.repositories.WeatherPhenomenonRepository;
import eu.tooizi.fooddeliverycostcalculator.scheduling.WeatherSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service that fetches the weather data and saves it to the database.
 */
@Service
public class WeatherStatusUpdateService
{
    /**
     * URI for the weather data resource.
     */
    public static final String WEATHER_RESOURCE_URL = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";
    private static final Logger log = LoggerFactory.getLogger(WeatherSchedule.class);
    private final URI WeatherResourceUri;
    private final RegionRepository regionRepository;
    private final WeatherPhenomenonRepository weatherPhenomenonRepository;
    private final WeatherConditionsRepository weatherConditionsRepository;


    /**
     * Constructor.
     * All fields are autowired.
     *
     * @param regionRepository            Repository for regions.
     * @param weatherPhenomenonRepository Repository for weather phenomena.
     * @param weatherConditionsRepository Repository for weather conditions.
     */
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

    /**
     * Fetches the weather data from the Estonian National Weather Service and saves it to the database.
     */
    public void updateWeatherStatus()
    {
        Observations weatherData = fetchWeatherData();
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

    private List<WeatherConditions> parseWeatherData(Observations observations, Map<String, Region> stationNames)
    {
        int integerTimestamp = observations.getTimestamp();
        Instant timestamp = Instant.ofEpochSecond(integerTimestamp);

        List<WeatherConditions> weatherConditions = new ArrayList<>();

        List<Station> stationsOfInterest = observations.stations.stream()
                .filter(station -> stationNames.containsKey(station.getName()))
                .toList();

        for (Station station : stationsOfInterest)
        {
            WeatherConditions conditions = new WeatherConditions();
            WeatherPhenomenon phenomenon = weatherPhenomenonRepository.findByName(station.getPhenomenon())
                    .orElse(weatherPhenomenonRepository.findByName("Clear")
                            .orElseThrow(() -> new IllegalStateException("Could not find clear weather phenomenon. Is the database corrupted?"))
                    );

            conditions.setRegion(stationNames.get(station.getName()));
            conditions.setWindSpeedMps(station.getWindspeed());
            conditions.setWeatherPhenomenon(phenomenon);
            conditions.setTemperatureCelsius(station.getAirtemperature());
            conditions.setTimestamp(timestamp);
            conditions.setStationWmoCode(station.getWmocode());

            weatherConditions.add(conditions);
        }

        return weatherConditions;
    }

    private Observations fetchWeatherData()
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

        try
        {
            XmlMapper xmlMapper = new XmlMapper();
            return xmlMapper.readValue(response.body(), Observations.class);
        } catch (IOException e)
        {
            log.error("Error while parsing weather data", e);
            throw new RuntimeException(e);
        }
    }


}
