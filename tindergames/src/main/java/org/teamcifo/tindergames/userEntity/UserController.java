package org.teamcifo.tindergames.userEntity;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private String IS_LOGGED_IN = "isLoggedIn";
    private static final String RESPONSE_MESSAGE = "responseMessage";
    @Autowired
    UserService userService;

    private final Set<String> sessionIds = Collections.synchronizedSet(new HashSet<>());

    // Index page
    /**
     * Users controller main page
     * @param containerToView contains relevant data to correctly render Thymeleaf view
     * @return the main page HTML file
     */
    @RequestMapping({"/", ""})
    public String index(Model containerToView) {
        // Retrieve all available users
        containerToView.addAttribute("usersFromController", userService.getAllUsers());
        return "users/index";
    }

    // CRUD operations
    // - Create a new user:
    /**
     * User's creation GET method. It initializes a new user so it can be populated
     * @param containerToView contains the new empty User
     * @return the user's creation form HTML file
     */
    @GetMapping(value = {"/createUser", "/createUser/"})
    public String createUser(Model containerToView) {
        containerToView.addAttribute("user", new User());
        containerToView.addAttribute("operation", "createUser");
        return "users/userForm";
    }

    /**
     * User's creation POST method. If the newUser ID doesn't exist, the newUser object is stored in the DB
     * @param id is the ID of the new User (obsolete)
     * @param newUser is the new User object
     * @param redirectAttributes contains the response message to be rendered in the GET method when finished
     * @return a redirect to the GET method
     */
    @PostMapping(value = "/createUser/{id}")
    public String createUser(@PathVariable("id") String id, Optional<User> newUser, RedirectAttributes redirectAttributes) {
        // Check that the ID is not already used inside the DB
        if (newUser.isPresent() && userService.getUserByID(newUser.get().getUserId()) == null) {
            userService.addUserToDB(newUser.get());
            redirectAttributes.addFlashAttribute(RESPONSE_MESSAGE, "User " + newUser.get().getUsername() + " saved");
        } else {
            redirectAttributes.addFlashAttribute(RESPONSE_MESSAGE, "Received object doesn't contain a user or User ID " + newUser.get().getUserId() + " already in DB!");
        }
        return "redirect:/users/createUser";
    }

    // - Read an existing user by ID
    /**
     * Retrieve the User object based on the provided ID
     * @param id is the ID of the desired User
     * @param containerToView contains the User data from the DB as well as the response message
     * @return the path to the User Details HTML file
     */
    @GetMapping(value = "/id/{id}")
    public String getUserByID(@PathVariable("id") String id, Model containerToView) {
        User userFromDB = userService.getUserByID(id);
        containerToView.addAttribute("user", userFromDB);
        if (userFromDB != null) {
            containerToView.addAttribute(RESPONSE_MESSAGE, "User ID " + id + " found");
        } else {
            containerToView.addAttribute(RESPONSE_MESSAGE, "User ID " + id + " not found!");
        }
        return "users/userDetails";
    }

    // - Read an existing user by username
    /**
     * Retrieve the User object based on the provided username
     * @param username is the username of the desired User
     * @param containerToView contains the User data from the DB as well as the response message
     * @return the path to the User Details HTML file
     */
    @GetMapping(value = "/username/{username}")
    public String getUserByUsername(@PathVariable("username") String username, Model containerToView) {
        User userFromDB = userService.getUserByUsername(username);
        containerToView.addAttribute("user", userFromDB);
        if (userFromDB != null) {
            containerToView.addAttribute(RESPONSE_MESSAGE, "Username " + username + " found");
        } else {
            containerToView.addAttribute(RESPONSE_MESSAGE, "Username " + username + " not found!");
        }
        return "users/userDetails";
    }

    // - Update an existing user by ID
    /**
     * User's update GET method. Based on the provided ID, if the user exists, it will be retrieved and presented in a
     * form for further modification.
     * @param id is the user-to-update ID
     * @param containerToView contains the User object that matches the given ID
     * @param redirectAttributes contains the response message of the operation
     * @return the path to the User update form if the user exists, or a redirect to the User's main page otherwise
     */
    @GetMapping(value = "/updateUser/{id}")
    public String updateUser(@PathVariable("id") String id, Model containerToView, RedirectAttributes redirectAttributes) {
        // Retrieve the user based on the provided ID
        User userFromDB = userService.getUserByID(id);
        if (userFromDB != null) {
            // Store the user object and go to the update form
            containerToView.addAttribute("user", userFromDB);
            containerToView.addAttribute("operation", "updateUser");
            return "users/userForm";
        } else {
            // Redirect the user to the main page
            redirectAttributes.addFlashAttribute(RESPONSE_MESSAGE, "User ID " + id + " not found!");
            return "redirect:/users/";
        }
    }

    /**
     * User's update POST method. Based on the provided ID, if the user exists into the DB, it is replaced
     * with the updated one
     * @param id is the ID of the user to update
     * @param updatedUser is the User object with the updated values
     * @param redirectAttr contains the response message
     * @return the path to the GET method
     */
    @PostMapping(value = "/updateUser/{id}")
    public String updateUser(@PathVariable("id") String id, Optional<User> updatedUser, RedirectAttributes redirectAttr) {
        // Retrieve the user based on the provided ID
        User userToUpdate = userService.getUserByID(id);
        if (updatedUser.isPresent()) {
            // Check if the User already exists and the IDs are equal
            if (userToUpdate != null && updatedUser.get().getUserId().equals(userToUpdate.getUserId())) {
                // Update the user
                userService.updateUserInDB(updatedUser.get());
                // Return response message
                redirectAttr.addFlashAttribute(RESPONSE_MESSAGE, "User " + updatedUser.get().getUsername() + " updated");
            } else {
                // Something wrong with the ID or the Database
                redirectAttr.addFlashAttribute(RESPONSE_MESSAGE, "UserID " + id + " not found or doesn't match the DB");
            }
        } else {
            // Something wrong with the updated object
            redirectAttr.addFlashAttribute(RESPONSE_MESSAGE, "Received object doesn't contain a user!");
        }
        // Redirect to the GET method
        return "redirect:/users/updateUser/" + id;
    }

    /**
     * User's deletion method based on its ID. If the ID exists, the user is removed from the DB
     * @param id is the ID of the user to delete
     * @param redirectAttributes include the response message of the operation
     * @return redirects to the user's main page to show the updated list of users
     */
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        // Retrieve the user based on the provided ID
        User userToDelete = userService.getUserByID(id);
        if (userService.deleteUserFromDB(userToDelete)) {
            redirectAttributes.addFlashAttribute(RESPONSE_MESSAGE, "User " + userToDelete.getUsername() + " deleted");
        }
        else {
            redirectAttributes.addFlashAttribute(RESPONSE_MESSAGE, "User " + userToDelete.getUsername() + " couldn't be deleted!");
        }
        // Return to the users main page
        return "redirect:/users/";
    }

    // Log In endpoint
    @GetMapping("login")
    public String logIn(HttpSession session, Model containerToView) {
        session.setAttribute(IS_LOGGED_IN, false);
        return "users/userLogin";
    }

    @PostMapping("login")
    public String logIn(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session, RedirectAttributes redirectAttributes) {
        // Check if the username and passwords are correct
        User loggedUser = userService.logIn(username, password);
        if (loggedUser != null && !this.sessionIds.contains(session.getId())) {
            // Add the HTTP session id to the sessions set
            this.sessionIds.add(session.getId());
            // Set the login value to true
            session.setAttribute(IS_LOGGED_IN, true);
            // Save useful user attributes in the HTTP session
            session.setAttribute("userId", loggedUser.getUserId());
            session.setAttribute("username", loggedUser.getUsername());
            // Return to the index page
            return "index";
        } else {
            redirectAttributes.addFlashAttribute(RESPONSE_MESSAGE, "Username or password incorrect");
            // Redirect to the login page
            return "redirect:/users/login";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Restart the session attributes
        session.removeAttribute(IS_LOGGED_IN);
        session.removeAttribute("userId");
        session.removeAttribute("username");
        // Remove the current session from the sessions set
        this.sessionIds.remove(session.getId());
        // Return to the main page and force the login again
        return "redirect:/";
    }

    // Other user operations
    @GetMapping("resetPassword/{id}")
    public String resetPassword(@PathVariable("id") String id, Model containerToView, RedirectAttributes redirectAttributes) {
        // Retrieve the user based on the provided ID
        User userFromDB = userService.getUserByID(id);
        if (userFromDB != null) {
            containerToView.addAttribute("userId", id);
            containerToView.addAttribute("oldPassword", userFromDB.getPassword());
            return "users/resetPassword";
        } else {
            redirectAttributes.addFlashAttribute(RESPONSE_MESSAGE, "User ID " + id + " not found!");
            return "redirect:/users";
        }
    }

    @PostMapping("resetPassword/{id}")
    public String resetPassword(@PathVariable("id") String id, @RequestParam("newPassword") String newPassword, RedirectAttributes redirectAttributes) {
        // Try to update the user's password
        if (userService.updateUserPassword(id, newPassword)) {
            redirectAttributes.addFlashAttribute(RESPONSE_MESSAGE, "Password for user ID " + id + " updated");
        } else {
            redirectAttributes.addFlashAttribute(RESPONSE_MESSAGE, "User ID " + id + " not found!");
        }
        return "redirect:/users";
    }

    // Add friends to the User's profile
    @GetMapping("addFriends/{id}")
    public String addFriends(@PathVariable("id") String userId, HttpSession session, Model containerToView, RedirectAttributes redirectAttributes) {
        if ((boolean) session.getAttribute(IS_LOGGED_IN) && userId.equals(session.getAttribute("userId"))) {
            if (userService.getUserByID(userId) != null) {
                // Include both the current friend's list, the user ID and the list of available users to the frontend
                containerToView.addAttribute("currentFriends", userService.getUserFriends(userId));
                containerToView.addAttribute("availableFriends", userService.getAllUsers());
                // TODO: remove userId from Model as it should be already available in the HttpSession --> Modify frontend accordingly
                containerToView.addAttribute("userId", userId);
                containerToView.addAttribute(RESPONSE_MESSAGE, "User ID " + userId + " found");
                return "users/friendsForm";
            } else {
                redirectAttributes.addFlashAttribute(RESPONSE_MESSAGE, "User not logged in! Cancelling friend's update");
                return "redirect:/users/id" + userId;
            }
        }
        redirectAttributes.addFlashAttribute(RESPONSE_MESSAGE, "User not logged in! Cancelling friend's update");
        return "redirect:/users/id" + userId;
    }

    @PostMapping("addFriends/{id}")
    public String addFriend(@PathVariable("id") String userId, @RequestParam("friendsIds") List<String> friendsIds, HttpSession session, RedirectAttributes redirectAttributes) {
        // Only add friends if the user is logged in and the ID received is the same as the one from the current session
        if ((boolean) session.getAttribute(IS_LOGGED_IN) && userId.equals(session.getAttribute("userId"))) {
            if (userService.addFriends(userId, friendsIds)) {
                redirectAttributes.addFlashAttribute(RESPONSE_MESSAGE, "Friends added to user's profile!");
            } else {
                redirectAttributes.addFlashAttribute(RESPONSE_MESSAGE, "User not found in DB, friend's update cancelled");
            }
        } else {
            redirectAttributes.addFlashAttribute(RESPONSE_MESSAGE, "User not logged in, friend's update cancelled");
        }
        return "redirect:/users/id/" + userId;
    }
}
