package com.example.Beadando.config;


import com.example.Beadando.model.ContactUser;
import com.example.Beadando.model.Role;
import com.example.Beadando.repository.ContactUserRepository;
import com.example.Beadando.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;



@Component
@Slf4j
public class UserDataSetup implements ApplicationListener<ContextRefreshedEvent> {

    private ContactUserRepository contactUserRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;
    boolean alreadySetup = false;

    @Autowired
    public UserDataSetup(ContactUserRepository contactUserRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.contactUserRepository = contactUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(alreadySetup){
            return;
        }


        createRoleIfNotExists("ADMIN");
        createRoleIfNotExists("USER");

        Role adminRole = roleRepository.findByName("ADMIN");

        ContactUser user = new ContactUser();
        user.setEnabled(true);
        user.setRoles(Arrays.asList(adminRole));
        user.setPassword(passwordEncoder.encode("ff"));
        user.setEmail("fff@ez.com");
        user.setUsername("fff");

        contactUserRepository.save(user);


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
}
