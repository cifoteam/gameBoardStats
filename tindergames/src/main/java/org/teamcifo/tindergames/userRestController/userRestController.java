package org.teamcifo.tindergames.userRestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamcifo.tindergames.boardGameEntity.BoardGame;
import org.teamcifo.tindergames.userEntity.User;
import org.teamcifo.tindergames.userEntity.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class userRestController {

    @Autowired
    UserService userService;

    @GetMapping("/users/")
    public Iterable<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable String username){
        Optional<User> user = Optional.ofNullable(userService.getUserByUsername(username));
        if (user.isPresent()){
            return user.get();
        }
        return null;
    }

    @GetMapping("/userID/{userID}")
    public User getUserByUserID(@PathVariable String userID){
        Optional<User> user = Optional.ofNullable(userService.getUserByID(userID));
        if (user.isPresent()){
            return user.get();
        }
        return null;
    }

    @PostMapping(path="createUser", consumes = "application/JSON")
    public Boolean createUser(@RequestBody User user){
        if(userService.addUserToDB(user)){
            userService.addUserToDB(user);
            return true;
        }
        return false;
    }

    @PutMapping("/updateUser/")
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

    @DeleteMapping("/deleteUser")
    public ResponseEntity<User> deleteUser(@RequestParam("userID") String userID){
        HttpHeaders headers = new HttpHeaders();
        headers.add("operation", "deleteUser");
        headers.add("version", "api 1.0");

        Optional<User> userFound = Optional.ofNullable(userService.getUserByID(userID));
        if (userFound.isPresent()){
            userService.deleteUserFromDB(userFound.get());
            headers.add("operationStatus", "user deleted");
            return ResponseEntity.accepted().headers(headers).body(userFound.get());
        }
        return ResponseEntity.accepted().body(null);
    }
}
