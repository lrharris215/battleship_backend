package com.battleship.backend.controllers;

import com.battleship.backend.BoardRepository;
import com.battleship.backend.models.Board;
import com.battleship.backend.models.Ship;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mockito;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BoardController.class)
class BoardControllerTest {

    Board[] boards = new Board[] {new Board("test")};
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardRepository boardRepository;

    @Test
    public void getBoardsReturnsBothBoards() throws Exception {
        Mockito.when(boardRepository.getBoards()).thenReturn(boards);

        this.mockMvc.perform(get("/boards")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("test")));
    }


    // TODO: fill in test for patch request
    @Test
    public void placeShipsPatchesShipOntoPlayerBoard() throws Exception {
        Ship testShip = new Ship("test", 2);
    }
}
