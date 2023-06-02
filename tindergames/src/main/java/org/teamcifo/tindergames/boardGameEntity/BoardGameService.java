package org.teamcifo.tindergames.boardGameEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardGameService {
    @Autowired
    BoardGameRepository boardGameRepository;

    public Iterable<BoardGame> getAllBoardGames() {

        return boardGameRepository.findAll();
    }

    public BoardGame createGame(BoardGame game){
        BoardGame newGame = boardGameRepository.save(game);
        return newGame;
    }


    public boolean addBoardGameToDB(BoardGame game){

        if (boardGameRepository.findById(game.getGameID()).isPresent()){
            return false;
        }
        boardGameRepository.save(game);
        return true;
    }

    public void addBoardGamesToDB(List<BoardGame> gameList) {
        gameList.stream()
                .forEach(boardGame -> {
                   addBoardGameToDB(boardGame);
                });
    }

    public BoardGame getGameByID(String gameID) {
        Optional<BoardGame> gameFromDB = boardGameRepository.findByGameID(gameID);
        if (gameFromDB.isPresent()) {
            return gameFromDB.get();
        }

        return null;
    }

    public BoardGame getGameByGameTitle(String gameTitle){
        Optional<BoardGame> gameFromDB = boardGameRepository.findByGameTitle(gameTitle);
        if (gameFromDB.isPresent()){
            return gameFromDB.get();
        }
        return null;
    }

    public boolean deleteGameFromDB(BoardGame game){
        if (boardGameRepository.findById(game.getGameID()).isPresent()){
            boardGameRepository.delete(game);
            return true;
        }
        return false;
    }

    public boolean updateGameFromDB(BoardGame game){
        if(boardGameRepository.existsById(game.getGameID())){
            BoardGame gameFromDB = boardGameRepository.findById(game.getGameID()).get();

            if (gameFromDB.getGameTitle() != game.getGameTitle()){
                gameFromDB.setGameTitle(game.getGameTitle());
            }
            if (gameFromDB.getMinPlayers()!= game.getMinPlayers()){
                gameFromDB.setMinPlayers(game.getMinPlayers());
            }
            if (gameFromDB.getMaxPlayers()!= game.getMaxPlayers()){
                gameFromDB.setMaxPlayers(game.getMaxPlayers());
            }
            if (gameFromDB.getMinPlayTime()!= game.getMinPlayTime()){
                gameFromDB.setMinPlayTime(game.getMinPlayTime());
            }
            if (gameFromDB.getMaxPlayTime()!= game.getMaxPlayTime()){
                gameFromDB.setMaxPlayTime(game.getMaxPlayTime());
            }
            boardGameRepository.save(gameFromDB);
            return true;
        }
        return false;
    }
}
