/**
 * UserProfile
 * It shows either the User profile or the LogIn information
 */
import React, { useContext } from "react";
import { UserContext } from "../../services/UserContext";
import UserSignIn from "../UserSignIn";
import UserCard from "../UserCard";
import { Button } from "semantic-ui-react";

const UserLogOut = () => {
    const { logoutUser } = useContext(UserContext);

    return (
        <Button onClick={logoutUser}>Log out</Button>
    );
}

const UserProfile = () => {
    // Distructure the content of the UserContext
    const { user } = useContext(UserContext);

    return (
        <React.Fragment>
            { user ?
                <React.Fragment>
                    <UserCard key={user.userId} user={user} />
                    <UserLogOut />
                </React.Fragment> :
                <React.Fragment>
                    <UserSignIn />
                </React.Fragment>
             }
        </React.Fragment>
    );
};

export default UserProfile;