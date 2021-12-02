package com.battleship.backend;

import com.battleship.backend.models.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class BoardRepositoryTest {
    BoardRepository testRepo;
    @BeforeEach
    void initNewBoardRepo(){
        testRepo = new BoardRepository();
    }

    @Test
    void testGetBoards() {
        assertEquals("com.battleship.backend.models.Board", testRepo.getBoards()[0].getClass().getName());
        assertEquals("Player Board", testRepo.getBoards()[0].getName());
        assertEquals("Computer Board", testRepo.getBoards()[1].getName());
    }

    @Test
    void getPlayerBoard() {
        assertEquals("Player Board", testRepo.getPlayerBoard().getName());
    }

    @Test
    void getComputerBoard() {
        assertEquals("Computer Board", testRepo.getComputerBoard().getName());
    }
}