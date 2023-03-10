package org.teamcifo.tindergames.userEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping({"/", ""})
    public String index(ModelMap containerToView) {
        // Retrieve all available users
        containerToView.addAttribute("usersFromController", userService.getAllUsers());
        //containerToView.addAttribute("responseMessage", null);
        return "users/index";
    }

    @GetMapping(value = {"/createUser", "/createUser/"})
    public String createUser(ModelMap containerToView) {
        containerToView.addAttribute("newUser", new User());
        return "users/createUser";
    }

    @PostMapping(value = "/createUser/{id}")
    public String createUser(@PathVariable("id") String id, Optional<User> newUser, RedirectAttributes redirectAttributes) {
        // Check that the ID is not already used inside the DB
        if (userService.getUserByID(id) != null) {
            redirectAttributes.addFlashAttribute("responseMessage", "UserID " + id + " already used! Try again");
        }
        else {
            userService.addUserToDB(newUser.get());
            redirectAttributes.addFlashAttribute("responseMessage", "User " + newUser.get().getUsername() + " saved");
        }
        return "redirect:";
    }

    @GetMapping(value = "/user/{id}")
    public String getUserByID(@PathVariable("id") String id, ModelMap containerToView) {
        User userFromDB = userService.getUserByID(id);
        containerToView.addAttribute("user", userFromDB);
        if (userFromDB != null) {
            containerToView.addAttribute("responseMessage", "User ID " + id + " found");
        } else {
            containerToView.addAttribute("responseMessage", "User ID " + id + " not found!");
        }
        return "users/userDetails";
    }

    @GetMapping(value = "/user/{username}")
    public String getUserByUsername(@PathVariable("username") String username, ModelMap containerToView) {
        User userFromDB = userService.getUserByUsername(username);
        containerToView.addAttribute("user", userFromDB);
        if (userFromDB != null) {
            containerToView.addAttribute("responseMessage", "Username " + username + " found");
        } else {
            containerToView.addAttribute("responseMessage", "Username " + username + " not found!");
        }
        return "users/userDetails";
    }

    @GetMapping(value = "/updateUser/{id}")
    public String updateUser(@PathVariable("id") String id, ModelMap containerToView, RedirectAttributes redirectAttributes) {
        // Retrieve the user based on the provided ID
        User userFromDB = userService.getUserByID(id);
        if (userFromDB != null) {
            containerToView.addAttribute("user", userFromDB);
            return "users/userUpdate";
        } else {
            redirectAttributes.addFlashAttribute("responseMessage", "User ID " + id + " not found!");
            return "redirect:/users/";
        }
    }

    @PostMapping(value = "/updateUser/{id}")
    public String updateUser(@PathVariable("id") String id, Optional<User> updatedUser, ModelMap containerToView) {
        // Retrieve the user based on the provided ID
        User userToUpdate = userService.getUserByID(id);
        // Check if the User already exists and the IDs are equal
        if (userToUpdate != null && updatedUser.get().getUserId().equals(userToUpdate.getUserId())) {
            // Update the user
            userService.addUserToDB(updatedUser.get());
            // Return response message
            containerToView.addAttribute("responseMessage", "User " + updatedUser.get().getUsername() + " updated");
        } else {
            containerToView.addAttribute("updatedUser", updatedUser.get());
            containerToView.addAttribute("responseMessage", "UserID " + id + " not found or doesn't match the DB");
        }
        return "/updateUser/" + id;
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        // Retrieve the user based on the provided ID
        User userToDelete = userService.getUserByID(id);
        if (userService.deleteUserFromDB(userToDelete)) {
            redirectAttributes.addFlashAttribute("responseMessage", "User " + userToDelete.getUsername() + " deleted");
        }
        else {
            redirectAttributes.addFlashAttribute("responseMessage", "User " + userToDelete.getUsername() + " couldn't be deleted!");
        }
        // Return to the users main page
        return "redirect:/users/";
    }
}
