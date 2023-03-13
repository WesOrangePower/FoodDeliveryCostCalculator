package eu.tooizi.fooddeliverycostcalculator.services;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WindSpeedFeeRule;
import eu.tooizi.fooddeliverycostcalculator.repositories.WindSpeedFeeRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
public class WindSpeedFeeRuleService
{
    private final WindSpeedFeeRuleRepository windSpeedFeeRuleRepository;

    public WindSpeedFeeRuleService(@Autowired WindSpeedFeeRuleRepository windSpeedFeeRuleRepository)
    {
        this.windSpeedFeeRuleRepository = windSpeedFeeRuleRepository;
    }

    public Collection<WindSpeedFeeRule> getWindSpeedFeeRules()
    {
        return StreamSupport.stream(windSpeedFeeRuleRepository.findAll().spliterator(), false)
                .toList();
    }

    public void addWindSpeedFeeRule(WindSpeedFeeRule windSpeedFeeRule)
    {
        windSpeedFeeRuleRepository.save(windSpeedFeeRule);
    }

    public void deleteWindSpeedFeeRuleById(UUID id)
    {
        windSpeedFeeRuleRepository.deleteById(id);
    }
}
