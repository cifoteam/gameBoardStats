package org.teamcifo.tindergames.userEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.teamcifo.tindergames.gamePlayEntity.Gameplay;
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
    // TODO: Create GamesCollection and GamePlay Entities and tables to re-enable these attributes
    //private GamesCollection userGameCollection;
    @ManyToMany
    @JoinTable(name = "USER_GAMEPLAY",
            joinColumns = @JoinColumn(name = "USER_FK"),
            inverseJoinColumns = @JoinColumn(name = "GAMEPLAY_FK")
    )
    private Set<Gameplay> gameplays;

    public User() {
        // First initialize the user's games collection
        //this.userGameCollection = new GamesCollection();
        // Then use the collection ID as the user ID
        //this.userId = this.userGameCollection.getCollectionId();
        this.gameplays = new HashSet<>();
        // TODO: Create GamesCollection and GamePlay Entities and tables to re-enable these attributes
        this.userId = Helpers.generateUUID();
    }

    public User(String firstName, String lastName, String password, String email, String username) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.username = username;
    }
}
