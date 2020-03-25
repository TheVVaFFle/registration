package com.easy.registration.controllers;

import com.easy.registration.config.properties.Label;
import com.easy.registration.models.UserDto;
import com.easy.registration.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

@Controller
public class UserController {
    private UserService userService;
    private Label label;

    public UserController(UserService userService, Label label){
        this.userService = userService;
        this.label = label;
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @GetMapping("/edit/{id}")
    public String getUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", this.userService.getUser(id));

        return "edit-user";
    }

    @PostMapping("/edit/{id}")
    public String completeRegistration(
            @ModelAttribute("user")
            @Valid UserDto userDto,
            BindingResult result,
            WebRequest request,
            Model model,
            Errors errors
    ) {
        if(result.hasErrors()){
            return "edit-user";
        }

        this.userService.editUser(userDto);

        model.addAttribute("label", label);
        model.addAttribute("users", this.userService.getUsers());

        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        this.userService.deleteUser(id);

        model.addAttribute("label", label);
        model.addAttribute("users", this.userService.getUsers());

        return "index";
    }
}
