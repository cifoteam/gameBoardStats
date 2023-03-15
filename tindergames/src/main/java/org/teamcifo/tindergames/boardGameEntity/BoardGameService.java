package org.teamcifo.tindergames.boardGameEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardGameService {

    @Autowired
    BoardGameRepository boardGameRepository;

    public boolean addBoardGameToDB(BoardGame boardGame){
        if (boardGameRepository.findByGameTitle(boardGame.getGameTitle()).isPresent()){
            return false;
        }
        boardGameRepository.save(boardGame);
        return true;
    }

    public Iterable
}
