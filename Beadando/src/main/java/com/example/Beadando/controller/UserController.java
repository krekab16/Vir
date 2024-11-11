package com.example.Beadando.controller;

import com.example.Beadando.model.Image;
import com.example.Beadando.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final ImageService imageService;

    @Autowired
    public UserController(ImageService imageService) {
        this.imageService = imageService;
    }



    @GetMapping
    public String getImageGallery(Authentication authentication, Model model) {
        List<Image> images = imageService.getImagesForUser(authentication);
        model.addAttribute("images", images);
        return "imageGallery";
    }










}
