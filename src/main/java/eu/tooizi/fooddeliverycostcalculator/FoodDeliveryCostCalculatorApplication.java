package eu.tooizi.fooddeliverycostcalculator;

import eu.tooizi.fooddeliverycostcalculator.services.DatabaseSeeder;
import eu.tooizi.fooddeliverycostcalculator.services.WeatherStatusUpdateService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main application class.
 */
@SpringBootApplication
@EnableScheduling
public class FoodDeliveryCostCalculatorApplication implements CommandLineRunner
{


    private final DatabaseSeeder databaseSeeder;
    private final WeatherStatusUpdateService weatherStatusUpdateService;

    /**
     * Constructor.
     * Fields are injected.
     *
     * @param databaseSeeder             Database seeder service
     * @param weatherStatusUpdateService Weather status update service
     */
    public FoodDeliveryCostCalculatorApplication(DatabaseSeeder databaseSeeder,
                                                 WeatherStatusUpdateService weatherStatusUpdateService)
    {
        this.databaseSeeder = databaseSeeder;
        this.weatherStatusUpdateService = weatherStatusUpdateService;
    }

    /**
     * Entry point of the application.
     *
     * @param args Command line arguments
     */
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
