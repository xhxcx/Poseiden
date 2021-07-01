package com.nnk.springboot.services;


import com.nnk.springboot.domain.RuleName;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service interface to manage RuleName
 */
@Service
public interface RuleNameService {

    /**
     * Retrieve all existing RuleName in database
     * @return a List of RuleName
     */
    List<RuleName> getAll();

    /**
     * Add a RuleName in database
     * @param ruleName to add
     * @return saved ruleName
     */
    RuleName createRuleName(RuleName ruleName);

    /**
     * Update an existing RuleName in database
     * @param ruleName with updated values to save
     * @return saved ruleName
     */
    RuleName updateRuleName(RuleName ruleName);

    /**
     * Delete the given ruleName from database
     * @param ruleName to delete
     */
    void deleteRuleName(RuleName ruleName);

    /**
     * Get a specific RuleName based on its id in database
     * @param id Integer id to search for
     * @return an Optional RuleName
     */
    Optional<RuleName> findById(Integer id);
}
