package org.teamcifo.logic;

import lombok.Data;
import org.teamcifo.domain.BoardGame;
import org.teamcifo.storage.BoardGameDataBase;
@Data
public class BoardGameManager {



    public static BoardGame createBoardGame(String gameID, String gameTitle, int minPlayers, int maxPlayers, int minPlayTime, int maxPlayTime) {
        BoardGame game = new BoardGame(gameID, gameTitle, minPlayers, maxPlayers, minPlayTime, maxPlayTime);
        return game;
    }

    public static BoardGame createBoardGame(String gameTitle){
        BoardGame game = new BoardGame(gameTitle);
        return game;
    }

    public static void addBoardGameToDataBase(BoardGame game){
        String gameID = game.getGameID();
        BoardGameDataBase.addBoardGame(gameID, game);
    }

    public static BoardGame findBoardGame(String gameTitle){
        return BoardGameDataBase.searchGameOnDataBase(gameTitle);
    }

    public static void updateGameTitle(BoardGame game, String gameTitle){
        game.setGameTitle(gameTitle);
    }

    public static void updateMinPlayers(BoardGame game, int minPlayers){
        game.setMinPlayers(minPlayers);
    }

    public static void updateMaxPlayers(BoardGame game, int maxPlayers){
        game.setMaxPlayers(maxPlayers);
    }

    public static void updateMinPlayTime(BoardGame game, int minPlayTime){
        game.setMinPlayTime(minPlayTime);
    }

    public static void updateMaxPlayTime(BoardGame game, int maxPlayTime){
        game.setMaxPlayTime(maxPlayTime);
    }
}