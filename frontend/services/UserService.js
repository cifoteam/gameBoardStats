import axios from "axios";

// TODO: parametrize the API_BASE_URL value
const API_BASE_URL = "http://localhost:9091/api/users";

// Define the Service layer of our TinderGames application here
// Logical object: each key can be a function or a prop

const UserService = {
    // Define the logic to retrieve all Users
    getAllUsers: async () => {
        try {
            // Retrieve data from the API using the GET method
            console.log("[UserService - getAllUsers] Retrieving all users...");
            const response = await axios.get(`${API_BASE_URL}`);
            console.log(response.data);
            return response.data;
        } catch (error) {
            // Inform that data couldn't be retrieved
            console.error("[UserService - getAllUsers] Error retrieving users:", error);
            throw error;
        }
    },
    // Define the logic to create a new User
    // --> Input params: already composed 'newUser' item
    createUser: async (newUser) => {
        try {
            // Send the 'newUser' to the API using the POST method
            console.log("[UserService - createUser] Sending newUser to the API:", newUser);
            const response = await axios.post(`${API_BASE_URL}/createUser`, newUser);
            // Return the response of the operation
            console.log("[UserService - createUser] Received response:", response.data);
            return response.data;
        } catch (error) {
            // Inform that data couldn't be created
            console.error("[UserService - createUser] Error creating new user:", error);
            throw error;
        }
    },
    // Define the logic to update an 'updatedUser'
    // --> Input param: updated and already composed 'updatedUser' object
    updateUser: async (updatedUser) => {
        try {
            // Send the updated 'updatedUser' to the API using the PUT method
            const response = await axios.put(`${API_BASE_URL}/updateUser`, updatedUser);
            // Return the response of the operation
            return response.data;

        } catch (error) {
            // Inform that data couldn't be updated
            console.error("[UserService - updateUser] Error updating user: ", error);
            throw error;
        }
    },
    // Define the logic to delete a user
    // --> Input param: we only need the 'user' ID
    deleteUser: async (userId) => {
        try {
            // Send the 'user' ID to be deleted to the API using the DELETE method
            const response = await axios.delete(`${API_BASE_URL}/deleteUser`, userId);
            // Return the response of the operation
            return response.data;
        } catch (error) {
            // Inform that data couldn't be deleted
            console.error("[UserService - deleteUser] Error deleting user:", error);
        }
    }
};

// Don't forget to define the default function to export!
export default UserService;