package org.teamcifo.testutils;

import com.github.javafaker.Faker;
import org.teamcifo.domain.BoardGame;
import org.teamcifo.domain.User;
import org.teamcifo.utils.Helpers;

import java.util.ArrayList;
import java.util.List;

public class FakeDataGenerator {

    public static List<BoardGame> populateBoardGames(int numGames) {
        // TODO: Create a random number of BoardGames and return them
        List<BoardGame> boardGameList = new ArrayList<>();
        for (int i = 0; i < numGames; i++) {
            boardGameList.add(createFakeGame());
        }
        return boardGameList;
    }

    public static List<User> populateUsers(int numUsers) {
        // TODO: Create a random number of users and return them
        return new ArrayList<>();
    }

    public static BoardGame createFakeGame() {
        Faker faker = new Faker();
        String gameID = Helpers.generateUUID();
        String gameTitle = faker.esports().game();
        int minPlayers = faker.number().numberBetween(1, 4);
        int maxPlayers = faker.number().numberBetween(minPlayers, 10);
        int minPlayingTime = faker.number().numberBetween(10, 30);
        int maxPlayingTime = faker.number().numberBetween(minPlayingTime, 240);

        return new BoardGame(gameID, gameTitle, minPlayers, maxPlayers, minPlayingTime, maxPlayingTime);
    }

}
