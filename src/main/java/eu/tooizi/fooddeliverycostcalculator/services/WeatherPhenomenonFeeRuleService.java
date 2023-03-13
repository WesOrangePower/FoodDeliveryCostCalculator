package eu.tooizi.fooddeliverycostcalculator.services;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WeatherPhenomenonFeeRule;
import eu.tooizi.fooddeliverycostcalculator.repositories.WeatherPhenomenonFeeRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
public class WeatherPhenomenonFeeRuleService
{
    private final WeatherPhenomenonFeeRuleRepository weatherPhenomenonFeeRuleRepository;

    public WeatherPhenomenonFeeRuleService(@Autowired WeatherPhenomenonFeeRuleRepository weatherPhenomenonFeeRuleRepository)
    {

        this.weatherPhenomenonFeeRuleRepository = weatherPhenomenonFeeRuleRepository;
    }

    public Collection<WeatherPhenomenonFeeRule> getWeatherPhenomenonFeeRules()
    {
        return StreamSupport.stream(weatherPhenomenonFeeRuleRepository.findAll().spliterator(), false)
                .toList();
    }

    public void addWeatherPhenomenonFeeRule(WeatherPhenomenonFeeRule weatherPhenomenonFeeRule)
    {
        weatherPhenomenonFeeRuleRepository.save(weatherPhenomenonFeeRule);
    }

    public void deleteWeatherPhenomenonFeeRuleById(UUID id)
    {
        weatherPhenomenonFeeRuleRepository.deleteById(id);
    }
}
