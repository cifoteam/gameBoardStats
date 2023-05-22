package org.teamcifo.tindergames.domain;

import com.github.javafaker.Faker;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.teamcifo.tindergames.userEntity.User;
import org.teamcifo.utils.FakeDataGenerator;

import java.util.HashSet;
import java.util.Set;

public class UserTest {
    private static User user, userNoArgs;
    private static Faker faker;

    //Faker variables
    private static String fakeId, fakeName, fakeLastName, fakeEmail, fakePassword;
    private static Set<User> fakeFriends;

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
        fakeFriends = FakeDataGenerator.populateUsers(faker.random().nextInt(0, 10));

        // Set the user object's properties with fake data
        user.setFirstName(fakeName);
        user.setLastName(fakeLastName);
        user.setEmail(fakeEmail);
        user.setPassword(fakePassword);
        user.setGameplays(new HashSet<>());
        user.setFriends(fakeFriends);

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
        assertNotEquals(user.getUserId(), null);
        assertEquals(user.getFirstName(), fakeName);
        assertEquals(user.getLastName(), fakeLastName);
        assertEquals(user.getEmail(), fakeEmail);
        assertEquals(user.getPassword(), fakePassword);
        assertEquals(user.getFriends(), fakeFriends);
        //assertEquals(user.getUserGameCollection(), user.getUserGameCollection());
        assertEquals(user.getGameplays(), new HashSet<>());
    }

    @Test
    public void testToString() {
        // Test that the toString() method does not return null
        assertNotNull(user.toString());
    }

}