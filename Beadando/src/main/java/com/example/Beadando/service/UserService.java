package com.example.Beadando.service;


import com.example.Beadando.model.User;
import com.example.Beadando.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

//    public void updateUser(User user) {
//        userRepository.updateUser(user);
//    }
}
