package eu.tooizi.fooddeliverycostcalculator;

import eu.tooizi.fooddeliverycostcalculator.services.DatabaseSeeder;
import eu.tooizi.fooddeliverycostcalculator.services.WeatherStatusUpdateService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FoodDeliveryCostCalculatorApplication implements CommandLineRunner
{


    private final DatabaseSeeder databaseSeeder;
    private final WeatherStatusUpdateService weatherStatusUpdateService;
    public FoodDeliveryCostCalculatorApplication(DatabaseSeeder databaseSeeder,
                                                 WeatherStatusUpdateService weatherStatusUpdateService)
    {
        this.databaseSeeder = databaseSeeder;
        this.weatherStatusUpdateService = weatherStatusUpdateService;
    }

    public static void main(String[] args)
    {
        SpringApplication.run(FoodDeliveryCostCalculatorApplication.class, args);
    }

    @Override
    public void run(String... args)
    {
        databaseSeeder.seed();
        weatherStatusUpdateService.updateWeatherStatus();
    }
}
