package org.teamcifo.tindergames.gamePlayEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamcifo.tindergames.userEntity.User;

import java.util.Optional;
import java.util.Set;

@Service
public class GameplayService {
    @Autowired
    GameplayRepository gameplayRepository;

    // CRUD operations
    private boolean addGamePlayToDB(Gameplay gameplay) {
        // If the GamePlay ID already exists, don't do anything
        if (gameplayRepository.existsById(gameplay.getGameplayId())) {
            return false;
        }
        gameplayRepository.save(gameplay);
        return true;
    }

    public boolean addGamePlayToDB(Gameplay gameplay, Set<User> players) {
        // If the GamePlay ID already exists, don't do anything
        if (gameplayRepository.existsById(gameplay.getGameplayId())) {
            return false;
        }
        gameplay.setPlayers(players);
        addGamePlayToDB(gameplay);
        return true;
    }

    public Iterable<Gameplay> getAllGameplays() {
        return gameplayRepository.findAll();
    }

    public Gameplay getGameplayByID(String gameplayID) {
        if (gameplayRepository.existsById(gameplayID)) {
            return gameplayRepository.findById(gameplayID).get();
        }
        return null;
    }

    public boolean updateGameplayInDB(Gameplay gameplay) {
        // TODO: Don't simply replace the gameplay entity. Retrieve the same entity from the DB and update its modified fields instead
        if (gameplayRepository.existsById(gameplay.getGameplayId())) {
            gameplayRepository.save(gameplay);
            return true;
        }
        return false;
    }

    public boolean deleteGameplayFromDB(Gameplay gameplay) {
        if (gameplayRepository.existsById(gameplay.getGameplayId())) {
            gameplayRepository.delete(gameplay);
            return true;
        }
        return false;
    }

    public boolean deleteGameplayByIDFromDB(String gameplayID) {
        if (gameplayRepository.existsById(gameplayID)) {
            gameplayRepository.deleteById(gameplayID);
            return true;
        }
        return false;
    }
}
