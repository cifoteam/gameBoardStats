package org.teamcifo.tindergames.userRestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamcifo.tindergames.boardGameEntity.BoardGame;
import org.teamcifo.tindergames.boardGameEntity.BoardGameService;
import org.teamcifo.tindergames.userEntity.User;
import org.teamcifo.tindergames.userEntity.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class userRestController {

    @Autowired
    UserService userService;
    @Autowired
    BoardGameService boardGameService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping({"/", ""})
    public Iterable<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("username/{username}")
    public User getUserByUsername(@PathVariable String username){
        Optional<User> user = Optional.ofNullable(userService.getUserByUsername(username));
        if (user.isPresent()){
            return user.get();
        }
        return null;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("userID/{userID}")
    public User getUserByUserID(@PathVariable String userID){
        Optional<User> user = Optional.ofNullable(userService.getUserByID(userID));
        if (user.isPresent()){
            return user.get();
        }
        return null;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path="createUser", consumes = "application/JSON")
    public Boolean createUser(@RequestBody User user){
        if(userService.addUserToDB(user)){
            userService.addUserToDB(user);
            return true;
        }
        return false;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("updateUser")
    public ResponseEntity<User> updateUser(@RequestBody User user){

        HttpHeaders headers = new HttpHeaders();
        headers.add("operation", "updateUser");
        headers.add("version", "api 1.0");
        Optional<User> userToUpdate= Optional.ofNullable(userService.getUserByID(user.getUserId()));

        if (userToUpdate.isPresent()){
            userService.updateUserInDB(user);
            headers.add("operationStatus", "updated");
            return  ResponseEntity.accepted().headers(headers).body(userService.getUserByID(user.getUserId()));
        }
        return ResponseEntity.accepted().headers(headers).body(null);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("deleteUser")
    public ResponseEntity<Boolean> deleteUser(@RequestParam("userID") String userID){
        HttpHeaders headers = new HttpHeaders();
        headers.add("operation", "deleteUser");
        headers.add("version", "api 1.0");

        Optional<User> userFound = Optional.ofNullable(userService.getUserByID(userID));
        if (userFound.isPresent()){
            userService.deleteUserFromDB(userFound.get());
            headers.add("operationStatus", "user deleted");
            return ResponseEntity.accepted().headers(headers).body(true);
        }
        return ResponseEntity.accepted().headers(headers).body(false);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("deleteFriend")
    public ResponseEntity<Boolean> deleteFriend(@RequestParam("userID") String userID, @RequestParam("friendID") String friendID){
        HttpHeaders headers = new HttpHeaders();
        headers.add("operation", "deleteFriend");
        headers.add("version", "api 1.0");

        Optional<User> userFound = Optional.ofNullable(userService.getUserByID(userID));
        if (userFound.isPresent()){
            userService.deleteFriend(userID, friendID, userFound.get().getFriends());
            return ResponseEntity.accepted().headers(headers).body(true);

        }
        return ResponseEntity.accepted().headers(headers).body(false);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("addFriends")
    public ResponseEntity<User> addFriend(@RequestParam("userID") String userID, @RequestParam("friendsIds") List<String> friendsIds){
        HttpHeaders headers = new HttpHeaders();
        headers.add("operation", "addFriend");
        headers.add("version", "api 1.0");

        Optional<User> userFormDB = Optional.ofNullable(userService.getUserByID(userID));
        if(userFormDB.isPresent()){
            userService.addFriends(userFormDB.get().getUserId(), friendsIds);
            return ResponseEntity.accepted().headers(headers).body(userFormDB.get());
        }
        return ResponseEntity.accepted().headers(headers).body(null);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("addGameToCollection")
    public ResponseEntity<User> addGameToCollection(@RequestParam("userID") String userID, @RequestParam("gameID") String gameID){
        HttpHeaders headers = new HttpHeaders();
        headers.add("operation", "addGameToUserCollection");
        headers.add("version", "api 1.0");

        Optional<User> userFromDB = Optional.ofNullable(userService.getUserByID(userID));
        Optional<BoardGame> gameFromDB = Optional.ofNullable(boardGameService.getGameByID(gameID));
        if (userFromDB.isPresent() && gameFromDB.isPresent()){
            userFromDB.get().addGameToCollection(gameFromDB.get());
            updateUser(userFromDB.get());
            return ResponseEntity.accepted().headers(headers).body(userFromDB.get());
        }
        return ResponseEntity.accepted().headers(headers).body(null);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("deleteGameFromCollection")
    public ResponseEntity<User> deleteGameFromCollection(@RequestParam("userID") String userID, @RequestParam("gameID") String gameID){
        HttpHeaders headers = new HttpHeaders();
        headers.add("operation", "deleteGameFromCollection");
        headers.add("version", "api 1.0");

        Optional<User> userFromDB = Optional.ofNullable(userService.getUserByID(userID));
        Optional<BoardGame> gameFromDB = Optional.ofNullable(boardGameService.getGameByID(gameID));
        if (userFromDB.isPresent() && gameFromDB.isPresent()){
            userFromDB.get().deleteGameFromCollection(gameFromDB.get());
            updateUser(userFromDB.get());
            return ResponseEntity.accepted().headers(headers).body(userFromDB.get());
        }
        return ResponseEntity.accepted().headers(headers).body(null);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("login")
    public ResponseEntity<User> login(@RequestParam("username")String username, @RequestParam("password")String password){
        HttpHeaders headers = new HttpHeaders();
        headers.add("operation", "login");
        headers.add("version", "api 1.0");

        Optional<User> loginUser = Optional.ofNullable(userService.logIn(username, password));
        if(loginUser.isPresent()){
            return ResponseEntity.accepted().headers(headers).body(loginUser.get());
        }

        return ResponseEntity.accepted().headers(headers).body(null);
    }
}
// https://stackoverflow.com/questions/34946237/how-delete-object-in-one-side-at-bidirectional-relation
