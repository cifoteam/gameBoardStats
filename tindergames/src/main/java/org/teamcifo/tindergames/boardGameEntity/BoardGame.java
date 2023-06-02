package org.teamcifo.tindergames.boardGameEntity;


import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.teamcifo.tindergames.utils.Helpers;

@Data
@AllArgsConstructor
@Entity(name="BoardGame")
@Table(name="BOARDGAME_TABLE")
@JsonSerialize
public class BoardGame {

    @Id
    @GenericGenerator(name="system-uuid", strategy="uuid")
    @Column(name="gameID", updatable=false)
    private String gameID;
    @Column(name="gameTitle")
    private String gameTitle;
    @Column(name="minPlayers")
    private int minPlayers;
    @Column(name="maxPlayers")
    private int maxPlayers;
    @Column(name="minPlayTime")
    private int minPlayTime;
    @Column(name="maxPlayTime")
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

    public BoardGame(){
        this.gameID = Helpers.generateUUID();
    }

    @Override
    @JsonValue
    public String toString() {
        return this.getGameTitle();
    }
}
