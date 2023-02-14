package org.teamcifo.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.teamcifo.utils.Helpers;

import java.util.HashMap;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    private String name, lastName, password, email;
    private String userId;
    private GameCollection userGameCollection;
    private List<GamePlay> gamePlays;
   
    
}
