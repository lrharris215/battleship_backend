package com.battleship.backend.controllers;
import com.battleship.backend.models.Board;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardController {

    @GetMapping("/boards")
    public @ResponseBody
    Board[] getBoards() {

        return null;
    }
}
