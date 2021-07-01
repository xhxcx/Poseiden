package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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
 * Manage endpoints for CurvePoint entity
 */
@Controller
public class CurveController {

    @Autowired
    private CurvePointService curvePointService;

    private static final Logger logger = LogManager.getLogger(CurveController.class);

    /**
     * Add to the model the curvePoint list to display
     * @param model
     * @return template name to show curvePoint listing
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        logger.debug("Display curvePoint list");
        model.addAttribute("curvePoints", curvePointService.getAll());
        return "curvePoint/list";
    }

    /**
     * Show the addCurvePointForm
     * @param curvePoint
     * @return template name to show add curvePoint form
     */
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint) {
        logger.info("Show add curvePoint form");
        return "curvePoint/add";
    }

    /**
     * Validate if the submitted curvePoint is correct and then create it or not
     * @param curvePoint to create
     * @param result contains errors if the curvePoint is not valid
     * @param model
     * @return template name to show, redirection to curvePoint listing if curvePoint is valid, or else add form
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if(result.hasErrors()) {
            logger.error("Validation error adding a new curvePoint on fields : " + result.getAllErrors());
            return "curvePoint/add";
        }
        curvePointService.createCurvePoint(curvePoint);
        logger.info("Create new curvePoint");
        return "redirect:/curvePoint/list";
    }

    /**
     * Show the update form with prefilled values
     * @param id of the curvePoint we need to update
     * @param model
     * @return template name to show update curvePoint form
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePointToUpdate = curvePointService.findById(id).orElseThrow(() -> new IllegalArgumentException("No curvePoint found for this id :" + id));
        model.addAttribute("curvePointToUpdate", curvePointToUpdate);
        logger.info("Update curvePoint form displayed to edit curvePoint : " + curvePointToUpdate.getId());
        return "curvePoint/update";
    }

    /**
     * Validate if the submitted curvePoint is correct and then update it or not
     * @param id of the curvePoint we need to update
     * @param curvePoint CurvePoint with new values
     * @param result contains errors if the curvePoint is not valid
     * @param model
     * @return template name to show, redirection to curvePoint listing if curvePoint is valid, or else update form
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        if(result.hasErrors()) {
            CurvePoint curvePointToUpdate = curvePointService.findById(id).orElseThrow(() -> new IllegalArgumentException("No curvePoint found for this id :" + id));
            model.addAttribute("curvePointToUpdate", curvePointToUpdate);
            logger.error("Validation error updating curvePoint : " + id + " on fields : " + result.getAllErrors());
            return "curvePoint/update";
        }
        curvePointService.updateCurvePoint(curvePoint);
        logger.info("Update curvePoint for id : " + id);
        return "redirect:/curvePoint/list";
    }

    /**
     * Call deletion of the given CurvePoint
     * @param id of the curvePoint we need to delete
     * @param model
     * @return template name to show as a redirection to curvePoint listing if id was found, else throw IllegalArgumentException
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePointToDelete = curvePointService.findById(id).orElseThrow(() -> new IllegalArgumentException("No curvePoint found for this id :" + id));
        curvePointService.deleteCurvePoint(curvePointToDelete);
        logger.info("Delete curvePoint for id " + id);
        return "redirect:/curvePoint/list";
    }
}
