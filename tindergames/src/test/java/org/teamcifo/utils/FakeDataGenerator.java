package org.teamcifo.utils;

import com.github.javafaker.Faker;
import org.teamcifo.tindergames.boardGameEntity.BoardGame;
import org.teamcifo.tindergames.gamesCollectionEntity.GameStats;
import org.teamcifo.tindergames.userEntity.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FakeDataGenerator {

    private static final Faker faker = new Faker();

    public static List<BoardGame> populateBoardGames(int numGames) {
        // TODO: Create a random number of BoardGames and return them
        List<BoardGame> boardGameList = new ArrayList<>();
        for (int i = 0; i < numGames; i++) {
            boardGameList.add(createFakeGame());
        }
        return boardGameList;
    }

    public static BoardGame createFakeGame() {
        Faker faker = new Faker();
        String gameTitle = faker.esports().game();
        int minPlayers = faker.number().numberBetween(1, 4);
        int maxPlayers = faker.number().numberBetween(minPlayers, 10);
        int minPlayingTime = faker.number().numberBetween(10, 30);
        int maxPlayingTime = faker.number().numberBetween(minPlayingTime, 240);
        return new BoardGame(gameTitle, minPlayers, maxPlayers, minPlayingTime, maxPlayingTime);
    }

    public static GameStats createFakeStats() {
        double buyPrice = faker.number().randomDouble(2, 5, 200);
        int numTimesPlayed = faker.number().numberBetween(1, 100);
        int numWins = faker.number().numberBetween(0, numTimesPlayed);
        boolean owned = faker.bool().bool();

        GameStats fakeStats = new GameStats();
        fakeStats.setBuyPrice(buyPrice);
        fakeStats.setNumTimesPlayed(numTimesPlayed);
        fakeStats.setNumWins(numWins);
        fakeStats.setOwned(owned);

        return fakeStats;
    }

    public static Set<User> populateUsers(int numUsers) {
        // TODO: Create a random number of BoardGames and return them
        Set<User> userSet = new HashSet<>();
        for (int i = 0; i < numUsers; i++) {
            userSet.add(createFakeUser());
        }
        return userSet;
    }

    public static User createFakeUser() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String password = faker.internet().password();
        String email = faker.internet().emailAddress();
        String username = faker.name().username();

        User fakeUser = new User();
        fakeUser.setFirstName(firstName);
        fakeUser.setLastName(lastName);
        fakeUser.setPassword(password);
        fakeUser.setEmail(email);
        fakeUser.setUsername(username);

        return fakeUser;
    }
}
