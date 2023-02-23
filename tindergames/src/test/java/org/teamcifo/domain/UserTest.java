package org.teamcifo.domain;

import com.github.javafaker.Faker;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class UserTest {
    private static User user, userNoArgs;
    private static Faker faker;

    //Faker variables
    private static String fakeId, fakeName, fakeLastName, fakeEmail, fakePassword;

    @BeforeEach
    void setUp(){
        //Init objects
        user = new User();
        faker = new Faker();
        userNoArgs = new User();//to test with a user without parameters

        // Generate fake data for the user object
        fakeId = faker.number().digits(10);
        fakeName = faker.name().firstName();
        fakeLastName = faker.name().lastName();
        fakeEmail = faker.internet().emailAddress();
        fakePassword = faker.internet().password();

        // Set the user object's properties with fake data
        user.setUserId(fakeId);
        user.setName(fakeName);
        user.setLastName(fakeLastName);
        user.setEmail(fakeEmail);
        user.setPassword(fakePassword);
        user.setUserGameCollection(new GamesCollection());
        user.setGamePlays(new ArrayList<GamePlay>());

    }

    @Test
    public void testAllArgsConstructor() {
        //Test that the all-args constructor creates a non-null user object
        assertNotNull(user);

    }

    @Test
    public void testNoArgsConstructor() {
        // Test that the no-args constructor creates a non-null user object
        assertNotNull(userNoArgs);

    }

    @Test
    public void testGettersAndSetters() {
        // Assert that the user object's properties have been set with the correct fake data
        assertEquals(user.getName(), fakeName);
        assertEquals(user.getLastName(), fakeLastName);
        assertEquals(user.getEmail(), fakeEmail);
        assertEquals(user.getPassword(), fakePassword);
        assertEquals(user.getUserId(), fakeId);
        assertEquals(user.getUserGameCollection(), user.getUserGameCollection());
        assertEquals(user.getGamePlays(), user.getGamePlays());

    }

    @Test
    public void testToString() {
        // Test that the toString() method does not return null
        assertNotNull(user.toString());

    }

}