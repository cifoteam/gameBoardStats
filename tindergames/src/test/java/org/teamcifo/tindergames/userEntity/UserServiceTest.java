package org.teamcifo.tindergames.userEntity;

import org.h2.tools.Server;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.teamcifo.tindergames.boardGameEntity.BoardGame;
import org.teamcifo.tindergames.boardGameEntity.BoardGameService;
import org.teamcifo.utils.FakeDataGenerator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

// Initialize a Web environment to access H2 console
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    BoardGameService boardGameService;
    @Autowired
    UserRepository userRepository;

    private User fakeUser;
    private List<BoardGame> fakeBoardGames;
    private Random random;

    @BeforeAll
    void warmUp() {
        // Initialize a Random object
        this.random = new Random();
        // Initialize a fake user
        this.fakeUser = FakeDataGenerator.createFakeUser();
        // Initialize a fake list of BoardGames
        this.fakeBoardGames = FakeDataGenerator.populateBoardGames(this.random.nextInt(2, 10));
        // Add the fakeBoardGames to the database
        boardGameService.addBoardGamesToDB(this.fakeBoardGames);
    }

    @BeforeEach
    void init() {
        userService.deleteUserFromDB(this.fakeUser);
        userService.addUserToDB(this.fakeUser);
    }

    @Test
    void addUserToDB() {
        // Check that the user has been saved in the DB
        assertTrue(userRepository.existsById(this.fakeUser.getUserId()));
    }

    @Test
    @Transactional
    void getUserByID() {
        // Retrieve the fakeUser from the DB
        User userFromDB = userService.getUserByID(this.fakeUser.getUserId());
        // Check that both users are the same
        assertEquals(this.fakeUser, userFromDB);
    }

    @Test
    @Transactional
    @Commit
    void deleteUserByID() {
        // Create a fake user
        User userToDelete = FakeDataGenerator.createFakeUser();
        // Add the user to the database
        userService.addUserToDB(userToDelete);
        assertEquals(userToDelete, userService.getUserByID(userToDelete.getUserId()));
        // Delete the user from the database
        userService.deleteUserFromDB(userToDelete);
        assertNull(userService.getUserByID(userToDelete.getUserId()));
    }

    @Test
    @Transactional
    @Commit
    void addGamesToCollection() {
        // Add a random number of games to the collection
        Integer numGames = this.random.nextInt(1, this.fakeBoardGames.size());
        List<String> storedGames = new ArrayList<>();
        for (int i = 0; i < numGames; i++) {
            Integer randomGame = this.random.nextInt(0, this.fakeBoardGames.size());
            String gameId = this.fakeBoardGames.get(randomGame).getGameID();
            while (storedGames.contains(gameId)) {
                // Repeat until a non-already selected game is found
                randomGame = random.nextInt(0, this.fakeBoardGames.size());
                gameId = this.fakeBoardGames.get(randomGame).getGameID();
            }
            storedGames.add(gameId);
        }
        // Add the list of gameIDs into the user's collection
        userService.addGamesToCollection(this.fakeUser.getUserId(), storedGames);
        // Check that the collection has been updated
        User userFromDB = userService.getUserByID(this.fakeUser.getUserId());
        // Assert that the user collection doesn't contain any of the storedGames
        storedGames.stream()
                .forEach(gameId -> {
                    assertTrue(userFromDB.hasGame(boardGameService.getGameByID(gameId)));
                });
    }

    @Test
    @Transactional
    @Commit
    void deleteGamesFromCollection() {
        // Add a random number of games to the collection
        Integer numGames = this.random.nextInt(1, this.fakeBoardGames.size());
        List<String> storedGames = new ArrayList<>();
        for (int i = 0; i < numGames; i++) {
            Integer randomGame = this.random.nextInt(0, this.fakeBoardGames.size());
            String gameId = this.fakeBoardGames.get(randomGame).getGameID();
            while (storedGames.contains(gameId)) {
                // Repeat until a non-already selected game is found
                randomGame = random.nextInt(0, this.fakeBoardGames.size());
                gameId = this.fakeBoardGames.get(randomGame).getGameID();
            }
            storedGames.add(gameId);
        }
        // Add the list of gameIDs into the user's collection
        userService.addGamesToCollection(this.fakeUser.getUserId(), storedGames);
        // Remove all the storedGames from the collection
        userService.deleteGamesFromCollection(this.fakeUser.getUserId(), storedGames);
        // Check that the collection has been updated
        User userFromDB = userService.getUserByID(this.fakeUser.getUserId());
        // Assert that the user collection doesn't contain any of the storedGames
        storedGames.stream()
                .forEach(gameId -> {
                    assertFalse(userFromDB.hasGame(boardGameService.getGameByID(gameId)));
                });
    }
}