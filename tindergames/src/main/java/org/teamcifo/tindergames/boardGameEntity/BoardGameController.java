package org.teamcifo.tindergames.boardGameEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.teamcifo.tindergames.userEntity.User;

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
    public String createBoardGame(@PathVariable("id") String gameTitle, Optional<BoardGame> game){
        if(boardGameService.getGameByGameTitle(gameTitle) != null){
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
        containerToView.addAttribute("boardgame", gameFromDB);
        return "boardgames/gameDetails";
    }
    @GetMapping(value = "game/{gameTitle}")
    public String getByGameTitle(@PathVariable("gameTitle") String gameTitle, Model containerToView) {
        BoardGame gameFromDB = boardGameService.getGameByGameTitle(gameTitle);
        containerToView.addAttribute("boardgame", gameFromDB);
        return "boardgames/gameDetails";
    }

    @GetMapping("/deleteGame/{id}")
    public String deleteGame(@PathVariable("id") String id) {
        BoardGame toDelete = boardGameService.getGameByID(id);
        boardGameService.deleteGameFromDB(toDelete);
        return "redirect:/boardgames/";
    }

    @GetMapping(value = "/updategame/{id}")
    public String updateBoardGame(@PathVariable("id") String id, Model containerToView) {
        // Retrieve the user based on the provided ID
            BoardGame gameFromDB = boardGameService.getGameByID(id);
            containerToView.addAttribute("boardgame", gameFromDB);
            return "boardgames/updategame";
        }

    @PostMapping(value = "/updategame/{id}")
    public String updateBoardGame(@PathVariable("id") String id, Optional<BoardGame> updatedGame) {
        BoardGame gameToUpdate = boardGameService.getGameByID(id);

        if (updatedGame.isPresent()) {
            if (gameToUpdate != null  && updatedGame.get().getGameID().equals(gameToUpdate.getGameID())) {
                boardGameService.updateGameFromDB(updatedGame.get());
            }
        }
        // Redirect to the GET method
        return "redirect:/boardgames/updategame/" + id;
    }
}