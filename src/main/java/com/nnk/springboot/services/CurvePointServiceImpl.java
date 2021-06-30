package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurvePointServiceImpl implements CurvePointService{

    private final CurvePointRepository curvePointRepository;
    
    @Autowired
    public CurvePointServiceImpl(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    @Override
    public List<CurvePoint> getAll() {
        return curvePointRepository.findAll();
    }

    @Override
    public CurvePoint createCurvePoint(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }

    @Override
    public CurvePoint updateCurvePoint(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }

    @Override
    public void deleteCurvePoint(CurvePoint curvePoint) {
        curvePointRepository.delete(curvePoint);
    }

    @Override
    public Optional<CurvePoint> findById(Integer id) {
        return curvePointRepository.findById(id);
    }
}
