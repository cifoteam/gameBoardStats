package org.teamcifo.tindergames.gamesCollectionEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GamesCollectionService {

    @Autowired
    GamesCollectionRepository gamesCollectionRepository;
    @Autowired
    GameStatsService gameStatsService;

    // CRUD
    // - Add a new GamesCollection
    public boolean addGamesCollectionToDB(GamesCollection gamesCollection) {
        // If the GamesCollection ID already exists, don't do anything
        if (gamesCollectionRepository.existsById(gamesCollection.getCollectionId())) {
            return false;
        }
        gamesCollectionRepository.save(gamesCollection);
        return true;
    }

    public Iterable<GamesCollection> getAllGamesCollection() {
        return gamesCollectionRepository.findAll();
    }

    public GamesCollection getGamesCollectionByID(String gamesCollectionID) {
        if (gamesCollectionRepository.existsById(gamesCollectionID)) {
            return gamesCollectionRepository.findById(gamesCollectionID).get();
        }
        return null;
    }

    public boolean updateGamesCollectionInDB(GamesCollection gamesCollection) {
        // TODO: Don't simply replace the GamesCollection entity. Retrieve the same entity from the DB and update its modified fields instead
        if (gamesCollectionRepository.existsById(gamesCollection.getCollectionId())) {
            gamesCollectionRepository.save(gamesCollection);
            return true;
        }
        return false;
    }

    public boolean deleteGamesCollectionFromDB(GamesCollection gamesCollection) {
        if (gamesCollectionRepository.existsById(gamesCollection.getCollectionId())) {
            gamesCollectionRepository.delete(gamesCollection);
            return true;
        }
        return false;
    }

    public boolean deleteGamesCollectionByIDFromDB(String gamesCollectionID) {
        if (gamesCollectionRepository.existsById(gamesCollectionID)) {
            gamesCollectionRepository.deleteById(gamesCollectionID);
            return true;
        }
        return false;
    }
}
