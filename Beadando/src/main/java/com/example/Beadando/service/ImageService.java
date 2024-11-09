package com.example.Beadando.service;

import com.example.Beadando.model.Image;
import com.example.Beadando.repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }


    public List<Image> getImagesForUser(Authentication authentication) {
        List<Image> images = new ArrayList<>();

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String role = authority.getAuthority();

            switch (role) {
                case "USER_JPEG":
                    images.addAll(imageRepository.findByFileType("image/jpeg"));
                    break;
                case "USER_PNG":
                    images.addAll(imageRepository.findByFileType("image/png"));
                    break;
                case "USER_GIF":
                    images.addAll(imageRepository.findByFileType("image/gif"));
                    break;
                default:
                    break;
            }
        }
        return images;
    }


    public List<Image> getAllImages() {
        List<Image> images = imageRepository.findAll();
        log.info("All images retrieved: " + images);
        return images;
    }




}
