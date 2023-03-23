package org.teamcifo.tindergames.boardGameEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/boardgames")
public class BoardGameController {
    @Autowired
    BoardGameService boardGameService;

    @GetMapping("/index")
    public String boardGameIndex(Model model){
        BoardGame catan = new BoardGame("Catan", 3, 4, 60, 120);
        BoardGame sagrada = new BoardGame("Roll Player", 1, 4, 60, 90);
        BoardGame arkham = new BoardGame("Arkham Horror", 1, 6, 120, 180);
        ArrayList<BoardGame> boardgames = new ArrayList<>();
        //boardgames.add(catan);
        //boardgames.add(sagrada);
        //boardgames.add(arkham);
        boardGameService.addBoardGameToDB(catan);
        boardGameService.addBoardGameToDB(sagrada);
        boardGameService.addBoardGameToDB(arkham);
        model.addAttribute("boardgames", boardgames);
        return "boardgames/index.html";
    }

    @PostMapping("/createnewgame")
        public String createGame(){

        return "redirect:/boaregames/createnewgame";
    }

    @RequestMapping({"/", ""})
    public String index(Model containerToView) {
        // Retrieve all available users
        containerToView.addAttribute("boardGamesFromController", boardGameService.getAllBoardGames());
        return "boardgames/index.html";
    }
}
