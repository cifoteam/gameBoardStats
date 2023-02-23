package org.teamcifo.logic;

import static org.junit.jupiter.api.Assertions.*;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.teamcifo.domain.User;
import java.util.HashMap;
import java.util.Map;

public class UserManagerTest {
    //private UserManager userManager;
    private Faker faker;
    @Test
    public void testAddUserAndGetUser() {
        // Create an instance of the Faker library to generate fake data

        //UserManager userManager = new UserManager();
        User user = new User();
        String fakeId = faker.number().digits(10);
        user.setUserId(fakeId);
        user.setName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());

        // Add the user to the user manager
        UserManager.addUser(user);
        //userManager.addUser(user);

        // Get the user from the user manager and check that it is the same as the user that was added
        //User retrievedUser = userManager.getUser(fakeId);
        User retrievedUser = UserManager.getUser(fakeId);
        assertEquals(user, retrievedUser);
    }

    @Test
    public void testAuthenticate() {
        // Create an instance of the Faker library to generate fake data

        UserManager userManager = new UserManager();
        User user = new User();
        String fakeId = faker.number().digits(10);
        user.setUserId(fakeId);
        user.setName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(faker.internet().emailAddress());
        String fakePassword = faker.internet().password();
        user.setPassword(fakePassword);
        System.out.println(user);

        // Add the user to the user manager
        //userManager.addUser(user);
        UserManager.addUser(user);
        // Authenticate the user and check that the result is true
        boolean result = userManager.authenticate(fakeId, fakePassword);
        assertTrue(result);

        //
        Assertions.assertTrue(userManager.authenticate(user.getUserId(), user.getPassword()));
        Assertions.assertFalse(userManager.authenticate(user.getUserId(), "wrong password"));
        Assertions.assertFalse(userManager.authenticate("non-existent user ID", "password"));
    }

    @Test
    public void testGetAllUsers() {
        // Create an instance of the Faker library to generate fake data

        //UserManager userManager = new UserManager();
        Map<String,User> users = new HashMap<>();


        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUserId(faker.number().digits(10));
            user.setName(faker.name().firstName());
            user.setLastName(faker.name().lastName());
            user.setEmail(faker.internet().emailAddress());
            user.setPassword(faker.internet().password());
            users.put(user.getUserId(),user);
            //userManager.addUser(user);
            UserManager.addUser(user);
        }

        // Get the list of all users from the user manager and check that it is the same as the list of users that was created
        Map<String,User> retrievedUsers = UserManager.getAllUsers();
        //assertEquals(users,retrievedUsers);
        assertEquals(users,UserManager.getAllUsers());

    }

    //More tests
    @BeforeEach
    void setUp() {
        UserManager.clear();
        faker = new Faker();
    }

    @Test
    void testAddUser() {
        User user = new User();
        user.setUserId(faker.number().digits(10));
        user.setName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());

        //userManager.addUser(user);

        UserManager.addUser(user);
        System.out.println(UserManager.getAllUsers());
        //Map<String, User> users = userManager.getAllUsers();

        // ojo Map<String, User> users = UserManager.getAllUsers();

        //Assertions.assertEquals(1, users.size());
        //Assertions.assertEquals(user, users.get(user.getUserId()));
        Assertions.assertEquals(1, UserManager.getAllUsers().size());//should be 1 but when I pass the global test it was 2
        Assertions.assertEquals(user, UserManager.getUser(user.getUserId()));
    }

    @Test
    void testGetUser() {
        User user = new User();
        user.setUserId(faker.number().digits(10));
        user.setName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());

        //userManager.addUser(user);
        UserManager.addUser(user);

        //Assertions.assertEquals(user, userManager.getUser(user.getUserId()));
        //Assertions.assertNull(userManager.getUser("non-existent user ID"));
        Assertions.assertEquals(user, UserManager.getUser(user.getUserId()));
        Assertions.assertNull(UserManager.getUser("non-existent user ID"));
    }
    @Test
    void testUpdateUser() {
        User user = new User();
        user.setUserId(faker.number().digits(10));
        user.setName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());

        //userManager.addUser(user);
        UserManager.addUser(user);

        User updatedUser = new User();
        updatedUser.setUserId(user.getUserId());
        updatedUser.setName(faker.name().firstName());
        updatedUser.setLastName(faker.name().lastName());
        updatedUser.setEmail(faker.internet().emailAddress());
        updatedUser.setPassword(faker.internet().password());

        //userManager.updateUser(updatedUser);
        UserManager.updateUser(updatedUser);

        //Assertions.assertEquals(updatedUser, userManager.getUser(user.getUserId()));
        Assertions.assertEquals(updatedUser, UserManager.getUser(user.getUserId()));
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setUserId(faker.number().digits(10));
        user.setName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());

        //userManager.addUser(user);
        UserManager.addUser(user);

        //userManager.deleteUser(user.getUserId());
        UserManager.deleteUser(user.getUserId());

        //Assertions.assertNull(userManager.getUser(user.getUserId()));
        //Assertions.assertEquals(0, userManager.getAllUsers().size());
        Assertions.assertNull(UserManager.getUser(user.getUserId()));
        Assertions.assertEquals(0, UserManager.getAllUsers().size());
    }


}