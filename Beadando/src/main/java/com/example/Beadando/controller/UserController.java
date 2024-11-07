package com.example.Beadando.controller;


import com.example.Beadando.model.ContactUser;
import com.example.Beadando.model.User;
import com.example.Beadando.service.ContactUserDetailsService;
import com.example.Beadando.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    private  ContactUserDetailsService contactUserDetailsService;


    @Autowired
    public void setContactUserDetailsService(UserService userService, ContactUserDetailsService contactUserDetailsService ) {
        this.userService = userService;
        this.contactUserDetailsService = contactUserDetailsService;

    }

    @GetMapping
    public String getListOfUsers(Model model){
        model.addAttribute("users", contactUserDetailsService.getUsers());
        return "authManagement";
    }

    @PostMapping
    public String postListOfUsers(Model model){
        model.addAttribute("users", contactUserDetailsService.getUsers());
        return "authManagement";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return "redirect:/user";
    }

    @GetMapping("/editForm/{id}")
    public String editUserForm(@PathVariable Long id, Model model){
        User u = userService.getUserById(id);
        model.addAttribute("user", u);
        return "editForm";
    }

    @PostMapping("/edit/{id}")
    public String editUser(User user){
        userService.saveUser(user);
        return "redirect:/user";
    }




}
