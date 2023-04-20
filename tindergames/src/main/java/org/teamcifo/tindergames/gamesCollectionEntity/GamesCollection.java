package org.teamcifo.tindergames.gamesCollectionEntity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.teamcifo.tindergames.boardGameEntity.BoardGame;
import org.teamcifo.tindergames.boardGameEntity.BoardGameService;
import org.teamcifo.tindergames.utils.Helpers;

import java.util.HashMap;
import java.util.Map;

// Lombok annotations
@Getter
@Setter
// JPA annotations
@Entity(name="GamesCollection")
@Table(name="GAMES_COLLECTION_TABLE")
public class GamesCollection {
    @Id
    @GenericGenerator(name="system-uuid", strategy="uuid")
    @Column(updatable = false, nullable = false)
    private String collectionId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "game_statuses_mapping",
        joinColumns = {@JoinColumn(name = "game_collection_id", referencedColumnName = "collectionId")},
        inverseJoinColumns = {@JoinColumn(name = "game_stats_id", referencedColumnName = "game_stats_id")})
    @MapKeyJoinColumn(name = "game_id")
    private Map<BoardGame, GameStats> gameStatuses; // Keys are BoardGames


    public GamesCollection() {
        // The collection ID is generated on creation time
        this.collectionId = Helpers.generateUUID();
        this.gameStatuses = new HashMap<>();
    }

    // - Check methods
    public int size() {
        return this.gameStatuses.size();
    }

    public boolean hasGame(BoardGame boardGame) {
        // Check that the collection has an entry with the same game ID
        return this.hasGame(boardGame.getGameID());
    }

    public boolean hasGame(String gameID) {
        // Check that the collection has an entry with the same game ID
        return this.gameStatuses.containsKey(gameID);
    }

    // - Print methods
    public void printGameStats(String gameID) {
        // Print the gameID stats
        System.out.println(this.gameStatuses.getOrDefault(gameID, null));
    }

    // - Manipulation between collections
    public void copyFrom(Map<BoardGame, GameStats> gamesCollection) {
        // TODO: If we copy an entire collection, we're also copying the GameStats of the previous user
        // TODO: Do we really need this method? Right now it is only used in tests
        this.gameStatuses.putAll(gamesCollection);
    }

    // Methods override
    @Override
    public int hashCode() {
        // As collectionID should already be unique for each collection, we can use it to compute the hashcode
        return this.collectionId.hashCode();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GamesCollection that = (GamesCollection) o;
        return getGameStatuses().equals(that.getGameStatuses());
    }

    @Override
    public String toString() {
        StringBuilder collectionStr = new StringBuilder();

        collectionStr.append("Collection ID: ").append(this.getCollectionId());
        collectionStr.append(System.getProperty("line.separator"));
        collectionStr.append("Number of games:\t").append(this.size());

        return collectionStr.toString();
    }

    // Private methods

}
