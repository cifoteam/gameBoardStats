package org.teamcifo.tindergames.userEntity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.teamcifo.tindergames.boardGameEntity.BoardGame;
import org.teamcifo.tindergames.gamePlayEntity.Gameplay;
import org.teamcifo.tindergames.gamesCollectionEntity.GamesCollection;
import org.teamcifo.tindergames.utils.Helpers;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
// JPA annotations
@Entity(name="User")
@Table(name="USER_TABLE")
public class User {

    @Column
    private String firstName, lastName, password, email, username;
    @Id
    @GenericGenerator(name="system-uuid", strategy="uuid")
    @Column(updatable = false, nullable = false)
    private String userId;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    @PrimaryKeyJoinColumn
    private GamesCollection userGamesCollection;

    @ManyToMany
    @JoinTable(name = "USER_GAMEPLAY",
            joinColumns = @JoinColumn(name = "USER_FK"),
            inverseJoinColumns = @JoinColumn(name = "GAMEPLAY_FK")
    )
    private Set<Gameplay> gameplays;

    @ManyToMany
    @JoinTable(name = "USER_FRIENDS",
            joinColumns = @JoinColumn(name = "USER_FK"),
            inverseJoinColumns = @JoinColumn(name = "FRIEND_FK")
    )
    private Set<User> friends;

    public User() {
        this.userId = Helpers.generateUUID();
        this.gameplays = new HashSet<>();
        this.friends = new HashSet<>();
        this.userGamesCollection = new GamesCollection(this);
    }

    public User(String firstName, String lastName, String password, String email, String username) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.username = username;
    }

    public boolean checkPassword(String password) {
        return this.getPassword().equals(password);
    }

    public void addFriend(User friend) {
        this.friends.add(friend);
    }

    public void addGameToCollection(BoardGame boardGame) {
        this.getUserGamesCollection().addGameToCollection(boardGame);
    }
}
