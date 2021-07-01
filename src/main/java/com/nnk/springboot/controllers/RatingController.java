package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * Manage endpoints for Rating entity
 */
@Controller
public class RatingController {
    
    @Autowired
    private RatingService ratingService;

    private static final Logger logger = LogManager.getLogger(RatingController.class);

    /**
     * Add to the model the rating list to display
     * @param model
     * @return template name to show rating listing
     */
    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        logger.debug("Display rating list");
        model.addAttribute("ratings", ratingService.getAll());
        return "rating/list";
    }

    /**
     * Show the addRatingForm
     * @param rating
     * @return template name to show add rating form
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        logger.info("Show add rating form");
        return "rating/add";
    }

    /**
     * Validate if the submitted rating is correct and then create it or not
     * @param rating to create
     * @param result contains errors if the rating is not valid
     * @param model
     * @return template name to show, redirection to rating listing if rating is valid, or else add form
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if(result.hasErrors()) {
            logger.error("Validation error adding a new rating on fields : " + result.getAllErrors());
            return "rating/add";
        }
        ratingService.createRating(rating);
        logger.info("Create new rating");
        return "redirect:/rating/list";
    }

    /**
     * Show the update form with prefilled values
     * @param id of the rating we need to update
     * @param model
     * @return template name to show update rating form
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating ratingToUpdate = ratingService.findById(id).orElseThrow(() -> new IllegalArgumentException("No rating found for this id :" + id));
        model.addAttribute("ratingToUpdate", ratingToUpdate);
        logger.info("Update rating form displayed to edit rating : " + ratingToUpdate.getId());
        return "rating/update";
    }

    /**
     * Validate if the submitted rating is correct and then update it or not
     * @param id of the rating we need to update
     * @param rating Rating with new values
     * @param result contains errors if the rating is not valid
     * @param model
     * @return template name to show, redirection to rating listing if rating is valid, or else update form
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        if(result.hasErrors()) {
            logger.error("Validation error updating rating : " + id + " on fields : " + result.getAllErrors());
            Rating ratingToUpdate = ratingService.findById(id).orElseThrow(() -> new IllegalArgumentException("No rating found for this id :" + id));
            model.addAttribute("ratingToUpdate", ratingToUpdate);
            return "/rating/update";
        }
        ratingService.updateRating(rating);
        logger.info("Update rating for id : " + id);
        return "redirect:/rating/list";
    }

    /**
     * Call deletion of the given Rating
     * @param id of the rating we need to delete
     * @param model
     * @return template name to show as a redirection to rating listing if id was found, else throw IllegalArgumentException
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        Rating ratingToDelete = ratingService.findById(id).orElseThrow(() -> new IllegalArgumentException("No rating found for this id :" + id));
        ratingService.deleteRating(ratingToDelete);
        logger.info("Delete rating for id " + id);
        return "redirect:/rating/list";
    }
}
