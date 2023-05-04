package org.teamcifo.tindergames.boardGameRestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamcifo.tindergames.boardGameEntity.BoardGame;
import org.teamcifo.tindergames.boardGameEntity.BoardGameController;
import org.teamcifo.tindergames.boardGameEntity.BoardGameService;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BoardGameRestController {

    @Autowired
    BoardGameService gameService;

    @GetMapping("")
    public String index(){
        return "Welcome to Meeple Match API!";
    }

    @GetMapping("/boardgames/")
    public Iterable<BoardGame> getAllGames(){
        return gameService.getAllBoardGames();
    }

    @GetMapping("/gameTitle/{gameTitle}")
    public BoardGame getGameByTitle(@RequestParam(value = "gameTitle")  @PathVariable("gameTitle") String gameTitle){
        Optional<BoardGame> game = Optional.ofNullable(gameService.getGameByGameTitle(gameTitle));
        if (game.isPresent()){
            return game.get();
        }
        return null;
    }

    @GetMapping("/gameByID/{gameID}")
    public BoardGame getGameByID(@RequestParam(value = "gameID") String gameID){
        Optional<BoardGame> game = Optional.ofNullable(gameService.getGameByGameTitle(gameID));
        if (game.isPresent()){
            return game.get();
        }
        return null;
    }
    @PostMapping(path="createGame", consumes = "application/JSON")
    public BoardGame createGame(@RequestBody BoardGame game){
        BoardGame newGame = gameService.createGame(game);
        return newGame;
    }

    @DeleteMapping("/deleteGame/{gameID}")
    public ResponseEntity<BoardGame> deleteGame(@RequestParam("gameID") String gameID){
        HttpHeaders headers = new HttpHeaders();
        headers.add("operation", "deleteGame");
        headers.add("version", "api 1.0");

        Optional<BoardGame> gameFound = Optional.ofNullable(gameService.getGameByID(gameID));
        boolean game = gameFound.isPresent();
        if (game){
            gameService.deleteGameFromDB(gameFound.get());
            headers.add("operationStatus", "deleted");
            return ResponseEntity.accepted().headers(headers).body(gameFound.get());
        }
        return ResponseEntity.accepted().body(null);
    }

    @PutMapping("/updateGame/{gameID}")
    public ResponseEntity<BoardGame> updateGame(@PathVariable String gameID, @RequestBody BoardGame game){

        HttpHeaders headers = new HttpHeaders();
        headers.add("operation", "updateGame");
        headers.add("version", "api 1.0");
        Optional<BoardGame> gameFromDB= Optional.ofNullable(gameService.getGameByID(gameID));

        if (gameFromDB.isPresent()){
            gameService.updateGameFromDB(game);
            headers.add("operationStatus", "updated");
            return  ResponseEntity.accepted().headers(headers).body(gameFromDB.get());
        }
        return ResponseEntity.accepted().headers(headers).body(null);
    }
}
