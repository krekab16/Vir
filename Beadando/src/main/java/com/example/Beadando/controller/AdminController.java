package com.example.Beadando.controller;


import com.example.Beadando.model.ContactUser;
import com.example.Beadando.model.Image;
import com.example.Beadando.model.Role;
import com.example.Beadando.repository.RoleRepository;
import com.example.Beadando.service.ContactUserDetailsService;
import com.example.Beadando.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    private  final ContactUserDetailsService contactUserDetailsService;
    private final RoleRepository roleRepository;
    private final ImageService imageService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(ContactUserDetailsService contactUserDetailsService, BCryptPasswordEncoder passwordEncoder,
                                             RoleRepository roleRepository,ImageService imageService ) {
        this.contactUserDetailsService = contactUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.imageService = imageService;

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


    @GetMapping("/all")
    public String getAllImageGallery(Model model) {
        List<Image> images = imageService.getAllImages();
        model.addAttribute("images", images);
        return "imageGallery";
    }




    @GetMapping("/editForm/{id}")
    public String editUserForm(@PathVariable Long id, Model model){
        ContactUser contactUser = contactUserDetailsService.findById(id);

        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);

        List<String> roleNames = contactUser.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
        model.addAttribute("roleNames", roleNames);

        model.addAttribute("contactUser", contactUser);
        return "editForm";
    }




    @PostMapping("/edit/{id}")
    public String editUser(@RequestParam List<String> selectedRoles, ContactUser contactUser) {
        List<Role> roles = selectedRoles.stream()
                .map(roleRepository::findByName)
                .toList();

        log.info("Kivalasztottak: " + roles);


        contactUser.setEnabled(true);
        contactUser.setRoles(roles);
        contactUser.setUsername(contactUser.getUsername());
        contactUserDetailsService.saveUser(contactUser);
        return "redirect:/admin";
    }




}
