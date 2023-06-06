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
@RequestMapping("/api/boardgames")
public class BoardGameRestController {

    @Autowired
    BoardGameService gameService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping({"/", ""})
    public Iterable<BoardGame> getAllGames(){
        return gameService.getAllBoardGames();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("title/{gameTitle}")
    public BoardGame getGameByTitle(@PathVariable String gameTitle){
        Optional<BoardGame> game = Optional.ofNullable(gameService.getGameByGameTitle(gameTitle));
        if (game.isPresent()){
            return game.get();
        }
        return null;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("id/{gameID}")
    public BoardGame getGameByID(@PathVariable String gameID){
        Optional<BoardGame> game = Optional.ofNullable(gameService.getGameByID(gameID));
        if (game.isPresent()){
            return game.get();
        }
        return null;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path="createGame", consumes = "application/JSON")
    public BoardGame createGame(@RequestBody BoardGame game){
        BoardGame newGame = gameService.createGame(game);
        return newGame;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("deleteGame")
    public ResponseEntity<BoardGame> deleteGame(@RequestParam("gameID") String gameID){
        HttpHeaders headers = new HttpHeaders();
        headers.add("operation", "deleteGame");
        headers.add("version", "api 1.0");

        Optional<BoardGame> gameFound = Optional.ofNullable(gameService.getGameByID(gameID));
        if (gameFound.isPresent()){
            gameService.deleteGameFromDB(gameFound.get());
            headers.add("operationStatus", "deleted");
            return ResponseEntity.accepted().headers(headers).body(gameFound.get());
        }
        return ResponseEntity.accepted().body(null);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("updateGame")
    public ResponseEntity<BoardGame> updateGame(@RequestBody BoardGame game){

        HttpHeaders headers = new HttpHeaders();
        headers.add("operation", "updateGame");
        headers.add("version", "api 1.0");
        Optional<BoardGame> gameFromDB= Optional.ofNullable(gameService.getGameByID(game.getGameID()));

        if (gameFromDB.isPresent()){
            gameService.updateGameFromDB(game);
            headers.add("operationStatus", "updated");
            return  ResponseEntity.accepted().headers(headers).body(gameService.getGameByID(game.getGameID()));
        }
        return ResponseEntity.accepted().headers(headers).body(null);
    }
}
