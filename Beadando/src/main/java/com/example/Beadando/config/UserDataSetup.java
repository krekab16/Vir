package com.example.Beadando.config;

import com.example.Beadando.model.ContactUser;
import com.example.Beadando.model.Image;
import com.example.Beadando.model.Role;
import com.example.Beadando.repository.ContactUserRepository;
import com.example.Beadando.repository.ImageRepository;
import com.example.Beadando.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;


@Component
@Slf4j
public class UserDataSetup implements ApplicationListener<ContextRefreshedEvent> {

    private ContactUserRepository contactUserRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private  ImageRepository imageRepository;
    boolean alreadySetup = false;

    @Autowired
    public UserDataSetup(ContactUserRepository contactUserRepository, RoleRepository roleRepository,
                         BCryptPasswordEncoder passwordEncoder,ImageRepository imageRepository) {
        this.contactUserRepository = contactUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.imageRepository = imageRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(alreadySetup){
            return;
        }


        createRoleIfNotExists("ADMIN");
        createRoleIfNotExists("USER_JPEG");
        createRoleIfNotExists("USER_PNG");
        createRoleIfNotExists("USER_GIF");

        Role adminRole = roleRepository.findByName("ADMIN");

        ContactUser user = new ContactUser();
        user.setEnabled(true);
        user.setRoles(Arrays.asList(adminRole));
        user.setPassword(passwordEncoder.encode("asd123"));
        user.setEmail("rekuci16@gmail.com");
        user.setUsername("rekuci16");

        contactUserRepository.save(user);

        // Automatikus képfeltöltés
        try {
            loadImagesFromFolder("src/main/resources/static/images");
        } catch (IOException e) {
            e.printStackTrace();
        }


        alreadySetup = true;
        log.info("Already setup value: " + alreadySetup);

        log.info("test user saved");
    }



    @Transactional
    public Role createRoleIfNotExists(String name) {
        Role role = roleRepository.findByName(name);

        if(role == null){
            role = new Role(name);
            roleRepository.save(role);
        }
        return role;

    }


    private void loadImagesFromFolder(String folderPath) throws IOException, IOException {
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Image folder does not exist: " + folderPath);
            return;
        }

        List<File> imageFiles = Files.list(folder.toPath())
                .map(java.nio.file.Path::toFile)
                .filter(file -> file.isFile() && file.getName().matches(".*\\.(jpg|jpeg|png|gif)"))
                .toList();

        for (File imageFile : imageFiles) {
            saveImageToDatabase(imageFile);
        }
    }

    private void saveImageToDatabase(File imageFile) throws IOException {
        String fileName = imageFile.getName();
        String contentType = Files.probeContentType(imageFile.toPath());

        // Képfájl cél útvonala a statikus mappában
        File uploadDir = new File("src/main/resources/static/uploads");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();  // Ha nem létezik, létrehozzuk a könyvtárat
        }

        // Cél útvonal a teljes másolási művelethez
        File targetFile = new File(uploadDir, fileName);
        Files.copy(imageFile.toPath(), targetFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        // Mentéshez relatív útvonal (webes eléréshez)
        String filePath = "uploads/" + fileName;

        // Kép objektum létrehozása és mentése az adatbázisba
        Image image = new Image(fileName, contentType, filePath);
        imageRepository.save(image);

        log.info("Fájl mentve a következő elérési útvonalra: " + filePath);
        log.info("Fájl MIME típusa: " + contentType);
    }



}
