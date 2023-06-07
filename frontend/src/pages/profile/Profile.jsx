import React from "react";
import UserProfile from "../../components/UserProfile";
import { UserProvider } from "../../services/UserContext";

function Profile() {
    return (
        <UserProvider>
            <UserProfile />
        </UserProvider>
    );
};

export default Profile;