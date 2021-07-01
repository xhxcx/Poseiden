package com.nnk.springboot.services;


import com.nnk.springboot.domain.Rating;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service interface to manage Rating
 */
@Service
public interface RatingService {

    /**
     * Retrieve all existing Rating in database
     * @return a List of Rating
     */
    List<Rating> getAll();

    /**
     * Add a Rating in database
     * @param rating to add
     * @return saved rating
     */
    Rating createRating(Rating rating);

    /**
     * Update an existing Rating in database
     * @param rating with updated values to save
     * @return saved rating
     */
    Rating updateRating(Rating rating);

    /**
     * Delete the given rating from database
     * @param rating to delete
     */
    void deleteRating(Rating rating);

    /**
     * Get a specific Rating based on its id in database
     * @param id Integer id to search for
     * @return an Optional Rating
     */
    Optional<Rating> findById(Integer id);
}
