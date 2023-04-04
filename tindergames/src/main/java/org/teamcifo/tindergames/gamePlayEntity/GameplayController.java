package org.teamcifo.tindergames.gamePlayEntity;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.teamcifo.tindergames.userEntity.User;
import org.teamcifo.tindergames.userEntity.UserService;

import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/gameplays")
public class GameplayController {
    @Autowired
    GameplayService gameplayService;
    @Autowired
    UserService userService;

    // Index page
    /**
     * Gameplay controller main page
     * @param containerToView contains relevant data to correctly render Thymeleaf view
     * @return the main page HTML file
     */
    @RequestMapping({"/", ""})
    public String index(Model containerToView) {
        // Retrieve all available users
        containerToView.addAttribute("gameplaysFromController", gameplayService.getAllGameplays());
        return "gameplays/index";
    }

    // CRUD operations
    // - Create a new gameplay:

    /**
     * Gameplay's creation GET method. It initializes a new gameplay so it can be populated
     * @param containerToView contains relevant data for correctly render Thymeleaf view
     * @return the gameplay's creation form HTML file
     */
    @GetMapping("/createGameplay")
    public String createGameplay(Model containerToView) {
        containerToView.addAttribute("gameplay", new Gameplay());
        // TODO: Add available games to select from!
        containerToView.addAttribute("availablePlayers", userService.getAllUsers());
        containerToView.addAttribute("operation", "createGameplay");
        return "gameplays/gameplayForm";
    }

    /**
     * Gameplay's creation POST method. Based on the given ID, if it doesn't exist, the newGameplay is stored in the DB
     * @param id is the ID of the new Gameplay
     * @param newGameplay is the new Gameplay object
     * @param redirectAttributes contains the response message to be rendered in the GET method when finished
     * @return a redirect to the GET method
     */
    @PostMapping("/createGameplay/{id}")
    public String createGameplay(@PathVariable("id") String id, Optional<Gameplay> newGameplay, @RequestParam("players") Set<User> players, RedirectAttributes redirectAttributes) {
        // Check that the ID is not already used inside the DB
        if (gameplayService.getGameplayByID(id) != null) {
            redirectAttributes.addFlashAttribute("responseMessage", "GameplayID " + id + " already used! Try again");
        } else {
            // Check that path variable ID equals the ID of the new gameplay
            if (newGameplay.isPresent() && id.equals(newGameplay.get().getGameplayId())) {
                // Add the gameplay along with the players to the DB
                gameplayService.addGamePlayToDB(newGameplay.get(), players);
                redirectAttributes.addFlashAttribute("responseMessage", "Gameplay " + newGameplay.get().getGameplayId() + " saved");
            } else {
                redirectAttributes.addFlashAttribute("responseMessage", "Received object doesn't contain a gameplay or something wrong with the provided IDs!");
            }
        }
        return "redirect:/gameplays/createGameplay";
    }

    // - Read an existing gameplay by ID:
    /**
     * Retrieve the Gameplay object based on the provided ID
     * @param id is the ID of the desired Gameplay
     * @param containerToView contains the User data from the DB as well as the response message
     * @return the path to the User Details HTML file
     */
    @GetMapping("/id/{id}")
    public String getGameplayByID(@PathVariable("id") String id, Model containerToView) {
        Gameplay gameplayFromDB = gameplayService.getGameplayByID(id);
        containerToView.addAttribute("gameplay", gameplayFromDB);
        if (gameplayFromDB != null) {
            containerToView.addAttribute("responseMessage", "Gameplay ID " + id + " found");
        } else {
            containerToView.addAttribute("responseMessage", "Gameplay ID " + id + " not found!");
        }
        return "gameplays/gameplayDetails";
    }

    // - Update an existing gameplay by ID
    /**
     * Gameplay's update GET method. Based on the proivded ID, if the gameplay exists, it will be retrieved and presented in a form for further modification
     * @param id is the gameplay-to-update ID
     * @param containerToView contains the Gameplay object that matches the given ID
     * @param redirectAttributes contains the response message of the operation
     * @param request contains the HTTP ServletRequest information, useful to redirect to the previous page
     * @return the path to the User update form if the gameplay exists, or a redirect to the previous page otherwise
     */
    @GetMapping("/updateGameplay/{id}")
    public String updateGameplay(@PathVariable("id") String id, Model containerToView, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        // Check if the ID exists in the DB
        Gameplay gameplayFromDB = gameplayService.getGameplayByID(id);
        if (gameplayFromDB != null) {
            // Store the gameplay object and go to the update form
            containerToView.addAttribute("gameplay", gameplayFromDB);
            containerToView.addAttribute("operation", "updateGameplay");
            return "gameplays/gameplayForm";
        } else {
            // Redirect the user to the previous page
            redirectAttributes.addFlashAttribute("responseMessage", "Gameplay ID " + id + " not found!");
            // Return to the previous page
            // TODO: Secure this as the referer isn't always provided by the browser
            return "redirect:" + request.getHeader("Referer");
        }
    }

    /**
     * Gameplay's update POST method. Based on the provided ID, if the gameplay exists into the DB, it is replaced with the updated one
     * @param id is the ID of the gameplay to update
     * @param updatedGameplay is the Gameplay object with the updated values
     * @param redirectAttributes contains the response message
     * @return the path to the GET method
     */
    @PostMapping("/updateGameplay/{id}")
    public String updateGameplay(@PathVariable("id") String id, Optional<Gameplay> updatedGameplay, RedirectAttributes redirectAttributes) {
        // Only act if a gameplay has been sent and IDs match
        if (updatedGameplay.isPresent() && id.equals(updatedGameplay.get().getGameplayId())) {
            // Try to update the gameplay
            if (gameplayService.updateGameplayInDB(updatedGameplay.get())) {
                redirectAttributes.addFlashAttribute("responseMessage", "Gameplay with ID " + id + " updated");
            } else {
                // Something wrong when trying to update the Gameplay on database
                redirectAttributes.addFlashAttribute("responseMessage", "GameplayID " + id + " wasn't updated!");
            }
        } else {
            redirectAttributes.addFlashAttribute("responseMessage", "Received object doesn't contain a gameplay or IDs don't match!");
        }
        // Redirect to the GET method
        return "redirect:/gameplays/updateGameplay/" + id;
    }

    /**
     * Gameplay's deletion method based on its ID. If the ID exists, the gameplay is removed from the DB
     * @param id is the ID of the gameplay to delete
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/deleteGameplay/{id}")
    public String deleteGameplay(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        if (gameplayService.deleteGameplayByIDFromDB(id)) {
            redirectAttributes.addFlashAttribute("responseMessage", "Gameplay with ID " + id + " deleted");
        } else {
            redirectAttributes.addFlashAttribute("responseMessage", "Gameplay with ID " + id + " couldn't be deleted!");
        }
        // Return to the previous page
        return "redirect:/gameplays";
    }
}
