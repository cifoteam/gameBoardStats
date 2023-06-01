package org.teamcifo.tindergames.logic;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.teamcifo.tindergames.boardGameEntity.BoardGame;
import org.teamcifo.tindergames.logic.BoardGameManager;
import org.teamcifo.tindergames.storage.BoardGameDataBase;
import org.teamcifo.utils.FakeDataGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BoardGameManagerTest {

    @Test
    public  void createBoardGameTest(){
        // create fake game with all params test
        BoardGame fakeGame;
        fakeGame = FakeDataGenerator.createFakeGame();
        //gets all the game params and stores to variables.
        String gameIDToTest = fakeGame.getGameID();
        String gameTitleToTest = fakeGame.getGameTitle();
        int minPlayersToTest = fakeGame.getMinPlayers();
        int maxPlayersToTest = fakeGame.getMaxPlayers();
        int minPlayTimeToTest = fakeGame.getMinPlayTime();
        int maxPlayTimeToTest = fakeGame.getMaxPlayTime();

        // creates a BoardGame with the manager function with all the values from the fake game
        BoardGame gameToTest = BoardGameManager.createBoardGame(gameIDToTest, gameTitleToTest, minPlayersToTest,
                                                                maxPlayersToTest, minPlayTimeToTest, maxPlayTimeToTest);

        assertEquals(fakeGame, gameToTest);

        // Test the create game method with only gameTitle as a param
        BoardGame secondFakeGame;
        secondFakeGame = FakeDataGenerator.createFakeGame();

        String gameTitle = secondFakeGame.getGameTitle();
        BoardGame gameWithOnlyStringAsParam = BoardGameManager.createBoardGame(gameTitle);
        assertEquals(gameTitle, gameWithOnlyStringAsParam.getGameTitle());
        assertNotEquals(secondFakeGame.getGameID(), gameWithOnlyStringAsParam.getGameID());
    }
    @Test
    public void addBoardGameToDataBaseTest(){
        BoardGame fakeGame = FakeDataGenerator.createFakeGame();
        BoardGame fakeGame2 = FakeDataGenerator.createFakeGame();
        BoardGameManager.addBoardGameToDataBase(fakeGame);
        BoardGameManager.addBoardGameToDataBase(fakeGame2);
        BoardGameDataBase.listAllBoardGamesOnDataBase();
        // TODO: Add assertions to this test
    }

    @Test
    public void findGameTest(){
        BoardGame fakeGame = BoardGameManager.createBoardGame("Catan");
        BoardGame fakeGame2 = BoardGameManager.createBoardGame("Takenoko");
        BoardGame fakeGame3 = BoardGameManager.createBoardGame("Pandemic");
        BoardGame fakeGame4 = BoardGameManager.createBoardGame("Arkham horror");
        BoardGameManager.addBoardGameToDataBase(fakeGame);
        BoardGameManager.addBoardGameToDataBase(fakeGame2);
        BoardGameManager.addBoardGameToDataBase(fakeGame3);
        BoardGameManager.addBoardGameToDataBase(fakeGame4);

        BoardGame foundGame = BoardGameManager.findBoardGame("Pandemic");
        assertEquals(fakeGame3, foundGame);
    }

    @Test
    public void updateGameTitleTest(){
        BoardGame game = FakeDataGenerator.createFakeGame();
        Faker fake = new Faker();
        String fakeNewGameTitle = fake.backToTheFuture().character();
        String initialGameTitle = game.getGameTitle();
        BoardGameManager.updateGameTitle(game, fakeNewGameTitle);
        assertNotEquals(initialGameTitle, game.getGameTitle());

    }
    @Test
    public void updateMinPlayersTest(){
        Faker fake = new Faker();
        BoardGame game = FakeDataGenerator.createFakeGame();
        int initialMinPlayerCount = game.getMinPlayers();
        int newMinPlayerCount = fake.number().numberBetween(1, 6);
        BoardGameManager.updateMinPlayers(game, newMinPlayerCount);
        assertNotEquals(initialMinPlayerCount, game.getMinPlayers());

    }
    @Test
    public void updateMaxPlayersTest(){
        Faker fake = new Faker();
        BoardGame game = FakeDataGenerator.createFakeGame();
        int initialMaxPlayerCount = game.getMaxPlayers();
        int newMaxPlayerCount = fake.number().numberBetween(1, 6);
        BoardGameManager.updateMaxPlayers(game, newMaxPlayerCount);
        assertNotEquals(initialMaxPlayerCount, game.getMaxPlayers());
    }
    @Test
    public void updateMinPlayTimeTest(){
        Faker fake = new Faker();
        BoardGame game = FakeDataGenerator.createFakeGame();
        int initialMinPlayTime = game.getMinPlayTime();
        int newMinPlayTime = fake.number().numberBetween(1, 6);
        BoardGameManager.updateMinPlayTime(game, newMinPlayTime);
        assertNotEquals(initialMinPlayTime, game.getMinPlayTime());
    }
    @Test
    public void updateMaxPlayTimeTest(){
        Faker fake = new Faker();
        BoardGame game = FakeDataGenerator.createFakeGame();
        int initialMaxPlayTime = game.getMaxPlayTime();
        int newMaxPlayTime = fake.number().numberBetween(1, 6);
        BoardGameManager.updateMaxPlayTime(game, newMaxPlayTime);
        assertNotEquals(initialMaxPlayTime, game.getMaxPlayTime());
    }
}