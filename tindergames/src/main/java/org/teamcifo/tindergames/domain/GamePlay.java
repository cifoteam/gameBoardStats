package org.teamcifo.tindergames.domain;


import java.util.List;

import lombok.Data;
@Data
public class GamePlay {
    private String gameId;
    private long time;
    private String winner;
    private String gameplayId;
    private List<String> playersId;
}
