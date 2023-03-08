package eu.tooizi.fooddeliverycostcalculator.scheduling;

import eu.tooizi.fooddeliverycostcalculator.services.WeatherStatusUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component // <- Component
public class WeatherSchedule
{

    private WeatherStatusUpdateService weatherStatusUpdateService;

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
