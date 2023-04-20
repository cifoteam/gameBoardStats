package org.teamcifo.tindergames.gamesCollectionEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.teamcifo.tindergames.utils.Helpers;

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
}
