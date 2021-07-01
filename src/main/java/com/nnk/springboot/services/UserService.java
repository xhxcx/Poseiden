package com.nnk.springboot.services;


import com.nnk.springboot.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service interface to manage User
 */
@Service
public interface UserService {

    /**
     * Retrieve all existing User in database
     * @return a List of User
     */
    List<User> getAll();

    /**
     * Add a User in database
     * @param user to add
     * @return saved user
     */
    User createUser(User user);

    /**
     * Update an existing User in database
     * @param user with updated values to save
     * @return saved user
     */
    User updateUser(User user);

    /**
     * Delete the given user from database
     * @param user to delete
     */
    void deleteUser(User user);

    /**
     * Get a specific User based on its id in database
     * @param id Integer id to search for
     * @return an Optional User
     */
    Optional<User> findById(Integer id);
}
