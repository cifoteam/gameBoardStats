package org.teamcifo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BoardGame {
    private String gameID;
    private String gameTitle;
    private int minPlayers;
    private int maxPlayers;
    private int minPlayingTime;
    private int maxPlayingTime;

}
