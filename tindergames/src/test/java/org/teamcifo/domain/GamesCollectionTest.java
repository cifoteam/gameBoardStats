package org.teamcifo.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.teamcifo.tindergames.domain.BoardGame;
import org.teamcifo.tindergames.domain.GameStats;
import org.teamcifo.tindergames.domain.GamesCollection;
import org.teamcifo.utils.FakeDataGenerator;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
class GamesCollectionTest {

    // Initialize private attributes for testing
    private GamesCollection collectionTest1;
    private GamesCollection collectionTest2;
    private List<BoardGame> fakeBoardGames1;
    private List<BoardGame> fakeBoardGames2;

    private Random random;

    @BeforeEach
    void setUp() {
        // Initialize the random value
        this.random = new Random();

        // Create fake HashMap of BoardGames
        int numFakeObjects = 10;
        this.fakeBoardGames1 = FakeDataGenerator.populateBoardGames(numFakeObjects);
        this.fakeBoardGames2 = FakeDataGenerator.populateBoardGames(numFakeObjects);

        // Create several empty BoardGames collections
        this.collectionTest1 = new GamesCollection();
        this.collectionTest2 = new GamesCollection();
    }

    @Test
    void addGameToCollection() {
        // Add a random game to the collection
        int randomInt = this.random.nextInt(0, 10);
        this.collectionTest1.addGameToCollection(this.fakeBoardGames1.get(randomInt));
        // Assert that the game has been added
        assertTrue(this.collectionTest1.hasGame(this.fakeBoardGames1.get(randomInt)));
        assertEquals(1, this.collectionTest1.size());
    }

    @Test
    void deleteGameFromCollection() {
        // Add a full list of fake boardgames to the collection
        this.fakeBoardGames1
                .forEach(boardGame -> this.collectionTest1.addGameToCollection((boardGame)));
        // Get a random game from the collection
        int randomInt = this.random.nextInt(0, 10);
        // Check that the random game has been added
        assertTrue(this.collectionTest1.hasGame(this.fakeBoardGames1.get(randomInt)));
        // Remove it from the collection
        this.collectionTest1.deleteGameFromCollection(this.fakeBoardGames1.get(randomInt));
        // Assert that the game doesn't exist anymore in the collection
        assertFalse(this.collectionTest1.hasGame(this.fakeBoardGames1.get(randomInt)));
        // Assert that the size of the collection is the expected after deleting the game
        assertEquals(this.fakeBoardGames1.size() - 1, this.collectionTest1.size());
    }

    @Test
    void getGameStats() {
        // Add a full list of fake boardgames to the collection
        this.fakeBoardGames1
                .forEach(boardGame -> this.collectionTest1.addGameToCollection((boardGame)));
        // Get a random game from the collection
        int randomInt = this.random.nextInt(0, 10);
        String randomGameID = this.fakeBoardGames1.get(randomInt).getGameID();
        // Get random game's stats
        GameStats randomStats = this.collectionTest1.getGameStats(randomGameID);
        // Assert that stats are correct - They are empty on creation
        assertEquals(randomStats, new GameStats());
    }

    @Test
    void updateGameStats() {
        // Add a full list of fake boardgames to the collection
        this.fakeBoardGames1
                .forEach(boardGame -> this.collectionTest1.addGameToCollection((boardGame)));
        // Get a random game from the collection
        int randomInt = this.random.nextInt(0, 10);
        String randomGameID = this.fakeBoardGames1.get(randomInt).getGameID();
        // Get old game's stats
        GameStats oldStats = this.collectionTest1.getGameStats(randomGameID);
        // Create a random game stats
        GameStats randomStats = FakeDataGenerator.createFakeStats();
        // Check that old and random aren't equals
        assertNotEquals(oldStats, randomStats);
        // Update the stats
        this.collectionTest1.updateGameStats(randomGameID, randomStats);
        // Assert that stats are correctly updated
        assertEquals(randomStats, this.collectionTest1.getGameStats(randomGameID));
    }

    @Test
    void hasGame() {
        // Add a full list of fake boardgames to the collection
        this.fakeBoardGames1
                .forEach(boardGame -> this.collectionTest1.addGameToCollection((boardGame)));
        // Get a random game from the previously added list
        int randomInt = this.random.nextInt(0, 10);
        // Assert that the game exists in the GamesCollection object
        assertTrue(this.collectionTest1.hasGame(this.fakeBoardGames1.get(randomInt)));
        // Assert that a game from the second list doesn't exist
        assertFalse(this.collectionTest1.hasGame(this.fakeBoardGames2.get(randomInt)));
    }

    @Test
    void copyFrom() {
        // Create two GamesCollection with different collection of BoardGames
        this.fakeBoardGames1
                .forEach(boardGame -> this.collectionTest1.addGameToCollection((boardGame)));
        this.fakeBoardGames2
                .forEach(boardGame -> this.collectionTest2.addGameToCollection((boardGame)));
        // Copy the second collection into the first collection
        this.collectionTest1.copyFrom(this.collectionTest2.getGameStatuses());
        // Check that the games from the second one are available within the first one
        this.fakeBoardGames2
                .forEach(boardGame -> assertTrue(this.collectionTest1.hasGame(boardGame)));
    }

    @Test
    void testEquals() {
        // Create two GamesCollection with the same BoardGames
        this.fakeBoardGames1
                .forEach(boardGame -> {
                    this.collectionTest1.addGameToCollection((boardGame));
                    this.collectionTest2.addGameToCollection((boardGame));
                });
        // Assert that both gameCollections are equal (not the ID)
        assertEquals(this.collectionTest1, this.collectionTest2);
        // Create a different GamesCollection from the previous ones
        this.fakeBoardGames2
                .forEach(boardGame -> this.collectionTest2.addGameToCollection((boardGame)));
        // Assert that the Games Collection are now different
        assertNotEquals(this.collectionTest1, this.collectionTest2);
    }
}