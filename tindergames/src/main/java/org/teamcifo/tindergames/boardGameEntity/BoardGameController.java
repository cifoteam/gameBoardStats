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

    /*
    @GetMapping("/index")
    public String boardGameIndex(Model model){

        BoardGame catan = new BoardGame("Catan", 3, 4, 60, 120);
        BoardGame sagrada = new BoardGame("Roll Player", 1, 4, 60, 90);
        BoardGame arkham = new BoardGame("Arkham Horror", 1, 6, 120, 180);
        BoardGame takenoko = new BoardGame("Takenoko", 2, 4, 45, 90);
        BoardGame carcassone = new BoardGame("Carcassone",2, 4, 30, 90);
        ArrayList<BoardGame> boardgames = new ArrayList<>();
        boardgames.add(catan);
        boardgames.add(sagrada);
        boardgames.add(arkham);
        boardgames.add(takenoko);
        boardgames.add(carcassone);
        boardGameService.addBoardGameToDB(catan);
        boardGameService.addBoardGameToDB(sagrada);
        boardGameService.addBoardGameToDB(arkham);
        boardGameService.addBoardGameToDB(takenoko);
        boardGameService.addBoardGameToDB(carcassone);
        model.addAttribute("boardgames", boardgames);
        return "boardgames/index.html";
    }



    @GetMapping(value = {"/createBoardGame", "/createBoardGame/"})
    public String createBoardGame(Model containerToView) {
        containerToView.addAttribute("boardGame", new BoardGame());
        containerToView.addAttribute("operation", "createBoardGame");
        return "boardgames/createboardgame.html";
    }


    @GetMapping(value ="boardgame/{boardgame}")
    public String getBoardGameByTitle(@PathVariable("gameTitle") String gameTitle, Model containerToView){
    //TODO: will return the game searched my game tittle
        return "boardgames/game";
    }




    @GetMapping("/createnewgame")
        public String createGame(){

        return "boardgames/createnewgame";
    }

     */
}
