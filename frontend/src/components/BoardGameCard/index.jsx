import { Card, Image } from "semantic-ui-react";

// Props are spreaded prior to pass them to the UserCard component
const BoardGameCard = (props) => {
    const boardGame = props.boardGame;

    return (
        <>
            <Card style={{ margin: "10px" }}>
                <Image src={boardGame.image} wrapped ui={false} />

                {/* Define the Card contents */}
                <Card.Content>
                    <Card.Header>
                        {boardGame.gameTitle}
                    </Card.Header>
                    <Card.Meta>
                        <p>{boardGame.gameID}</p>
                    </Card.Meta>
                    <Card.Description>
                        <p><b>Min players: </b>{boardGame.minPlayers} players</p>
                        <p><b>Max players: </b>{boardGame.maxPlayers} players</p>
                        <p><b>Min Play Time: </b>{boardGame.minPlayTime} min.</p>
                        <p><b>Max Play Time: </b>{boardGame.maxPlayTime} min.</p>
                    </Card.Description>
                </Card.Content>
            </Card>
        </>
    );
};

export default BoardGameCard;