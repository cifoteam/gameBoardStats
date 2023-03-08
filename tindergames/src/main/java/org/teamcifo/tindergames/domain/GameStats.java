package org.teamcifo.tindergames.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class GameStats {
    private double buyPrice;
    private int numTimesPlayed;
    private int numWins;
    private boolean owned;

    @Override
    public String toString() {
        StringBuilder stats = new StringBuilder();

        stats.append("Price when bought:\t").append(this.getBuyPrice());
        stats.append(System.getProperty("line.separator"));
        stats.append("Number of times played:\t").append(this.getNumTimesPlayed());
        stats.append(System.getProperty("line.separator"));
        stats.append("Number of wins:\t").append(this.getNumWins());
        stats.append(System.getProperty("line.separator"));
        stats.append("Game owned:\t").append(this.isOwned());
        stats.append(System.getProperty("line.separator"));

        return stats.toString();
    }
}
