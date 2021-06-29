package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service interface to manage BidLists
 */
@Service
public interface BidListService {

    /**
     * Retrieve all existing BidList in database
     * @return a List of BidList
     */
    List<BidList> getAll();

    /**
     * Add a BidList in database
     * @param bidList to add
     * @return saved bidList
     */
    BidList createBidList(BidList bidList);

    /**
     * Update an existing BidList in database
     * @param bidList with updated values to save
     * @return saved bidList
     */
    BidList updateBidList(BidList bidList);

    /**
     * Delete the given bidList from database
     * @param bidList to delete
     */
    void deleteBidList(BidList bidList);

    /**
     * Get a specific BidList based on its id in database
     * @param id Integer id to search for
     * @return an Optional BidList
     */
    Optional<BidList> findById(Integer id);
}
