package org.teamcifo.tindergames.userEntity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.teamcifo.tindergames.gamePlayEntity.Gameplay;
import org.teamcifo.tindergames.gamesCollectionEntity.GamesCollection;
import org.teamcifo.tindergames.utils.Helpers;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
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

    @OneToOne
    @JoinColumn(name="games_collection_id", referencedColumnName = "collectionId")
    @JsonManagedReference
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
        this.userGamesCollection = new GamesCollection(this);
        this.gameplays = new HashSet<>();
        this.friends = new HashSet<>();
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
}
