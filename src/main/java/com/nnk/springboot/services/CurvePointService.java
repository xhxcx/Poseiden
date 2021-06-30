package com.nnk.springboot.services;


import com.nnk.springboot.domain.CurvePoint;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service interface to manage CurvePoint
 */
@Service
public interface CurvePointService {

    /**
     * Retrieve all existing CurvePoint in database
     * @return a List of CurvePoint
     */
    List<CurvePoint> getAll();

    /**
     * Add a CurvePoint in database
     * @param curvePoint to add
     * @return saved curvePoint
     */
    CurvePoint createCurvePoint(CurvePoint curvePoint);

    /**
     * Update an existing CurvePoint in database
     * @param curvePoint with updated values to save
     * @return saved curvePoint
     */
    CurvePoint updateCurvePoint(CurvePoint curvePoint);

    /**
     * Delete the given curvePoint from database
     * @param curvePoint to delete
     */
    void deleteCurvePoint(CurvePoint curvePoint);

    /**
     * Get a specific CurvePoint based on its id in database
     * @param id Integer id to search for
     * @return an Optional CurvePoint
     */
    Optional<CurvePoint> findById(Integer id);
}
