package com.easy.registration.controllers;

import com.easy.registration.config.properties.Label;
import com.easy.registration.exceptions.EmailExistsException;
import com.easy.registration.exceptions.PasswordsDontMatchException;
import com.easy.registration.models.NewUserDto;
import com.easy.registration.models.UserEntity;
import com.easy.registration.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    private UserService userService;

    private Label label;

    public RegistrationController(UserService userService, Label label){
        this.userService = userService;
        this.label = label;
    }

    @GetMapping("/")
    public String getHomePage(Model model){
        model.addAttribute("users", this.userService.getUsers());

        return "index";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(WebRequest request, Model model){
        model.addAttribute("label", label);
        model.addAttribute("user", new NewUserDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String completeRegistration(
            @ModelAttribute("user")
            @Valid NewUserDto newUserDto,
            BindingResult result,
            WebRequest request,
            Model model,
            Errors errors
    ) {
        UserEntity userEntity = new UserEntity();

        if(!result.hasErrors()){
            try{
                userEntity = this.userService.registerUser(newUserDto);
            }
            catch(EmailExistsException e){
                result.rejectValue("email", "messages.regError", "Email already exists!");
            }
            catch(PasswordsDontMatchException e){
                result.rejectValue("password", "messages.regError", "Passwords do not match!");
            }
        }

        model.addAttribute("label", label);
        model.addAttribute("user", newUserDto);

        if(result.hasErrors()){
            return "registration";
        }
        else{
            return "registration-success";
        }
    }
}
