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
@Entity(name="Boardgame")
@Table(name="BOARDGAMES_TABLE")

public class BoardGame {

    @Id
    @GenericGenerator(name="system-uuid", strategy="uuid")

    @Column(name="GAME_ID")
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
}
