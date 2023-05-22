package org.teamcifo.tindergames.gamesCollectionEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameStatsService {
    @Autowired
    GameStatsRepository gameStatsRepository;

    // CRUD operations
    public boolean addGameStatsToDB(GameStats gameStats) {
        // If the GameStats ID already exists, don't do anything
        if (gameStatsRepository.existsById(gameStats.getGameStatsId())) {
            return false;
        }
        gameStatsRepository.save(gameStats);
        return true;
    }

    public Iterable<GameStats> getAllGameStats() {
        return gameStatsRepository.findAll();
    }

    public GameStats getGameStatsByID(String gameStatsID) {
        if (gameStatsRepository.existsById(gameStatsID)) {
            return gameStatsRepository.findById(gameStatsID).get();
        }
        return null;
    }

    public boolean updateGameStatsInDB(GameStats gameStats) {
        // TODO: Don't simply replace the GameStats entity. Retrieve the same entity from the DB and update its modified fields instead
        if (gameStatsRepository.existsById(gameStats.getGameStatsId())) {
            gameStatsRepository.save(gameStats);
            return true;
        }
        return false;
    }

    public boolean deleteGameStatsFromDB(GameStats gameStats) {
        if (gameStatsRepository.existsById(gameStats.getGameStatsId())) {
            gameStatsRepository.delete(gameStats);
            return true;
        }
        return false;
    }

    public boolean deleteGameStatsByIDFromDB(String gameStatsID) {
        if (gameStatsRepository.existsById(gameStatsID)) {
            gameStatsRepository.deleteById(gameStatsID);
            return true;
        }
        return false;
    }
}
