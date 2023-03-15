package org.teamcifo.tindergames.boardGameEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/boardgames")
public class BoardGameController {

    @Autowired
    BoardGameService boardGameService;
}
