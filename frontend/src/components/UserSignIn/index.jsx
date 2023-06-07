/**
 * UserSignIn:
 * Render a Login form
 */
import React, { useState, useContext } from "react";
import { UserContext } from "../../services/UserContext";
import { Button, Form } from "semantic-ui-react";

const UserSignIn = () => {
    // Distructure the content of the UserContext
    const { user, loginUser } = useContext(UserContext);

    // Define local variables
    const [ username, setUsername ] = useState('');
    const [ password, setPassword] = useState('');

    // Define the login logic
    const handleLogin = () => {
        loginUser({ username, password });
        if (user === null) {
            return (
                <p>Invalid username or password</p>
            );
        } else {
            return (
                <p>Welcome, { user.username }</p>
            );
        }
    };

    // Render
    return(
        <React.Fragment>
        <Form>
            <Form.Field>
                <label>Username</label>
                <input
                    type="text"
                    placeholder='Username'
                    value={username}
                    onChange={e => setUsername(e.target.value)}
                />
            </Form.Field>
            <Form.Field>
                <label>Password</label>
                <input
                    type="password"
                    placeholder='Password'
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                />
            </Form.Field>
            <Button type="submit" onClick={handleLogin}>Submit</Button>
        </Form>
        {user ? <p>Welcome, {user.username}</p> : <p>Not logged in</p>}
        </React.Fragment>
    );
};

export default UserSignIn;