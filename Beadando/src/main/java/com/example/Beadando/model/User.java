package com.example.Beadando.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String name;
    private String email;
    private String password;


}
