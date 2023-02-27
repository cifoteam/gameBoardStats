package org.teamcifo.storage;
import lombok.Data;
import org.teamcifo.domain.BoardGame;


import java.util.HashMap;
import java.util.Map;

@Data
public class BoardGameDataBase {
    public static HashMap<String, BoardGame> games = new HashMap<String, BoardGame>();

    // TODO: Discuss about storage manager.

    public BoardGameDataBase(){
        this.games =  new HashMap<>();
    }

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
