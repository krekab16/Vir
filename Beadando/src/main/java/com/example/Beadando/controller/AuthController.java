package com.example.Beadando.controller;


import com.example.Beadando.model.ContactUser;
import com.example.Beadando.model.Role;
import com.example.Beadando.repository.RoleRepository;
import com.example.Beadando.service.ContactUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Random;

@Controller
public class AuthController {

    private ContactUserDetailsService contactUserDetailsService;
    private final RoleRepository roleRepository;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(ContactUserDetailsService contactUserDetailsService, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.contactUserDetailsService = contactUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    @Autowired
    public void setContactUserDetailsService(ContactUserDetailsService contactUserDetailsService ) {
        this.contactUserDetailsService = contactUserDetailsService;
    }


    @GetMapping("/login")
    public String showLoginForm(ContactUser contactUser){
        return "login";
    }


    @GetMapping("/registration")
    public String showRegisterForm(ContactUser contactUser){
        return "registration";
    }


    @PostMapping("/registration")
    public String createUser(@Valid ContactUser contactUser, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "registration";
        }

        contactUser.setPassword(passwordEncoder.encode(contactUser.getPassword()));
        Random random = new Random();
        List<String> roles = List.of("USER_JPEG", "USER_PNG", "USER_GIF");
        String randomRole = roles.get(random.nextInt(roles.size()));


        Role role = roleRepository.findByName(randomRole);
        if (role == null) {

            throw new IllegalStateException("Role not found: " + randomRole);
        }

        contactUser.setRoles(List.of(role));
        contactUser.setEnabled(true);

        contactUserDetailsService.registerUser(contactUser);
        return "redirect:/login";
    }



}
