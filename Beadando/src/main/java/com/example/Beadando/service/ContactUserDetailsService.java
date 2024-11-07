package com.example.Beadando.service;

import com.example.Beadando.model.ContactUser;
import com.example.Beadando.model.User;
import com.example.Beadando.repository.ContactUserRepository;
import com.example.Beadando.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("contactUserDetailsService")
@Transactional
@Slf4j
public class ContactUserDetailsService implements UserDetailsService {

    private final ContactUserRepository contactUserRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public ContactUserDetailsService(ContactUserRepository contactUserRepository, RoleRepository roleRepository) {
        this.contactUserRepository = contactUserRepository;
        this.roleRepository = roleRepository;
    }


    public void registerUser(ContactUser contactUser) {
        contactUserRepository.save(contactUser);
        log.info("user registered: " + contactUser);
    }


    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        ContactUser user = contactUserRepository.findByUsername(usernameOrEmail);
        if (user == null) {
            user = contactUserRepository.findByEmail(usernameOrEmail);

            if (user == null) {
                log.error("User not found with username (or email): " + usernameOrEmail);
                throw new UsernameNotFoundException("Could not find user with username (or email): " + usernameOrEmail);
            }
        }

        log.info("User found: " + user.getEmail());
        return user;
    }



    public List<ContactUser> getUsers() {
        List<ContactUser> users = contactUserRepository.findAll();
        log.info("All users retrieved: " + users);
        return users;
    }





}
