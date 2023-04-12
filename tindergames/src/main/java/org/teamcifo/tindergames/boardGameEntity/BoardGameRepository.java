package org.teamcifo.tindergames.boardGameEntity;

import org.springframework.data.repository.CrudRepository;
import org.teamcifo.tindergames.boardGameEntity.BoardGame;

import java.util.Optional;

public interface BoardGameRepository extends CrudRepository<BoardGame, String> {
    Optional<BoardGame> findByGameTitle(String gameTitle);
    Optional<BoardGame> findByGameID(String gameID);
}
