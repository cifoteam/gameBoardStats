package org.teamcifo.tindergames.gamePlayEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameplayService {
    @Autowired
    GameplayRepository gameplayRepository;

    // CRUD operations
    public boolean addGamePlayToDB(Gameplay gameplay) {
        // If the GamePlay ID already exists, don't do anything
        if (gameplayRepository.existsById(gameplay.getGameplayId())) {
            return false;
        }
        gameplayRepository.save(gameplay);
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
