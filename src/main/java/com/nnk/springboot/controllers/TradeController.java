package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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
 * Manage endpoints for Trade entity
 */
@Controller
public class TradeController {
    
    @Autowired
    private TradeService tradeService;

    private static final Logger logger = LogManager.getLogger(TradeController.class);

    /**
     * Add to the model the trade list to display
     * @param model
     * @return template name to show trade listing
     */
    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        logger.debug("Display trade list");
        model.addAttribute("trades", tradeService.getAll());
        return "trade/list";
    }
    
    /**
     * Show the addTradeForm
     * @param trade
     * @return template name to show add trade form
     */
    @GetMapping("/trade/add")
    public String addUser(Trade trade) {
        logger.info("Show add trade form");
        return "trade/add";
    }

    /**
     * Validate if the submitted trade is correct and then create it or not
     * @param trade to create
     * @param result contains errors if the trade is not valid
     * @param model
     * @return template name to show, redirection to trade listing if trade is valid, or else add form
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if(result.hasErrors()) {
            logger.error("Validation error adding a new trade on fields : " + result.getAllErrors());
            return "trade/add";
        }
        tradeService.createTrade(trade);
        logger.info("Create new trade");
        return "redirect:/trade/list";
    }

    /**
     * Show the update form with prefilled values
     * @param id of the trade we need to update
     * @param model
     * @return template name to show update trade form
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade tradeToUpdate = tradeService.findById(id).orElseThrow(() -> new IllegalArgumentException("No trade found for this id :" + id));
        model.addAttribute("tradeToUpdate", tradeToUpdate);
        logger.info("Update trade form displayed to edit trade : " + tradeToUpdate.getTradeId());
        return "trade/update";
    }

    /**
     * Validate if the submitted trade is correct and then update it or not
     * @param id of the trade we need to update
     * @param trade Trade with new values
     * @param result contains errors if the trade is not valid
     * @param model
     * @return template name to show, redirection to trade listing if trade is valid, or else update form
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        if(result.hasErrors()) {
            logger.error("Validation error updating trade : " + id + " on fields : " + result.getAllErrors());
            Trade tradeToUpdate = tradeService.findById(id).orElseThrow(() -> new IllegalArgumentException("No trade found for this id :" + id));
            model.addAttribute("tradeToUpdate", tradeToUpdate);
            return "/trade/update";
        }
        tradeService.updateTrade(trade);
        logger.info("Update trade for id : " + id);
        return "redirect:/trade/list";
    }
    
    /**
     * Call deletion of the given Trade
     * @param id of the trade we need to delete
     * @param model
     * @return template name to show as a redirection to trade listing if id was found, else throw IllegalArgumentException
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        Trade tradeToDelete = tradeService.findById(id).orElseThrow(() -> new IllegalArgumentException("No trade found for this id :" + id));
        tradeService.deleteTrade(tradeToDelete);
        logger.info("Delete trade for id " + id);
        return "redirect:/trade/list";
    }
}
