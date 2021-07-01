package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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

@Controller
public class RuleNameController {
    
    @Autowired
    private RuleNameService ruleNameService;

    private static final Logger logger = LogManager.getLogger(RuleNameController.class);

    /**
     * Add to the model the ruleName list to display
     * @param model
     * @return template name to show ruleName listing
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        logger.debug("Display ruleName list");
        model.addAttribute("ruleNames", ruleNameService.getAll());
        return "ruleName/list";
    }
    
    /**
     * Show the addRuleNameForm
     * @param ruleName
     * @return template name to show add ruleName form
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName) {
        logger.info("Show add ruleName form");
        return "ruleName/add";
    }

    /**
     * Validate if the submitted ruleName is correct and then create it or not
     * @param ruleName to create
     * @param result contains errors if the ruleName is not valid
     * @param model
     * @return template name to show, redirection to ruleName listing if ruleName is valid, or else add form
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if(result.hasErrors()) {
            logger.error("Validation error adding a new ruleName on fields : " + result.getAllErrors());
            return "ruleName/add";
        }
        ruleNameService.createRuleName(ruleName);
        logger.info("Create new ruleName");
        return "redirect:/ruleName/list";
    }

    /**
     * Show the update form with prefilled values
     * @param id of the ruleName we need to update
     * @param model
     * @return template name to show update ruleName form
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleName ruleNameToUpdate = ruleNameService.findById(id).orElseThrow(() -> new IllegalArgumentException("No ruleName found for this id :" + id));
        model.addAttribute("ruleNameToUpdate", ruleNameToUpdate);
        logger.info("Update ruleName form displayed to edit ruleName : " + ruleNameToUpdate.getId());
        return "ruleName/update";
    }
    
    /**
     * Validate if the submitted ruleName is correct and then update it or not
     * @param id of the ruleName we need to update
     * @param ruleName RuleName with new values
     * @param result contains errors if the ruleName is not valid
     * @param model
     * @return template name to show, redirection to ruleName listing if ruleName is valid, or else update form
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        if(result.hasErrors()) {
            logger.error("Validation error updating ruleName : " + id + " on fields : " + result.getAllErrors());
            RuleName ruleNameToUpdate = ruleNameService.findById(id).orElseThrow(() -> new IllegalArgumentException("No ruleName found for this id :" + id));
            model.addAttribute("ruleNameToUpdate", ruleNameToUpdate);
            return "/ruleName/update";
        }
        ruleNameService.updateRuleName(ruleName);
        logger.info("Update ruleName for id : " + id);
        return "redirect:/ruleName/list";
    }

    /**
     * Call deletion of the given RuleName
     * @param id of the ruleName we need to delete
     * @param model
     * @return template name to show as a redirection to ruleName listing if id was found, else throw IllegalArgumentException
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        RuleName ruleNameToDelete = ruleNameService.findById(id).orElseThrow(() -> new IllegalArgumentException("No ruleName found for this id :" + id));
        ruleNameService.deleteRuleName(ruleNameToDelete);
        logger.info("Delete ruleName for id " + id);
        return "redirect:/ruleName/list";
    }
}
