package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service interface to manage Trade
 */
@Service
public interface TradeService {

    /**
     * Retrieve all existing Trade in database
     * @return a List of Trade
     */
    List<Trade> getAll();

    /**
     * Add a Trade in database
     * @param trade to add
     * @return saved trade
     */
    Trade createTrade(Trade trade);

    /**
     * Update an existing Trade in database
     * @param trade with updated values to save
     * @return saved trade
     */
    Trade updateTrade(Trade trade);

    /**
     * Delete the given trade from database
     * @param trade to delete
     */
    void deleteTrade(Trade trade);

    /**
     * Get a specific Trade based on its id in database
     * @param id Integer id to search for
     * @return an Optional Trade
     */
    Optional<Trade> findById(Integer id);
}
