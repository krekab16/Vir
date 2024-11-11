package com.example.Beadando.repository;

import com.example.Beadando.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByFileType(String fileType);

}
