import axios from "axios";

// TODO: parametrize the API_BASE_URL value
const API_BASE_URL = "http://localhost:9091/api/boardgames";

// Define the Service layer of our TinderGames application here
// Logical object: each key can be a function or a prop
const BoardGamesService = {
    // Define the logic to retrieve all Users
    getAllBoardGames: async () => {
        try {
            // Retrieve data from the API using the GET method
            console.log("[BoardGamesService - getAllBoardGames] Retrieving all boardgames...");
            const response = await axios.get(`${API_BASE_URL}`);
            console.log(response.data);
            return response.data;
        } catch (error) {
            // Inform that data couldn't be retrieved
            console.error("[BoardGamesService - getAllBoardGames] Error retrieving boardgames:", error);
            throw error;
        }
    },
    // Define the logic to retrieve a single boardgame by ID
    // --> Input params: the boardgame's ID
    getBoardGameByID: async(boardGameID) => {
        try {
            // Retrieve data from the API using the GET method
            console.log("[BoardGameService - getBoardGameByID] Retrieving user...");
            const response = await axios.get(`${API_BASE_URL}/id/${boardGameID}`);
            console.log(response.data);
            return response.data;
        } catch (error) {
            // Inform that data couldn't be retrieved
            console.error("[BoardGameService - getBoardGameByID] Error retrieving boardgame:", error);
        }

    },
    // Define the logic to retrieve a single boardgame by title
    // --> Input params: the boardgame's title
    getBoardGameByUsername: async(title) => {
        try {
            // Retrieve data from the API using the GET method
            console.log("[BoardGameService - getBoardGameByTitle] Retrieving boardgame...");
            const response = await axios.get(`${API_BASE_URL}/title/${title}`);
            console.log(response.data);
            return response.data;
        } catch (error) {
            // Inform that data couldn't be retrieved
            console.error("[BoardGameService - getBoardGameByID] Error retrieving boardgame:", error);
        }

    },
    // Define the logic to create a new BoardGame
    // --> Input params: already composed 'newBoardGame' item
    createBoardGame: async (newBoardGame) => {
        try {
            // Send the 'newUser' to the API using the POST method
            console.log("[BoardGameService - createBoardGame] Sending newBoardGame to the API:", newBoardGame);
            const response = await axios.post(`${API_BASE_URL}/createGame`, newBoardGame);
            // Return the response of the operation
            console.log("[BoardGameService - createBoardGame] Received response:", response.data);
            return response.data;
        } catch (error) {
            // Inform that data couldn't be created
            console.error("[BoardGameService - createBoardGame] Error creating new boardgame:", error);
            throw error;
        }
    },
    // Define the logic to update an 'updatedBoardGame'
    // --> Input param: updated and already composed 'updatedBoardGame' object
    updateBoardGame: async (updatedBoardGame) => {
        try {
            // Send the updated 'updatedUser' to the API using the PUT method
            const response = await axios.put(`${API_BASE_URL}/updateGame`, updatedBoardGame);
            // Return the response of the operation
            return response.data;

        } catch (error) {
            // Inform that data couldn't be updated
            console.error("[BoardGameService - updateBoardGame] Error updating boardgame: ", error);
            throw error;
        }
    },
    // Define the logic to delete a user
    // --> Input param: we only need the 'user' ID
    deleteBoardGame: async (boardGameID) => {
        try {
            // Send the 'user' ID to be deleted to the API using the DELETE method
            const response = await axios.delete(`${API_BASE_URL}/deleteGame`, boardGameID);
            // Return the response of the operation
            return response.data;
        } catch (error) {
            // Inform that data couldn't be deleted
            console.error("[BoardGameService - deleteBoardGame] Error deleting boardgame:", error);
        }
    }
};

// Don't forget to define the default function to export!
export default BoardGameService;