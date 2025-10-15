package com.example.Backend.User;

import com.example.Backend.Product.Product;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    public void updateUser(User user, long id) {
        User userToUpdate = userRepository.findById(id).orElse(null);
        if (userToUpdate != null) {
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setLastName(user.getLastName());
            userRepository.save(userToUpdate);
        }
    }
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
