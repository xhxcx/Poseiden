package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
/**
 * Manage endpoints for User entity
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    private static final Logger logger = LogManager.getLogger(UserController.class);

    /**
     * Add to the model the user list to display
     * @param model
     * @return template name to show user listing
     */
    @RequestMapping("/user/list")
    public String home(Model model)
    {
        logger.debug("Display user list");
        model.addAttribute("users", userService.getAll());
        return "user/list";
    }

    /**
     * Show the addUserForm
     * @param user
     * @return template name to show add user form
     */
    @GetMapping("/user/add")
    public String addUser(User user) {
        logger.info("Show add user form");
        return "user/add";
    }

    /**
     * Validate if the submitted user is correct and then create it or not
     * @param user to create
     * @param result contains errors if the user is not valid
     * @param model
     * @return template name to show, redirection to user listing if user is valid, or else add form
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userService.createUser(user);
            model.addAttribute("users", userService.getAll());
            logger.info("Create new user");
            return "redirect:/user/list";
        }
        logger.error("Validation error adding a new user on fields : " + result.getAllErrors());
        return "user/add";
    }

    /**
     * Show the update form with prefilled values
     * @param id of the user we need to update
     * @param model
     * @return template name to show update user form
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");
        model.addAttribute("user", user);
        logger.info("Update user form displayed to edit user : " + user.getId());
        return "user/update";
    }

    /**
     * Validate if the submitted user is correct and then update it or not
     * @param id of the user we need to update
     * @param user User with new values
     * @param result contains errors if the user is not valid
     * @param model
     * @return template name to show, redirection to user listing if user is valid, or else update form
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Validation error updating user : " + id + " on fields : " + result.getAllErrors());
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userService.updateUser(user);
        model.addAttribute("users", userService.getAll());
        logger.info("Update user for id : " + id);
        return "redirect:/user/list";
    }

    /**
     * Call deletion of the given User
     * @param id of the user we need to delete
     * @param model
     * @return template name to show as a redirection to user listing if id was found, else throw IllegalArgumentException
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userService.deleteUser(user);
        model.addAttribute("users", userService.getAll());
        logger.info("Delete user for id " + id);
        return "redirect:/user/list";
    }
}
