package eu.tooizi.fooddeliverycostcalculator.services;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.Region;
import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WeatherConditions;
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

            conditions.setRegion(stationNames.get(station.getName()));
            conditions.setWindSpeedMps(station.getWindspeed());
            conditions.setWeatherPhenomenon(weatherPhenomenonRepository.findByName(station.getPhenomenon()).orElse(null));
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
