package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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
 * Manage endpoints for BidList entity
 */
@Controller
public class BidListController {

    @Autowired
    private BidListService bidListService;

    private static final Logger logger = LogManager.getLogger(BidListController.class);

    /**
     * Add to the model the bidList list to display
     * @param model
     * @return template name to show bidList listing
     */
    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        logger.debug("Display bidList list");
        model.addAttribute("bidLists", bidListService.getAll());
        return "bidList/list";
    }

    /**
     * Show the addBidForm
     * @param bid
     * @return template name to show add bid form
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        logger.info("Show add bidList form");
        return "bidList/add";
    }

    /**
     * Validate if the submitted bidList is correct and then create it or not
     * @param bid to create
     * @param result contains errors if the bid is not valid
     * @param model
     * @return template name to show, redirection to bidList listing if bid is valid, or else add form
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        if(result.hasErrors()) {
            logger.error("Validation error adding a new bid on fields : " + result.getAllErrors());
            return "bidList/add";
        }
        bidListService.createBidList(bid);
        logger.info("Create new bid");
        return "redirect:/bidList/list";
    }

    /**
     * Show the update form with prefilled values
     * @param id of the bid we need to update
     * @param model
     * @return template name to show update bid form
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidList bidListToUpdate = bidListService.findById(id).orElseThrow(() -> new IllegalArgumentException("No bidList found for this id :" + id));
        model.addAttribute("bidListToUpdate", bidListToUpdate);
        logger.info("Update bid form displayed to edit bid : " + bidListToUpdate.getBidListId());
        return "bidList/update";
    }

    /**
     * Validate if the submitted bidList is correct and then update it or not
     * @param id of the bid we need to update
     * @param bidList BidList with new values
     * @param result contains errors if the bid is not valid
     * @param model
     * @return template name to show, redirection to bidList listing if bid is valid, or else update form
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        if(result.hasErrors()) {
            BidList bidListToUpdate = bidListService.findById(id).orElseThrow(() -> new IllegalArgumentException("No bidList found for this id :" + id));
            model.addAttribute("bidListToUpdate", bidListToUpdate);
            logger.error("Validation error updating bid : " + id + " on fields : " + result.getAllErrors());
            return "bidList/update";
        }
        bidListService.updateBidList(bidList);
        logger.info("Update bid for id : " + id);
        return "redirect:/bidList/list";
    }

    /**
     * Call deletion of the given BidList
     * @param id of the bid we need to delete
     * @param model
     * @return template name to show as a redirection to bidList listing if id was found, else throw IllegalArgumentException
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        BidList bidListToDelete = bidListService.findById(id).orElseThrow(() -> new IllegalArgumentException("No bidList found for this id :" + id));
        bidListService.deleteBidList(bidListToDelete);
        logger.info("Delete bid for id " + id);
        return "redirect:/bidList/list";
    }
}
