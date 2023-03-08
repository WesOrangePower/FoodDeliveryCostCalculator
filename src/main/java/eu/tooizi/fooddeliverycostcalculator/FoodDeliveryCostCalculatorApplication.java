package eu.tooizi.fooddeliverycostcalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FoodDeliveryCostCalculatorApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(FoodDeliveryCostCalculatorApplication.class, args);
    }

}
