package com.example.Beadando.repository;

import com.example.Beadando.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  UserRepository extends JpaRepository<User, Long> {

}

//    private List<User> users = new ArrayList<>();
//
//    public void addUser(User user){
//        users.add(user);
//    }
//
//    public void deleteUser(UUID id){
//        users.removeIf(user -> user.getId().equals(id));
//
//    }
//
//    public User findById(UUID id) {
//        return users.stream().filter(user -> user.getId().equals(id)).findFirst().get();
//    }
//
//    public void updateUser(User u) {
//        User existing = users.stream().filter(user -> user.getId().equals(u.getId())).findFirst().get();
//        existing.setName(u.getName());
//        existing.setEmail(u.getEmail());
//    }
