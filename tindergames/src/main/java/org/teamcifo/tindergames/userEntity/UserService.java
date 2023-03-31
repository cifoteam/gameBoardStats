package org.teamcifo.tindergames.userEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    /**
     * Add a User entity to the database, only if its ID doesn't already exist
     * @param user is the User entity to add
     * @return true if the User has been added to the database, false otherwise
     */
    public boolean addUserToDB(User user) {
        // If the User ID already exists, don't do anything
        if (userRepository.findById(user.getUserId()).isPresent()) {
            return false;
        }

        // Insert the user into the DB
        userRepository.save(user);
        return true;
    }

    /**
     * Return all available users in the database
     * @return an Iterable of Users
     */
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieve a User entity by its ID
     * @param userID is the User ID
     * @return the User entity if it exists within the database, null otherwise
     */
    public User getUserByID(String userID) {
        Optional<User> userFromDB = userRepository.findById(userID);
        if (userFromDB.isPresent()) {
            return userFromDB.get();
        }

        return null;
    }

    /**
     * Retrieve a User entity by its username
     * @param username is the User username
     * @return the User entity if it exists within the database, null otherwise
     */
    public User getUserByUsername(String username) {
        Optional<User> userFromDB = userRepository.findByUsername(username);
        if (userFromDB.isPresent()) {
            return userFromDB.get();
        }
        return null;
    }

    /**
     * Delete a User entity from the database
     * @param user is the User entity to delete
     * @return true if the operation has successfully finished, false otherwise
     */
    public boolean deleteUserFromDB(User user) {
        if (userRepository.findById(user.getUserId()).isPresent()) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    /**
     * Update a User entity inside the database
     * @param user is the User entity to update
     * @return true if the operation has successfully finished, false otherwise
     */
    public boolean updateUserInDB(User user) {
        if (userRepository.existsById(user.getUserId())) {
            // TODO: Don't simply overwrite the user, update only the modified fields
            User userFromDB = userRepository.findById(user.getUserId()).get();
            // Check all fields from User, update only modified ones
            // TODO: Password update is now done on another form. Should we unify them?
            if (!userFromDB.getFirstName().equals(user.getFirstName())) {
                userFromDB.setFirstName(user.getFirstName());
            }
            if (!userFromDB.getLastName().equals(user.getLastName())) {
                userFromDB.setLastName(user.getLastName());
            }
            if (!userFromDB.getEmail().equals(user.getEmail())) {
                userFromDB.setEmail(user.getEmail());
            }
            if (!userFromDB.getUsername().equals(user.getUsername())) {
                userFromDB.setUsername(user.getUsername());
            }
            userRepository.save(userFromDB);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Update the user's password given its ID and the new password
     * @param userId is the ID of the user
     * @param newPassword is the new password provided by the user
     * @return true if the operation has successfully finished, false otherwise
     */
    public boolean updateUserPassword(String userId, String newPassword) {
        // Retrieve the user from the userRepository
        if (userRepository.existsById(userId)) {
            User userToUpdate = userRepository.findById(userId).get();
            userToUpdate.setPassword(newPassword);
            userRepository.save(userToUpdate);
            return true;
        }
        return false;
    }
}
