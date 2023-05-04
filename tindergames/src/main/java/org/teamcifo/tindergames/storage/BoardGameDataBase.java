package org.teamcifo.tindergames.storage;
import lombok.Data;
import org.teamcifo.tindergames.boardGameEntity.BoardGame;


import java.util.HashMap;
import java.util.Map;

@Data
public class BoardGameDataBase {
    public static HashMap<String, BoardGame> games = new HashMap<>();

    // TODO: Discuss about storage manager.

    public static void addBoardGame(String gameID, BoardGame game){
        games.put(gameID, game);
    }
    public static BoardGame searchGameOnDataBase(String gameTitle){
        BoardGame foundGame = null;
        for(Map.Entry<String, BoardGame> entry: games.entrySet()) {

            if(entry.getValue().getGameTitle() == gameTitle) {
                foundGame = entry.getValue();
                break;
            }
        }
        return foundGame;
    }

    public static void listAllBoardGamesOnDataBase(){
        System.out.println(games);
    }
}