package org.teamcifo.tindergames.boardGameEntity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.teamcifo.tindergames.utils.Helpers;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="BoardGame")
@Table(name="BOARDGAME_TABLE")

public class BoardGame {
    @Id
    @GenericGenerator(name="system-uuid", strategy="uuid")
    @Column(name="GAME_ID", updatable=false)
    private String gameID;
    @Column(name="GAME_TITLE")
    private String gameTitle;
    @Column(name="MIN_PLAYERS")
    private int minPlayers;
    @Column(name="MAX_PLAYERS")
    private int maxPlayers;
    @Column(name="MIN_PLAYTIME")
    private int minPlayTime;
    @Column(name="MAX_PLAYTIME")
    private int maxPlayTime;

    public BoardGame(String gameTitle){
        this.gameID = Helpers.generateUUID();
        this.gameTitle = gameTitle;
        this.minPlayers = 0;
        this.maxPlayers = 0;
        this.minPlayTime = 0;
        this.maxPlayTime = 0;
    }
    public BoardGame(String gameTitle, int minPlayers, int maxPlayers, int minPlayTime, int maxPlayTime){
        this.gameID = Helpers.generateUUID();
        this.gameTitle = gameTitle;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.minPlayTime = minPlayTime;
        this.maxPlayTime = maxPlayTime;
    }

    //TODO: empty constructor for JPA injection
    /*
    public User() {
        // First initialize the user's games collection
        //this.userGameCollection = new GamesCollection();
        // Then use the collection ID as the user ID
        //this.userId = this.userGameCollection.getCollectionId();
        //this.gameplays = new ArrayList<>();
        // TODO: Create GamesCollection and GamePlay Entities and tables to re-enable these attributes
        this.userId = Helpers.generateUUID();
    }
     */
}
