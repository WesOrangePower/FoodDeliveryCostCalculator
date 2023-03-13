package eu.tooizi.fooddeliverycostcalculator.services;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.AirTemperatureFeeRule;
import eu.tooizi.fooddeliverycostcalculator.repositories.AirTemperatureFeeRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
public class AirTemperatureFeeRuleService
{
    private final AirTemperatureFeeRuleRepository airTemperatureFeeRuleRepository;

    public AirTemperatureFeeRuleService(
            @Autowired AirTemperatureFeeRuleRepository airTemperatureFeeRuleRepository)
    {
        this.airTemperatureFeeRuleRepository = airTemperatureFeeRuleRepository;
    }

    public Collection<AirTemperatureFeeRule> getAirTemperatureFeeRules()
    {
        return StreamSupport.stream(airTemperatureFeeRuleRepository.findAll().spliterator(), false)
                .toList();
    }

    public void addAirTemperatureFeeRule(AirTemperatureFeeRule airTemperatureFeeRule)
    {
        airTemperatureFeeRuleRepository.save(airTemperatureFeeRule);
    }

    public void deleteAirTemperatureFeeRuleById(UUID id)
    {
        airTemperatureFeeRuleRepository.deleteById(id);
    }
}
