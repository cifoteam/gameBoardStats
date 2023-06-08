import React from "react";
import { Card, Image } from "semantic-ui-react";

// Props are spreaded prior to pass them to the UserCard component
const UserCard = (props) => {
    const user = props.user;
    return (
        <React.Fragment>
            <Card style={{ margin: "10px" }}>
                <Image src={user.image} wrapped ui={false} />

                {/* Define the Card contents */}
                <Card.Content>
                    <Card.Header>
                        {user.username}
                    </Card.Header>
                    <Card.Meta>
                        <p>ID: {user.userId}</p>
                        <p>{user.lastName}, {user.firstName}</p>
                    </Card.Meta>
                    {/* TODO: API doesn't return the role of the person, not included in the DB entry */}
                    <Card.Description>
                        { user.mail ? <p><b>Mail:</b> { user.mail }</p> :''}
                        { user.friends.map((friend) => (
                            <p>Friend: {friend.username}</p>
                        ))}
                        { /** Render BoardGames only if it's not empty*/}
                        <b>Owned board games:</b>
                        { Object.keys(user.userGamesCollection).length === 0
                            ? 'None'
                            : ( Object.keys(user.userGamesCollection).map((boardGame, gameStats) => (
                                <p>{boardGame}</p>
                                )) )
                        }
                    </Card.Description>
                </Card.Content>
            </Card>
        </ React.Fragment>
    );
};

export default UserCard;