package org.teamcifo.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.teamcifo.utils.Helpers;

import java.util.ArrayList;
import java.util.List;

@Data

@AllArgsConstructor
@Getter
@Setter
public class User {

    private String name, lastName, password, email, username;
    private String userId;
    private GamesCollection userGameCollection;
    private List<GamePlay> gamePlays;

    public User() {
        this.userId = Helpers.generateUUID();
        this.userGameCollection = new GamesCollection();
        this.gamePlays = new ArrayList<>();
    }

    public User(String name, String lastName, String password, String email, String username) {
        this();
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.username = username;
    }



}
