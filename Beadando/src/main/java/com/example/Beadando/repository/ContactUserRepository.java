package com.example.Beadando.repository;

import com.example.Beadando.model.ContactUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface  ContactUserRepository extends JpaRepository <ContactUser, Long> {

    ContactUser findByEmail(String email);

    ContactUser findByUsername(String username);

}
