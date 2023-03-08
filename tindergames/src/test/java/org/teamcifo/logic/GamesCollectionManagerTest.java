package org.teamcifo.logic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.teamcifo.tindergames.domain.GamesCollection;
import org.teamcifo.tindergames.logic.GamesCollectionManager;
import org.teamcifo.utils.FakeDataGenerator;
import org.teamcifo.tindergames.utils.Helpers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GamesCollectionManagerTest {

    private List<GamesCollection> fakeGamesCollection;
    private Random random;

    // Redirection of System.out and System.err
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        // Regenerate the random
        this.random = new Random();
        // Clear the gamesCollections
        GamesCollectionManager.clearAll();
        // Create new random gamesCollections
        this.fakeGamesCollection = FakeDataGenerator.populateGamesCollection(random.nextInt(1, 10));
        // Redirect the System.out
        System.setOut(new PrintStream(this.out));
    }

    @AfterEach
    public void tearDown() {
        // Restore the System.out
        System.setOut(originalOut);
    }

    @Test
    void addGamesCollectionDoesNotAddAnExistingUser() {
        // Create a fake UserID
        String fakeUserID = Helpers.generateUUID();
        // Initialize the user collection into the manager
        GamesCollectionManager.addGamesCollection(fakeUserID);
        // Check that the GamesCollection has one available collection
        assertEquals(1, GamesCollectionManager.size());
        // Try to initialize the same user collection into the manager
        GamesCollectionManager.addGamesCollection(fakeUserID);
        // Check that the GamesCollection has one available collection
        assertEquals(1, GamesCollectionManager.size());
    }

    @Test
    void deleteGamesCollectionCanNotDeleteAUserThatDoesNotExist() {
        // Create a fake UserID
        String fakeUserID = Helpers.generateUUID();
        // Initialize the user collection into the manager
        GamesCollectionManager.addGamesCollection(fakeUserID);
        // Check that the GamesCollection has one available collection
        assertEquals(1, GamesCollectionManager.size());
        // Delete the user collection from the manager
        GamesCollectionManager.deleteGamesCollection(fakeUserID);
        // Check that the GamesCollection has one available collection
        assertEquals(0, GamesCollectionManager.size());
        // Try to delete the same user collection from the manager
        GamesCollectionManager.deleteGamesCollection(fakeUserID);
        // Check that the GamesCollection is empty
        assertEquals(0, GamesCollectionManager.size());
    }

    @Test
    void printGamesCollection() {
        // Select a random fakeGamesCollection
        int randomCollection = random.nextInt(0, this.fakeGamesCollection.size());
        // Create a fake UserID
        String fakeUserID = Helpers.generateUUID();
        // Initialize the user collection into the manager
        GamesCollectionManager.addGamesCollection(fakeUserID, this.fakeGamesCollection.get(randomCollection));
        GamesCollectionManager.printGamesCollection(fakeUserID);
        // Check the content of the output
        // - Number of games in collection
        List<String> resultOutput = List.of(this.out.toString().split("\n"));
        String numberOfPrintedGamesString;
        numberOfPrintedGamesString = resultOutput.stream()
                .filter(line -> line.startsWith("Number of games"))
                .findFirst()
                .get();

        int numGames = Integer.parseInt(numberOfPrintedGamesString.split("\t")[1]);
        // - Assert that "Number of games:    X" matches the number of games within the randomly selected fakeGamesCollection
        assertEquals(this.fakeGamesCollection.get(randomCollection).size(), numGames);
        // - Number of Game stats printed (Each stat prints a "Price when bought" line)
        int numStats = (int) resultOutput.stream()
                .filter(line -> line.startsWith("Price when bought"))
                .count();
        // - Assert again that the number of stats printed equals the number of games
        assertEquals(numStats, numGames);
    }

    @Test
    void addGameToUserCollection() {
        // Select a random fakeGamesCollection
        int randomCollection = random.nextInt(0, this.fakeGamesCollection.size());
        // Create a fake UserID
        String fakeUserID = Helpers.generateUUID();
        // Initialize an empty collection for that user
        GamesCollectionManager.addGamesCollection(fakeUserID);
        // Insert a random game into that collection
        int randomGameNum = random.nextInt(0, this.fakeGamesCollection.get(randomCollection).size());
        GamesCollectionManager.addGameToUserCollection(
                fakeUserID,
                this.fakeGamesCollection.get(randomCollection).getGameStatuses().keySet().stream().toList().get(randomGameNum)
        );
        // User's collection should only have one game
        assertEquals(1, GamesCollectionManager.getGameCollection(fakeUserID).size());
        // Now add all games in the random fakeGamesCollection
        GamesCollectionManager.getGameCollection(fakeUserID).copyFrom(this.fakeGamesCollection.get(randomCollection).getGameStatuses());
        // User's collection should be the same size as the random fakeGamesCollection
        assertEquals(this.fakeGamesCollection.get(randomCollection).size(), GamesCollectionManager.getGameCollection(fakeUserID).size());
    }

    @Test
    void printUserGameStats() {
        fail("Pending to implement");
    }

    @Test
    void deleteGameFromUserCollection() {
        fail("Pending to implement");
    }
}