package org.teamcifo.tindergames.userEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.teamcifo.tindergames.utils.Helpers;

@Data

@AllArgsConstructor
@Getter
@Setter
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
    //private List<Gameplay> gameplays;

    public User() {
        // First initialize the user's games collection
        //this.userGameCollection = new GamesCollection();
        // Then use the collection ID as the user ID
        //this.userId = this.userGameCollection.getCollectionId();
        //this.gameplays = new ArrayList<>();
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
