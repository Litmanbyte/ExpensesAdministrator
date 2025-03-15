package com.expensesadministrator.expenses.service;

import com.expensesadministrator.expenses.entity.User;
import com.expensesadministrator.expenses.exception.UserNotFoundException;
import com.expensesadministrator.expenses.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
    }

    public User getUserByName(String name){
        return userRepository.findByName(name)
                .orElseThrow(() -> new UserNotFoundException("User not found with name " + name));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(String id, User user) {
        if (userRepository.existsById(id)) {
            user.setId(id);  
            return userRepository.save(user);
        } else {
            throw new UserNotFoundException("User not found with id " + id);
        }
    }

    public void deleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("User not found with id " + id);
        }
    }
}
