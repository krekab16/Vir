package com.example.Beadando.controller;


import com.example.Beadando.model.ContactUser;
import com.example.Beadando.model.Image;
import com.example.Beadando.repository.RoleRepository;
import com.example.Beadando.service.ContactUserDetailsService;
import com.example.Beadando.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private  ContactUserDetailsService contactUserDetailsService;
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setContactUserDetailsService(ContactUserDetailsService contactUserDetailsService, BCryptPasswordEncoder passwordEncoder,
                                             RoleRepository roleRepository) {
        this.contactUserDetailsService = contactUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;

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
        contactUserDetailsService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/editForm/{id}")
    public String editUserForm(@PathVariable Long id, Model model){
        ContactUser contactUser = contactUserDetailsService.editUser(id);
        model.addAttribute("contactUser", contactUser);
        return "editForm";
    }



//    Ezt javítani kell mert szerkesztésnél törli a jelszót.
    @PostMapping("/edit/{id}")
    public String editUser(ContactUser contactUser){
        passwordEncoder.encode(contactUser.getPassword());
        contactUser.setRoles(List.of(roleRepository.findByName("USER")));
        contactUser.setEnabled(true);
        contactUserDetailsService.saveUser(contactUser);
        return "redirect:/admin";
    }




}
