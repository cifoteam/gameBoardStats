package org.teamcifo.tindergames.logic;

import org.teamcifo.tindergames.domain.User;
import java.util.Scanner;

public class LogIn {
    private static Scanner scanner = new Scanner(System.in);

    public static void demo() {
        // TODO: Initialize some fake users and collections
        start();
    }
    public static void start() {
        while (true) {
            System.out.println("Please enter option number:");
            System.out.println("1. Log in");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> login();
                case "2" -> register();
                case "3" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public static void login() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (UserManager.authenticate(username, password)) {
            System.out.println("Login successful.");
            showMenu(username);
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    public static void register() {
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();

        // Check if user exists based on its username
        if (UserManager.getUserByUsername(username)==null) {
            User user = new User();
            user.setUsername(username);

            System.out.print("Enter your first name: ");
            user.setName(scanner.nextLine());

            System.out.print("Enter your last name: ");
            user.setLastName(scanner.nextLine());

            System.out.print("Enter your password: ");
            user.setPassword(scanner.nextLine());

            // Creation of a new user automatically initializes an entry in the GamesCollectionManager
            UserManager.addUser(user);

            System.out.println("Registration successful. Please log in.");
        } else {
            System.out.println("Username already in use.");
        }
    }
    //private void
    public static void showMenu(String username) {
        while (true) {
            System.out.println("Welcome " + username + " to the game collection app.");
            System.out.println("1. View my games");
            System.out.println("2. Add a game");
            System.out.println("3. Remove a game");
            System.out.println("4. Log out");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> viewGames(username);
                case "2" -> addGame(username);
                case "3" -> removeGame(username);
                case "4" -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void viewGames(String username) {
        // Display the user's game collection
        UserManager.getGamesCollectionByUsername(username);

    }

    private static void addGame(String username) {
        // Add a game to the user's collection

    }

    private static void removeGame(String username) {
        // Remove a game from the user's collection

    }

    public static void setScanner(Scanner scanner) {
        LogIn.scanner = scanner;
    }

    public static Scanner getScanner() {
        return scanner;
    }
}

