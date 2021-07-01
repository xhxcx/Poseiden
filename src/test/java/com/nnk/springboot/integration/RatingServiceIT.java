package com.nnk.springboot.integration;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingServiceImpl;
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
public class RatingServiceIT {

    @Autowired
    private RatingServiceImpl ratingService;

    @Autowired
    private RatingRepository ratingRepository;

    @Test
    public void getAllIT(){
        List<Rating> result = ratingService.getAll();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getOrderNumber()).isEqualTo(1);
    }

    @Test
    public void createRatingIT(){
        Rating newRating = new Rating();
        newRating.setOrderNumber(99);

        Rating result = ratingService.createRating(newRating);

        assertThat(result.getId()).isEqualTo(3);
        assertThat(result.getOrderNumber()).isEqualTo(newRating.getOrderNumber());
        assertThat(ratingRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    public void updateRatingIT(){
        Rating ratingToUpdate = ratingRepository.findById(1).orElse(new Rating());
        ratingToUpdate.setOrderNumber(56);

        Rating result = ratingService.updateRating(ratingToUpdate);

        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getOrderNumber()).isEqualTo(56);
        assertThat(ratingRepository.findById(1).orElse(null)).isEqualTo(result);
        assertThat(ratingRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    public void deleteRatingIT(){
        Rating ratingToDelete = ratingRepository.findById(2).orElse(new Rating());
        ratingService.deleteRating(ratingToDelete);
        assertThat(ratingRepository.findAll().size()).isEqualTo(1);
        assertThat(ratingRepository.findById(2).isPresent()).isFalse();
    }

    @Test
    public void findByIdIT(){
        Rating result = ratingService.findById(1).orElse(null);
        assertThat(result).isNotNull();
        assertThat(result.getOrderNumber()).isEqualTo(1);
    }

}
