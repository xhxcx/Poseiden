package com.nnk.springboot.controller;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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
public class RatingControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingServiceMock;

    @Test
    public void homeShouldReturnOkAndRatingPageTest() throws Exception {

        mockMvc.perform(get("/rating/list"))
                .andExpect(view().name("rating/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ratings"));
    }

    @Test
    public void getAddBidFormShouldReturnToAddFormTest() throws Exception {

        mockMvc.perform(get("/rating/add"))
                .andExpect(view().name("rating/add"))
                .andExpect(status().isOk());
    }

    @Test
    public void validateAddShouldReturnToAddFormWhenErrorsTest() throws Exception {

        Rating rating = new Rating();
        rating.setFitchRating("More than 125 characters to test the @size annotation validation on this field and then have error in binding result of the post mapping");

        mockMvc.perform(post("/rating/validate")
                .flashAttr("rating", rating))
                .andExpect(view().name("rating/add"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void validateAddShouldRedirectToRatingAndCreateIsCalledWhenNoErrorsTest() throws Exception {

        Rating rating = new Rating();
        rating.setOrderNumber(1);
        mockMvc.perform(post("/rating/validate")
                .flashAttr("rating", rating))
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(status().is3xxRedirection());
        verify(ratingServiceMock, Mockito.times(1)).createRating(rating);
    }

    @Test
    public void showUpdateFormShouldReturnToUpdateFormAndAddExistingRatingToTheModelTest() throws Exception {

        Rating existingRating = new Rating();
        existingRating.setOrderNumber(1);

        when(ratingServiceMock.findById(anyInt())).thenReturn(Optional.of(existingRating));

        mockMvc.perform(get("/rating/update/{id}",1))
                .andExpect(view().name("rating/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ratingToUpdate"));
        verify(ratingServiceMock,Mockito.times(1)).findById(1);
    }

    @Test
    public void showUpdateFormShouldThrowIllegalArgumentExceptionWhenIdDoesntExistTest() {

        when(ratingServiceMock.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mockMvc.perform(get("/rating/update/{id}",1))).hasCause(new IllegalArgumentException("No rating found for this id :1"));
    }

    @Test
    public void updateShouldReturnToUpdateFormWhenErrorsTest() throws Exception {

        Rating existingRating = new Rating();
        existingRating.setFitchRating("Initial Fitch rating");

        when(ratingServiceMock.findById(1)).thenReturn(Optional.of(existingRating));

        existingRating.setFitchRating("More than 125 characters to test the @size annotation validation on this field and then have error in binding result of the post mapping");

        mockMvc.perform(post("/rating/update/{id}",1)
                .flashAttr("rating", existingRating))
                .andExpect(view().name("/rating/update"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void updateShouldRedirectToRatingAndUpdateIsCalledWhenNoErrorsTest() throws Exception {

        Rating existingRating = new Rating();
        existingRating.setOrderNumber(1);

        when(ratingServiceMock.findById(anyInt())).thenReturn(Optional.of(existingRating));

        mockMvc.perform(post("/rating/update/{id}",1)
                .flashAttr("rating", existingRating))
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(status().is3xxRedirection());
        verify(ratingServiceMock, Mockito.times(1)).updateRating(existingRating);
    }

    @Test
    public void deleteBidShouldRedirectToBidlistListAndDeleteIsCalledTest() throws Exception {

        Rating existingRating = new Rating();
        existingRating.setOrderNumber(1);

        when(ratingServiceMock.findById(anyInt())).thenReturn(Optional.of(existingRating));

        mockMvc.perform(get("/rating/delete/{id}",1)
                .flashAttr("rating", existingRating))
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(status().is3xxRedirection());
        verify(ratingServiceMock,Mockito.times(1)).deleteRating(existingRating);
    }

    @Test
    public void deleteBidShouldThrowIllegalArgumentExceptionWhenIdDoesntExistTest() {

        when(ratingServiceMock.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mockMvc.perform(get("/rating/delete/{id}",1))).hasCause(new IllegalArgumentException("No rating found for this id :1"));
    }

}
