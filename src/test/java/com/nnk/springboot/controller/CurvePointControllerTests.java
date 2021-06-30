package com.nnk.springboot.controller;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
public class CurvePointControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurvePointService curvePointServiceMock;

    @Test
    public void homeShouldReturnOkAndCurvePointPageTest() throws Exception {

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(view().name("curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("curvePoints"));
    }

    @Test
    public void getAddBidFormShouldReturnToAddFormTest() throws Exception {

        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(view().name("curvePoint/add"))
                .andExpect(status().isOk());
    }

    @Test
    public void validateAddShouldReturnToAddFormWhenErrorsTest() throws Exception {

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(null);
        curvePoint.setTerm(1d);
        curvePoint.setValue(1d);
        mockMvc.perform(post("/curvePoint/validate")
                .flashAttr("curvePoint", curvePoint))
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void validateAddShouldRedirectToCurvePointAndCreateIsCalledWhenNoErrorsTest() throws Exception {

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(1);
        curvePoint.setTerm(1d);
        curvePoint.setValue(1d);
        mockMvc.perform(post("/curvePoint/validate")
                .flashAttr("curvePoint", curvePoint))
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andExpect(status().is3xxRedirection());
        verify(curvePointServiceMock, Mockito.times(1)).createCurvePoint(curvePoint);
    }

    @Test
    public void showUpdateFormShouldReturnToUpdateFormAndAddExistingCurvePointToTheModelTest() throws Exception {

        CurvePoint existingCurvePoint = new CurvePoint();
        existingCurvePoint.setCurveId(1);
        existingCurvePoint.setTerm(1d);
        existingCurvePoint.setValue(1d);

        when(curvePointServiceMock.findById(anyInt())).thenReturn(Optional.of(existingCurvePoint));

        mockMvc.perform(get("/curvePoint/update/{id}",1))
                .andExpect(view().name("curvePoint/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("curvePointToUpdate"));
        verify(curvePointServiceMock,Mockito.times(1)).findById(1);
    }

    @Test
    public void showUpdateFormShouldThrowIllegalArgumentExceptionWhenIdDoesntExistTest() {

        when(curvePointServiceMock.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mockMvc.perform(get("/curvePoint/update/{id}",1))).hasCause(new IllegalArgumentException("No curvePoint found for this id :1"));
    }

    @Test
    public void updateShouldReturnToUpdateFormWhenErrorsTest() throws Exception {

        CurvePoint existingCurvePoint = new CurvePoint();
        existingCurvePoint.setCurveId(1);
        existingCurvePoint.setTerm(1d);
        existingCurvePoint.setValue(1d);
        when(curvePointServiceMock.findById(anyInt())).thenReturn(Optional.of(existingCurvePoint));

        existingCurvePoint.setCurveId(null);

        mockMvc.perform(post("/curvePoint/update/{id}",1)
                .flashAttr("curvePointToUpdate", existingCurvePoint))
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void updateShouldRedirectToCurvePointAndUpdateIsCalledWhenNoErrorsTest() throws Exception {

        CurvePoint existingCurvePoint = new CurvePoint();
        existingCurvePoint.setCurveId(1);
        existingCurvePoint.setTerm(1d);
        existingCurvePoint.setValue(1d);
        when(curvePointServiceMock.findById(anyInt())).thenReturn(Optional.of(existingCurvePoint));

        mockMvc.perform(post("/curvePoint/update/{id}",1)
                .flashAttr("curvePoint", existingCurvePoint))
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andExpect(status().is3xxRedirection());
        verify(curvePointServiceMock, Mockito.times(1)).updateCurvePoint(existingCurvePoint);
    }

    @Test
    public void deleteBidShouldRedirectToBidlistListAndDeleteIsCalledTest() throws Exception {

        CurvePoint existingCurvePoint = new CurvePoint();
        existingCurvePoint.setCurveId(1);
        existingCurvePoint.setTerm(1d);
        existingCurvePoint.setValue(1d);

        when(curvePointServiceMock.findById(anyInt())).thenReturn(Optional.of(existingCurvePoint));

        mockMvc.perform(get("/curvePoint/delete/{id}",1)
                .flashAttr("curvePoint", existingCurvePoint))
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andExpect(status().is3xxRedirection());
        verify(curvePointServiceMock,Mockito.times(1)).deleteCurvePoint(existingCurvePoint);
    }

    @Test
    public void deleteBidShouldThrowIllegalArgumentExceptionWhenIdDoesntExistTest() {

        when(curvePointServiceMock.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mockMvc.perform(get("/curvePoint/delete/{id}",1))).hasCause(new IllegalArgumentException("No curvePoint found for this id :1"));
    }

}
