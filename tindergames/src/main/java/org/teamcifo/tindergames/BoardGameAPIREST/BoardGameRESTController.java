package org.teamcifo.tindergames.BoardGameAPIREST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.teamcifo.tindergames.boardGameEntity.BoardGame;
import org.teamcifo.tindergames.boardGameEntity.BoardGameController;
import org.teamcifo.tindergames.boardGameEntity.BoardGameService;

import java.util.Optional;

@RestController
@RequestMapping("api")
public class BoardGameRESTController {
    @Autowired
    BoardGameService boardGameService;

    @GetMapping()
    public String index(){
        return "This is the index of REST controller";
    }

    @GetMapping("boardgames")
    public Iterable<BoardGame> getAllGames(){
        return boardGameService.getAllBoardGames();
    }

    /*
    @GetMapping("getBoardGame")
    public BoardGame findBoardGameByTitle(String gameTitle){
        BoardGame gameFound = boardGameService.getBoardGameByTitle(gameTitle);
        if (gameFound.isPresent()){
            return gameFound.get();
        }
        return null;
    }
    */
}
