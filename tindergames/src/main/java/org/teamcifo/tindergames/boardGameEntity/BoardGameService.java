package org.teamcifo.tindergames.boardGameEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class BoardGameService {
    @Autowired
    BoardGameRepository boardGameRepository;
    public static HashMap<String, BoardGame> boardgames = new HashMap<>();

    public void createBoardGame(BoardGame game){
        boardgames.put(game.getGameID(), game);
    }

    public BoardGame createBoardGame(){
        BoardGame game = new BoardGame();
        return game;
    }

    public boolean addBoardGameToDB(BoardGame game){
        if (boardGameRepository.findByGameTitle(game.getGameTitle()).isPresent()){
            return false;
        }
        boardGameRepository.save(game);
        return true;
    }

    public Iterable<BoardGame> getAllBoardGames() {
        return boardGameRepository.findAll();
    }
}
