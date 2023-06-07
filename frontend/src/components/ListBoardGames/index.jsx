import React, { useEffect, useState } from "react";
import { Grid } from "semantic-ui-react";
import BoardGameCard from "../../components/BoardGameCard";
import BoardGamesService from "../../services/BoardGamesService";

const ListBoardGames = () => {

    const [ boardGames, setBoardGames ] = useState([]);
    const [ isLoading, setIsLoading ] = useState(true);

    // useEffect hook ensures that the ListBoardGames component stays connected to the API
    useEffect(() => {
        const fetchGames = async() => {
            const response = await BoardGamesService.getAllBoardGames();
            setBoardGames(response);
            setIsLoading(false);
        };
        // useEffect invokes the fetchGames function
        fetchGames();
        // TODO: return a clean code function with cleanup code that disconnects from the API
    }, []);

    return (
        <React.Fragment>
            {/* Conditional rendering depending on the isLoading value */}
            { isLoading ? (
                <p>Loading...</p>
            ) : (
                <Grid
                    columns={4}
                    doubling
                    stackable
                    style={{ padding: "20px", backgroundcolor: "#F0F0F0"}}
                >
                    {/** Iterate the array of BoardGames with the map function */}
                    { boardGames.map((boardGame, index) => (
                        <Grid.Column key={index}>
                            <BoardGameCard
                                boardGame={boardGame}
                            />
                        </Grid.Column>
                    ))}
                </Grid>
            )
            }
        </React.Fragment>
    );
};

export default ListBoardGames;