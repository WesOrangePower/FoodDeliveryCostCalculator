package eu.tooizi.fooddeliverycostcalculator.services.scheduling;

import eu.tooizi.fooddeliverycostcalculator.services.WeatherStatusUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * A scheduler component that fetches weather data from the National Weather Service API
 */
@Component
public class WeatherSchedule
{

    private final WeatherStatusUpdateService weatherStatusUpdateService;

    /**
     * Constructor.
     * Field is autowired.
     *
     * @param weatherStatusUpdateService The service that fetches weather data from the API
     */
    public WeatherSchedule(@Autowired WeatherStatusUpdateService weatherStatusUpdateService)
    {
        this.weatherStatusUpdateService = weatherStatusUpdateService;
    }


    /**
     * Scheduled task to fetch weather data from the API
     * Runs every hour at 15th minute
     */
    @Scheduled(cron = "0 15 * * * *")
    void fetchWeatherData()
    {
        weatherStatusUpdateService.updateWeatherStatus();
    }
}
