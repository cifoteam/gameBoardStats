package org.teamcifo.tindergames.gamePlayEntity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.teamcifo.tindergames.boardGameEntity.BoardGame;
import org.teamcifo.tindergames.userEntity.User;
import org.teamcifo.tindergames.utils.Helpers;

import java.util.Set;

// Lombok annotations
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
    @ManyToOne
    @JoinColumn(name = "gameID")
    private BoardGame boardGame;
    @Column
    private Long time;
    @ManyToOne
    @JoinColumn(name = "id")
    private User winner;

    @ManyToMany
    @JoinTable(name = "USER_GAMEPLAY",
            joinColumns = @JoinColumn(name = "GAMEPLAY_FK"),
            inverseJoinColumns = @JoinColumn(name = "USER_FK")
    )
    private Set<User> players;

    public Gameplay() {
        this.gameplayId = Helpers.generateUUID();
    }
}
