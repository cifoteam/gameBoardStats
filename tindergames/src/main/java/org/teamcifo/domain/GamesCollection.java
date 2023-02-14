package org.teamcifo.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.teamcifo.utils.Helpers;

import java.util.HashMap;
import java.util.Map;

@Data
@Getter
@Setter
public class GamesCollection {
    private String collectionId;
    private Map<String, BoardGame> gamesCollection;

    public GamesCollection() {
        this.collectionId = Helpers.generateUUID();
        this.gamesCollection = new HashMap<>();
    }

    @Override
    public int hashCode() {
        // As collectionID should already be unique for each collection, we can use it to compute the hashcode
        return this.collectionId.hashCode();
    }
}
