package org.teamcifo.tindergames.boardGameEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/boardgames")
public class BoardGameController {
    @Autowired
    BoardGameService boardGameService;

    @RequestMapping({"/", ""})
    public String index(Model containerToView) {
        // Retrieve all available users
        containerToView.addAttribute("boardGamesFromController", boardGameService.getAllBoardGames());
        return "boardgames/index";
    }

    @GetMapping(value = {"/creategame", "/creategame/"})
    public String createBoardGame(Model containerToView) {
        containerToView.addAttribute("boardgame", new BoardGame());
        containerToView.addAttribute("operation", "creategame");
        return "boardgames/creategame.html";
    }

    @PostMapping(value = "/creategame/{id}")
    public String createBoardGame(@PathVariable("id") String gameTitle, Optional<BoardGame> game, RedirectAttributes redirectAttributes){
        if(boardGameService.getGameByTitle(gameTitle) != null){
            return "Already on DB";
        }
        if(game.isPresent()) {
            boardGameService.addBoardGameToDB(game.get());
            //TODO: add a confirmation message with redirection
        }
        return "redirect:/boardgames/creategame";
    }

    @GetMapping(value = "/id/{id}")
    public String getGameByID(@PathVariable("id") String id, Model containerToView) {
        BoardGame gameFromDB = boardGameService.getGameByID(id);
        containerToView.addAttribute("user", gameFromDB);
        if (gameFromDB != null) {
            containerToView.addAttribute("responseMessage", "User ID " + id + " found");
        } else {
            containerToView.addAttribute("responseMessage", "User ID " + id + " not found!");
        }
        return "boardgames/gameSheet";
    }
    @GetMapping(value = "/boardgames/{game}")
    public String getUserByUsername(@PathVariable("game") String gameTitle, Model containerToView) {
        BoardGame game = boardGameService.getGameByTitle(gameTitle);
        containerToView.addAttribute("user", game);
        if (game != null) {
            containerToView.addAttribute("responseMessage", "Username " + gameTitle + " found");
        } else {
            containerToView.addAttribute("responseMessage", "Username " + game + " not found!");
        }
        return "boardgames/gameSheet";
    }

    /*
    @GetMapping(value= "/boardgames/{gametitle}")
    public String getBoardGameByTitle(@PathVariable("gametitle") String gameTitle, Model containerToView){
        BoardGame game = boardGameService.getGameByTitle(gameTitle);
        containerToView.addAttribute("game", game);

        return "boardgames/gameSheet";
    }

     */

    //TODO: @GetMapping
    //TODO: public String updateBoardGame();

    //TODO: @PostMapping
    //TODO: public String updateBoardGame();
}
