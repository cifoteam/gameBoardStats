package org.teamcifo.tindergames.domain;

import lombok.Data;
import lombok.Getter;
import org.teamcifo.tindergames.boardGameEntity.BoardGame;
import org.teamcifo.tindergames.utils.Helpers;

import java.util.HashMap;
import java.util.Map;

@Data
@Getter
public class GamesCollection {
    private String collectionId;
    private Map<String, GameStats> gameStatuses; // Keys are gameIDs

    public GamesCollection() {
        // The collection ID is generated on creation time
        this.collectionId = Helpers.generateUUID();
        this.gameStatuses = new HashMap<>();
    }

    // Public methods
    // - CRUD operations
    public void addGameToCollection(String gameID) {
        this.gameStatuses.putIfAbsent(gameID, new GameStats());
    }

    public void addGameToCollection(BoardGame boardGame) {
        this.addGameToCollection(boardGame.getGameID());
    }

    public GameStats getGameStats(String gameID) {
        return this.gameStatuses.getOrDefault(gameID, null);
    }

    public void updateGameStats(String gameID, GameStats newStats) {
        this.gameStatuses.put(gameID, newStats);
    }

    public void deleteGameFromCollection(String gameID) {
        // Only try to remove the BoardGame if its gameId exists
        if (this.hasGame(gameID)) {
            this.gameStatuses.remove(gameID);
        } else {
            System.out.println("Game " + gameID + " is not included in the collection, can't remove it!");
        }
    }

    public void deleteGameFromCollection(BoardGame boardGame) {
        this.deleteGameFromCollection(boardGame.getGameID());
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
    public void copyFrom(Map<String, GameStats> gamesCollection) {
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
