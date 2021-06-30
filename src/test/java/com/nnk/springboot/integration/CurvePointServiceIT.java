package com.nnk.springboot.integration;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "/application-test.properties")
@Sql({"/sql/IT_data.sql"})
public class CurvePointServiceIT {

    @Autowired
    private CurvePointServiceImpl curvePointService;

    @Autowired
    private CurvePointRepository curvePointRepository;

    @Test
    public void getAllIT(){
        List<CurvePoint> result = curvePointService.getAll();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getCurveId()).isEqualTo(1);
    }

    @Test
    public void createCurvePointIT(){
        CurvePoint newCurvePoint = new CurvePoint();
        newCurvePoint.setCurveId(99);
        newCurvePoint.setTerm(555d);
        newCurvePoint.setValue(999d);

        CurvePoint result = curvePointService.createCurvePoint(newCurvePoint);

        assertThat(result.getId()).isEqualTo(3);
        assertThat(result.getCurveId()).isEqualTo(newCurvePoint.getCurveId());
        assertThat(curvePointRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    public void updateCurvePointIT(){
        CurvePoint curvePointToUpdate = curvePointRepository.findById(1).orElse(new CurvePoint());
        curvePointToUpdate.setCurveId(56);

        CurvePoint result = curvePointService.updateCurvePoint(curvePointToUpdate);

        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getCurveId()).isEqualTo(56);
        assertThat(curvePointRepository.findById(1).orElse(null)).isEqualTo(result);
        assertThat(curvePointRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    public void deleteCurvePointIT(){
        CurvePoint curvePointToDelete = curvePointRepository.findById(2).orElse(new CurvePoint());
        curvePointService.deleteCurvePoint(curvePointToDelete);
        assertThat(curvePointRepository.findAll().size()).isEqualTo(1);
        assertThat(curvePointRepository.findById(2).isPresent()).isFalse();
    }

    @Test
    public void findByIdIT(){
        CurvePoint result = curvePointService.findById(1).orElse(null);
        assertThat(result).isNotNull();
        assertThat(result.getCurveId()).isEqualTo(1);
    }

}
