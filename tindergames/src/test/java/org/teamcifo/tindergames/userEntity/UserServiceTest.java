package org.teamcifo.tindergames.userEntity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.teamcifo.tindergames.boardGameEntity.BoardGame;
import org.teamcifo.tindergames.boardGameEntity.BoardGameService;
import org.teamcifo.utils.FakeDataGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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
    void setUp(){
        // Initialize a Random object
        this.random = new Random();
        this.fakeUser = FakeDataGenerator.createFakeUser();
        // Add the fakeUser to the database
        userService.addUserToDB(this.fakeUser);
        this.fakeBoardGames = FakeDataGenerator.populateBoardGames(this.random.nextInt(2, 10));
        // Add the fakeBoardGames to the database
        boardGameService.addBoardGamesToDB(this.fakeBoardGames);
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
        for (String gameID: storedGames
             ) {
            // Retrieve the game from the BoardGame DB and assert that the user has it
            assertTrue(userFromDB.getUserGamesCollection().hasGame(boardGameService.getGameByID(gameID)));
        }
    }
}