package org.teamcifo.logic;


import org.junit.jupiter.api.*;
import java.util.Scanner;
import com.github.javafaker.Faker;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class LogInTest {
        private static Faker faker;
        private static Scanner defaultScanner;

        @BeforeAll
        public static void setUp() {
            faker = new Faker();
            defaultScanner = LogIn.getScanner();
            // TODO: Create or access fake databases for test
        }

        @AfterEach
        public void tearDown() {
            // Remember to restore the default scanner in the static class Login after each test
            LogIn.setScanner(defaultScanner);
        }

        @AfterAll
        public static void cleanUp() {
            // Clear Manager's hashmaps before the next set of tests
            UserManager.clear();
        }

        @Test
        @DisplayName("Test LogIn.login() method")
        public void testLogin() {
            // Create a mock input of a valid username and password
            String username = faker.name().username();
            String password = faker.internet().password();
            String input = username + "\n" + password + "\n";
            Scanner testScanner = new Scanner(input);
            LogIn.setScanner(testScanner);

            // Call the login method and check if it returns successfully
            Assertions.assertDoesNotThrow(LogIn::login);
        }

        @Test
        @DisplayName("Test LogIn.register() method")
        public void testRegister() {
            // Create a mock input of a new username and user information
            //fails needs to see why fails
            String username = faker.name().username();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String password = faker.internet().password();
            String input = username + "\n" + firstName + "\n" + lastName + "\n" + password + "\n";
            Scanner testScanner = new Scanner(input);
            LogIn.setScanner(testScanner);

            // Call the register method and check if it returns successfully
            Assertions.assertDoesNotThrow(LogIn::register);

            // Check that the user has been properly inserted into the database
            assertNotNull(UserManager.getUserByUsername(username));
        }
    }

