package eu.tooizi.fooddeliverycostcalculator;

import eu.tooizi.fooddeliverycostcalculator.services.DatabaseSeeder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FoodDeliveryCostCalculatorApplication implements CommandLineRunner
{


    public static void main(String[] args)
    {
        SpringApplication.run(FoodDeliveryCostCalculatorApplication.class, args);
    }

    private final DatabaseSeeder databaseSeeder;
    public FoodDeliveryCostCalculatorApplication(DatabaseSeeder databaseSeeder)
    {
        this.databaseSeeder = databaseSeeder;
    }

    @Override
    public void run(String... args) throws Exception
    {
        databaseSeeder.seed();
    }
}
