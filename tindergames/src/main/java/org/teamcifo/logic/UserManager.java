package org.teamcifo.logic;

import lombok.Data;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import org.teamcifo.domain.*;;
import org.teamcifo.domain.GamesCollection;

@Data
@AllArgsConstructor
@Getter
@Setter
public class UserManager{

    private static Map<String, User> users = new HashMap<>();

    public static GamesCollection getGamesCollection(String userID) {
        // TODO: Correctly access the userID gamesCollection once User class is implemented
        return new GamesCollection();
    }
    public static void addUser(User user) {

        users.put(user.getUserId(), user);
        GamesCollectionManager.addGamesCollection(user.getUserId(), user.getUserGameCollection());
    }

    public static User getUser(String userId) {
        return users.get(userId);
    }

    public static boolean authenticate(String username, String password) {
        User user = getUserByUsername(username);
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(password);
    }

    public static void updateUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static void deleteUser(String userId) {
        users.remove(userId);
        GamesCollectionManager.deleteGamesCollection(userId);
    }

    public static Map<String,User> getAllUsers() { return users; }

    public static void clear(){
        users.clear();
    }

    public static User getUserByUsername(String username) {
        for (User user : users.values()
             ) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static void getGamesCollectionByUsername(String username) {
        for (User user : users.values()
        ) {
            if (user.getUsername().equals(username)) {
                System.out.println(user.getUserGameCollection());
                break;
            }
        }
    }
}



