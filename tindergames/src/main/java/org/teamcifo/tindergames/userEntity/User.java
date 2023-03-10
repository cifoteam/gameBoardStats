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
    private String userId;
    //private GamesCollection userGameCollection;
    //private List<GamePlay> gamePlays;

    public User() {
        // First initialize the user's games collection
        //this.userGameCollection = new GamesCollection();
        // Then use the collection ID as the user ID
        //this.userId = this.userGameCollection.getCollectionId();
        //this.gamePlays = new ArrayList<>();
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
