package org.teamcifo.tindergames.gamesCollectionEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.teamcifo.tindergames.utils.Helpers;

import java.util.Objects;

@Getter
@Setter
// JPA annotations
@Entity(name = "GameStats")
@Table(name = "GAME_STATS")
public class GameStats {
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(updatable = false, nullable = false)
    private String gameStatsId;
    private double buyPrice;
    private int numTimesPlayed;
    private int numWins;
    private boolean owned;

    public GameStats() {
        this.gameStatsId = Helpers.generateUUID();
        this.buyPrice = 0.0;
        this.numTimesPlayed = 0;
        this.numWins = 0;
        this.owned = false;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameStats gameStats)) return false;
        return Double.compare(gameStats.buyPrice, buyPrice) == 0 && numTimesPlayed == gameStats.numTimesPlayed && numWins == gameStats.numWins && owned == gameStats.owned;
    }

    @Override
    public int hashCode() {
        return Objects.hash(buyPrice, numTimesPlayed, numWins, owned);
    }
}
