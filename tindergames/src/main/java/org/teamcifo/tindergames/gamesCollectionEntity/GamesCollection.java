package org.teamcifo.tindergames.gamesCollectionEntity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.teamcifo.tindergames.boardGameEntity.BoardGame;
import org.teamcifo.tindergames.userEntity.User;
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
    //@GenericGenerator(name="system-uuid", strategy="uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false, name = "user_userId")
    private String collectionId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_userId")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "game_statuses_mapping",
        joinColumns = {@JoinColumn(name = "game_collection_id", referencedColumnName = "user_userId")},
        inverseJoinColumns = {@JoinColumn(name = "game_stats_id", referencedColumnName = "gameStatsId")})
    @MapKeyJoinColumn(name = "game_id")
    private Map<BoardGame, GameStats> gameStatuses; // Keys are BoardGames


    public GamesCollection() {
        // TODO: The collection ID should be shared with the UserId, less indexes to maintain in memory
        //this.collectionId = Helpers.generateUUID();
        this.gameStatuses = new HashMap<>();
    }

    public GamesCollection(User user) {
        this();
        this.user = user;
        // TODO: see if JPA can assign for us the ID when saving the data in H2
        this.collectionId = user.getUserId();
    }

    // CRUD methods?
    // Add game
    public void addGameToCollection(BoardGame boardGame) {
        if (!this.hasGame(boardGame)) {
            this.gameStatuses.put(boardGame, new GameStats());
        }
    }

    public void deleteGameFromCollection(BoardGame boardGame) {
        if (this.hasGame(boardGame)) {
            // TODO: remember to also delete the gamestats from the DB!
            this.gameStatuses.remove(boardGame);
        }
    }

    // Get game stats
    public GameStats getGameStats(BoardGame boardGame) {
        return this.gameStatuses.getOrDefault(boardGame, null);
    }

    public void updateGameStats(BoardGame boardGame, GameStats gameStats) {
        if (this.hasGame(boardGame)) {
            this.gameStatuses.put(boardGame, gameStats);
        }
    }

    // - Check methods
    public int size() {
        return this.gameStatuses.size();
    }

    public boolean hasGame(BoardGame boardGame) {
        // Check that the collection has an entry with the same game ID
        return this.gameStatuses.containsKey(boardGame);
    }

    // - Print methods
    public void printGameStats(String gameID) {
        // Print the gameID stats
        System.out.println(this.gameStatuses.getOrDefault(gameID, null));
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
