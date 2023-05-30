package org.teamcifo.tindergames.userEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamcifo.tindergames.boardGameEntity.BoardGame;
import org.teamcifo.tindergames.boardGameEntity.BoardGameService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BoardGameService boardGameService;

    /**
     * Add a User entity to the database, only if its ID doesn't already exist
     * @param user is the User entity to add
     * @return true if the User has been added to the database, false otherwise
     */
    public boolean addUserToDB(User user) {
        // If the User ID already exists, don't do anything
        if (userRepository.existsById(user.getUserId())) {
            return false;
        }

        // First of all, add the GamesCollection to the DB
        //gamesCollectionService.addGamesCollectionToDB(user.getUserGamesCollection());
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
        // TODO: Refactor the method by using the 'getUserById' method?
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
        // TODO: Refactor the method by using the 'getUserById' method
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
            /*
            if (!userFromDB.getUserGamesCollection().equals(user.getUserGamesCollection())) {
                userFromDB.setUserGamesCollection(user.getUserGamesCollection());
            }
            */

            // Save the updated user
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
        // TODO: Refactor the method by using the 'getUserById' method
        if (userRepository.existsById(userId)) {
            User userToUpdate = userRepository.findById(userId).get();
            userToUpdate.setPassword(newPassword);
            userRepository.save(userToUpdate);
            return true;
        }
        return false;
    }

    public User logIn(String username, String password) {
        // First retrieve the user by its username
        // TODO: Refactor the method by using the 'getUserByUsername' method
        Optional<User> loginUser = userRepository.findByUsername(username);
        // If the user exists, check if the password is correct and return that user
        if (loginUser.isPresent() && loginUser.get().checkPassword(password)) {
            return loginUser.get();
        }
        // Return null if login fails
        return null;
    }

    /**
     * Return the list of friends for a given user
     * @param userId
     * @return
     */
    public Iterable<User> getUserFriends(String userId) {
        // Retrieve the user from the userRepository
        User userFromDB = getUserByID(userId);
        if (userFromDB != null) {
            return userFromDB.getFriends();
        }
        return null;
    }

    /**
     * Update the list of friendIds of a user
     * @param userId is the ID of the user to update
     * @param friendIds is a list of User IDs
     * @return true if the user to update exists, false otherwise
     */
    public boolean addFriends(String userId, List<String> friendIds) {
        // Retrieve the user from the userRepository
        User userFromDB = getUserByID(userId);
        if (userFromDB != null) {
            friendIds.stream()
                    .filter(friendId -> !(friendId.equals(userId)))
                    .forEach(friendId -> {
                        // Retrieve the friend from the DB
                        User friend = getUserByID(friendId);
                        if (friend != null) {
                            userFromDB.addFriend(friend);
                        }
                    });
            // Save the updated user
            userRepository.save(userFromDB);
            return true;
        }
        return false;
    }

    public boolean deleteFriend(String userId, String friendIdToRemove, Set<User> friends){
        User userFromDB= getUserByID(userId);
        if (userFromDB != null) {
            friends.stream()
                    .filter(friendId -> !(friendId.equals(userId)))
                    .forEach(friendId -> {
                        // Retrieve the friend from the DB
                        User friendToRemove = getUserByID(friendIdToRemove);
                        if (friendToRemove != null) {
                            userFromDB.deleteFriend(friendToRemove);
                        }
                    });
            // Save the updated user
            userRepository.save(userFromDB);
            return true;
        }
        return false;
    }

    /**
     * Update the GamesCollection of a user
     * @param userId is the ID of the user to update
     * @param boardGameIds is a list of BoardGame IDs
     * @return true if the user to update exists, false otherwise
     */
    public boolean addGamesToCollection(String userId, List<String> boardGameIds) {
        User userFromDB = getUserByID(userId);
        if (userFromDB != null) {
            boardGameIds.stream()
                    .forEach(boardGameId -> {
                        BoardGame boardGame = boardGameService.getGameByID(boardGameId);
                        if (boardGame != null) {
                            userFromDB.addGameToCollection(boardGame);
                        }
                    });
            // Save the updated user
            userRepository.save(userFromDB);
            return true;
        }
        return false;
    }

    public boolean deleteGamesFromCollection(String userId, List<String> boardGameIds) {
        User userFromDB = getUserByID(userId);
        if (userFromDB != null) {
            boardGameIds.stream()
                    .forEach(boardGameId -> {
                        BoardGame boardGame = boardGameService.getGameByID(boardGameId);
                        if (boardGame != null) {
                            userFromDB.deleteGameFromCollection(boardGame);
                        }
                    });
            // Save the updated user
            userRepository.save(userFromDB);
            return true;
        }
        return false;
    }
}
