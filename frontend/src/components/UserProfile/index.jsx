/**
 * UserProfile
 * It shows either the User profile or the LogIn information
 */
import React, { useContext, useEffect, useState } from "react";
import { UserContext, UserProvider } from "../../services/UserContext";
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
    const [ isLoggedIn, setLoggedIn ] = useState(false);

    // Define a useEffect to re-render the component if the 'user' state changes
    useEffect(() => {
        if (user === null) {
            setLoggedIn(false);
        } else {
            setLoggedIn(true);
        }
    }, [user]);

    return (
        <React.Fragment>
            { isLoggedIn ?
                <React.Fragment>
                    <UserCard user={user} />
                    <UserLogOut />
                </React.Fragment> :
                <UserProvider>
                    <UserSignIn />
                    <UserLogOut />
                </UserProvider>
             }
        </React.Fragment>
    );
};

export default UserProfile;