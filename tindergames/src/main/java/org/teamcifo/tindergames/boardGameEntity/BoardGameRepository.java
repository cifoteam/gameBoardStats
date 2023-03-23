package org.teamcifo.tindergames.boardGameEntity;

import org.springframework.data.repository.CrudRepository;


import java.util.Optional;

public interface BoardGameRepository extends CrudRepository<BoardGame, String> {
    Optional<BoardGame> findByGameTitle(String gameTitle);
}
