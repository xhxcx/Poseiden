package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameServiceImpl implements RuleNameService{

    private final RuleNameRepository ruleNameRepository;
    
    @Autowired
    public RuleNameServiceImpl(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    @Override
    public List<RuleName> getAll() {
        return ruleNameRepository.findAll();
    }

    @Override
    public RuleName createRuleName(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    @Override
    public RuleName updateRuleName(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    @Override
    public void deleteRuleName(RuleName ruleName) {
        ruleNameRepository.delete(ruleName);
    }

    @Override
    public Optional<RuleName> findById(Integer id) {
        return ruleNameRepository.findById(id);
    }
}
