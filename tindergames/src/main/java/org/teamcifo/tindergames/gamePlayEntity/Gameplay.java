package org.teamcifo.tindergames.gamePlayEntity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.teamcifo.tindergames.userEntity.User;

// Lombok annotations
@Data
@Getter
@Setter
// JPA annotations
@Entity(name="GamePlay")
@Table(name="GAMEPLAY_TABLE")
public class Gameplay {
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(updatable = false, nullable = false)
    private String gameplayId;
    //@ManyToOne
    //@JoinColumn(name = "gameID")
    //private BoardGame boardGame;
    @Column
    private long time;
    @ManyToOne
    @JoinColumn(name = "id")
    @Column(name = "WINNER_ID")
    private User winner;
    //private List<User> players;
}
