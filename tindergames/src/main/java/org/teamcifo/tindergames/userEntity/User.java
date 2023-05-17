package org.teamcifo.tindergames.userEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.teamcifo.tindergames.boardGameEntity.BoardGame;
import org.teamcifo.tindergames.gamePlayEntity.Gameplay;
import org.teamcifo.tindergames.gamesCollectionEntity.GameStats;
import org.teamcifo.tindergames.utils.Helpers;

import java.util.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "game_statuses_mapping",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "game_stats_id", referencedColumnName = "gameStatsId")})
    @MapKeyJoinColumn(name = "game_id")
    @ToString.Exclude
    private Map<BoardGame, GameStats> userGamesCollection; // Keys are BoardGames

    @ManyToMany
    @JoinTable(name = "USER_GAMEPLAY",
            joinColumns = @JoinColumn(name = "USER_FK"),
            inverseJoinColumns = @JoinColumn(name = "GAMEPLAY_FK")
    )
    @ToString.Exclude
    private Set<Gameplay> gameplays;

    /*@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity=User.class)

     */
    @ManyToMany
    @JoinTable(name = "USER_FRIENDS",
            joinColumns = @JoinColumn(name = "USER_FK"),
            inverseJoinColumns = @JoinColumn(name = "FRIEND_FK")
    )
    @ToString.Exclude
    private Set<User> friends;

    public User() {
        this.userId = Helpers.generateUUID();
        this.gameplays = new HashSet<>();
        this.friends = new HashSet<>();
        this.userGamesCollection = new HashMap<>();
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
        this.userGamesCollection.putIfAbsent(boardGame, new GameStats());
    }

    public void deleteGameFromCollection(BoardGame boardGame) {
        if (this.hasGame(boardGame)) {
            this.userGamesCollection.remove(boardGame);
        }
    }

    public boolean hasGame(BoardGame boardGame) {
        return this.userGamesCollection.containsKey(boardGame);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getUserId() != null && Objects.equals(getUserId(), user.getUserId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
