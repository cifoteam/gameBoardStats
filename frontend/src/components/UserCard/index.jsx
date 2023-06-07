import { Card, Image } from "semantic-ui-react";

// Props are spreaded prior to pass them to the UserCard component
const UserCard = ({image, firstName, lastName, username, mail, friends, userGamesCollection }) => {
    return (
        <>
            <Card style={{ margin: "10px" }}>
                <Image src={image} wrapped ui={false} />

                {/* Define the Card contents */}
                <Card.Content>
                    <Card.Header>
                        {username}
                    </Card.Header>
                    <Card.Meta>
                        <p>{lastName}, {firstName}</p>
                    </Card.Meta>
                    {/* TODO: API doesn't return the role of the person, not included in the DB entry */}
                    <Card.Description>
                        { mail ? <p><b>Mail:</b> { mail }</p> :''}
                        { friends.map((friend) => (
                            <p>Friend: {friend.username}</p>
                        ))}
                    </Card.Description>
                </Card.Content>
            </Card>
        </>
    );
};

export default UserCard;