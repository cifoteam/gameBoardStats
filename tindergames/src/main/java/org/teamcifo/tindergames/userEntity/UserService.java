package org.teamcifo.tindergames.userEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public boolean addUserToDB(User user) {
        // If the User ID already exists, don't do anything
        if (userRepository.findById(user.getUserId()).isPresent()) {
            return false;
        }

        // Insert the user into the DB
        userRepository.save(user);
        return true;
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByID(String userID) {
        Optional<User> userFromDB = userRepository.findById(userID);
        if (userFromDB.isPresent()) {
            return userFromDB.get();
        }

        return null;
    }

    public User getUserByUsername(String username) {
        Optional<User> userFromDB = userRepository.findByUsername(username);
        if (userFromDB.isPresent()) {
            return userFromDB.get();
        }
        else {
            return null;
        }
    }

    public boolean deleteUserFromDB(User user) {
        if (userRepository.findById(user.getUserId()).isPresent()) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    public boolean updateUserInDB(User user) {
        if (userRepository.existsById(user.getUserId())) {
            userRepository.save(user);
            return true;
        }
        else {
            return false;
        }
    }
}
