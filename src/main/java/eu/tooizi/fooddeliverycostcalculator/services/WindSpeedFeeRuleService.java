package eu.tooizi.fooddeliverycostcalculator.services;

import eu.tooizi.fooddeliverycostcalculator.domain.DTOs.WindSpeedFeeRule;
import eu.tooizi.fooddeliverycostcalculator.repositories.WindSpeedFeeRuleRepository;
import eu.tooizi.fooddeliverycostcalculator.services.exceptions.WindSpeedFeeRuleOverlapsException;
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
            throws WindSpeedFeeRuleOverlapsException
    {

        RangeFeeRuleOverlapChecker<WindSpeedFeeRule> overlapChecker = new RangeFeeRuleOverlapChecker<>();

        overlapChecker.findOverlap(windSpeedFeeRule, windSpeedFeeRuleRepository.findAll())
                .ifPresent(overlappingFeeRule -> {
                    throw new WindSpeedFeeRuleOverlapsException("Added rule overlaps with and existing one.",
                            overlappingFeeRule);
                });

        windSpeedFeeRuleRepository.save(windSpeedFeeRule);
    }

    public void deleteWindSpeedFeeRuleById(UUID id)
    {
        windSpeedFeeRuleRepository.deleteById(id);
    }
}
