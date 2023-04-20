package org.teamcifo.tindergames.logic;

import lombok.Data;
import org.teamcifo.tindergames.gamesCollectionEntity.GamesCollection;

import java.util.HashMap;

@Data
public class GamesCollectionManager {
    private static HashMap<String, GamesCollection> gamesCollections = new HashMap<>(); // Key is the userID

    /**
     * The private constructor prevents the class from being instantiated
     * This is required for Code Coverage, as it doesn't cover static classes
     */
    private GamesCollectionManager() throws InstantiationException
    {
        throw new InstantiationException("Instances of this type are forbidden.");
    }

    // TODO: Convert public to private when required

    // Public methods
    // - GamesCollection creation/deletion
    /**
     * Inserts a new gamesCollection entry for a given userID
     * @param userID: the ID of the user
     */
    public static void addGamesCollection(String userID) {
        // Don't overwrite the userID collection if it already exists
        GamesCollection newCollection = gamesCollections.putIfAbsent(userID, new GamesCollection());

        if (newCollection != null) {
            System.out.println("[" + GamesCollectionManager.class + "] userID " + userID + " already has a games collection! We won't overwrite it");
        }
    }

    /**
     * Inserts an existing gamesCollection entry for a given userID
     * Currently only used in tests
     * @param userID: the ID of the user
     */
    public static void addGamesCollection(String userID, GamesCollection gamesCollection) {
        // Don't overwrite the userID collection if it already exists
        GamesCollection newCollection = gamesCollections.putIfAbsent(userID, gamesCollection);

        if (newCollection != null) {
            System.out.println("[" + GamesCollectionManager.class + "] userID " + userID + " already has a games collection! We won't overwrite it");
        }
    }

    /**
     * Deletes the userID games collection
     * @param userID: the ID of the user
     */
    public static void deleteGamesCollection(String userID) {
        GamesCollection deletedCollection = gamesCollections.remove(userID);

        if (deletedCollection == null) {
            System.out.println("[" + GamesCollectionManager.class + "] userID " + userID + " doesn't exist or has an empty collection. Skipping deletion");
        }
    }

    /**
     * Clears the whole gamesCollection hashmap
     */
    public static void clearAll() {
        gamesCollections.clear();
    }

    /**
     * Prints the games collection of the given user
     * @param userID: ID of the user
     */
    public static void printGamesCollection(String userID) {
        // Retrieve the user's collection
        GamesCollection userCollection = getGameCollection(userID);

        // Print the toString method of the GamesCollection
        System.out.println(userCollection);
        // Iterate over all the games in the user's collection
        for (String gameID : userCollection.getGameStatuses().keySet()) {
            // Print the stats for that user's game
            printUserGameStats(userID, gameID);
        }
    }

    // - Size
    public static int size() {
        return gamesCollections.size();
    }

    // User's GamesCollection manipulation
    // - CRUD operations
    /**
     * Inserts a new game entry into the corresponding userID gamesCollection.
     * @param userID: the ID of the user
     * @param gameID: the ID of the game
     */
    public static void addGameToUserCollection(String userID, String gameID) {
        // Retrieve the user's gameCollection
        GamesCollection userCollection = getGameCollection(userID);

        // Check if the game already exists in the collection
        if (userCollection.hasGame(gameID)) {
            System.out.println("[" + GamesCollectionManager.class + "] userID " + userID + " already has the game " + gameID + "in its collection! Exiting");
            return;
        }

        // Add the game to the collection
        userCollection.addGameToCollection(gameID);

    }

    public static void printUserGameStats(String userID, String gameID) {
        // Print the game name
        BoardGameManager.printGame(gameID);
        // Print the game stats
        gamesCollections.get(userID).printGameStats(gameID);
    }

    public static void deleteGameFromUserCollection(String userID, String gameID) {
        // Retrieve the user's collection
        GamesCollection userCollection = getGameCollection(userID);

        // Check if the game exists in the collection
        if (!userCollection.hasGame(gameID)) {
            System.out.println("[" + GamesCollectionManager.class + "] userID " + userID + " doesn't have the game " + gameID + "in its collection! Can't delete it");
            return;
        }

        // Delete the game from the collection
        userCollection.deleteGameFromCollection(gameID);
    }

    public static GamesCollection getGameCollection(String userID) {
        return gamesCollections.getOrDefault(userID, null);
    }

}
