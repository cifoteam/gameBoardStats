/*  UserContext - serves as:
    - A reposotiry of data related to Users in the React domain
    - A provider access to the UserService functions, which utilize Axios to interact with
    an external API for data operations
    - A state manager: the UserProvider component is responsible for managing the state of the User item using the useState hook
*/

import React, { createContext, useState } from "react";
import UserService from "./UserService";

// Initialize a User context
const UserContext = createContext();

// Define a provider for the User application
// --> Input param: children (TODO: further explanation on this)
const UserProvider = ({ children }) => {
    // Define a state for the User item
    const [ user, setUser ] = useState(null);
    const [ users, setUsers ] = useState([]);

    // Define a function to fetch all users
    // --> async function, as we don't want to block the execution of the parent thread
    const fetchUsers = async () => {
        // Retrieve the Users from the API (URL harcoded in UserService.js)
        try {
            const receivedUsers = await UserService.getAllUsers();
            // Set the 'users' state to the received ones
            setUsers(receivedUsers);
        } catch (error) {
            // Notify an error in the operation
            console.error("[UserContext - fetchUsers] Error fetchin users:", error);
        }
    };
    // Define a function to get a single user by ID
    // --> async function, as we don't want to block the execution of the parent thread
    const getUserByID = async (userID) => {
        try {
            const receivedUser = await UserService.getUserByID(userID);
            // Set the 'user' state to the received one
            setUser(receivedUser);
        } catch (error) {
            // Notify an error in the operation
            console.error("[UserContext - fetchUserByID] Error fetchin user:", error);
        }
    };
    // Define a function to get a single user by username
    // --> async function, as we don't want to block the execution of the parent thread
    const getUserByUsername = async (username) => {
        try {
            const receivedUser = await UserService.getUserByUsername(username);
            // Set the 'user' state to the received one
            setUser(receivedUser);
        } catch (error) {
            // Notify an error in the operation
            console.error("[UserContext - fetchUserByUsername] Error fetchin user:", error);
        }
    };
    // Define a function to create a new user
    // --> async function, as we don't want to block the execution of the parent thread
    const createUser = async (user) => {
        try {
            const createdUser = await UserService.createUser(user);
            // Update the Users state with the new entry
            setUsers((prevUsers) => [ ...prevUsers, createdUser ]);
        } catch (error) {
            // Notify an error in the operation
            console.error("[UserContext - createUser] Error creating user:", error);
        }
    };
    // Define a function to update an existing user
    const updateUser = async (updatedUser) => {
        try {
            const updateResponse = await UserService.updateUser(updatedUser);
            setUsers((prevUsers) => {
                const updatedUsers = [ ...prevUsers ];
                const userIndex = updatedUsers.findIndex((user) => user.id === updatedUser.id);
                updatedUsers[userIndex] = updatedUser;
                return updatedUser;
            });
        } catch (error) {
            // Notify an error in the operation
            console.error("[UserContext - updateUser] Error updating user:", error);
        }
    };
    // Define a function to delete a user
    const deleteUser = async (userID) => {
        try {
            const deleteResponse = await UserService.deleteUser(userID);
        } catch (error) {
            // Notify an error in the operation
            console.error("[UserContext - deleteUser] Error deleting user:", error);
        }
    };
    // Define a function to login user
    const loginUser = async (credentials) => {
        try {
            const loggedUser = await UserService.loginUser(credentials);
            console.log("[UserContext - loginUser] Response:", loggedUser);
            setUser(loggedUser);
        } catch (error) {
            console.error("[UserContext - loginUser] Error logging user:", error);
        }
    };

    const logoutUser = () => {
        // Just reset the user state to null
        setUser(null);
    };

    // Don't forget the return of the Provider!
    return (
        <UserContext.Provider value = {{ user, users, fetchUsers, getUserByID, getUserByUsername, createUser, updateUser, deleteUser, loginUser, logoutUser }}>
            {children}
        </UserContext.Provider>
    );
};

// Export Context and Provider
export { UserContext, UserProvider };