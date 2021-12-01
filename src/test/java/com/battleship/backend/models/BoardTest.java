package com.battleship.backend.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    @Test
    public void testAddShipsPlacesFirstShipSectionOnBoard(){
        Board testBoard = new Board("test");
        Ship testShip = new Ship("testShip", 3);

        // ship, x-coord, y-coord
        testBoard.addShip(testShip, 0, 0 );

        assertEquals(testBoard.getGrid()[0][0], testShip.getShipSections()[0]);

    }

    @Test
    public void testAddShipsPlacesSecondShipSectionOnBoardHorizontally(){
        Board testBoard = new Board("test");
        Ship testShip = new Ship("testShip", 3);

        // ship, x-coord, y-coord
        testBoard.addShip(testShip, 0, 0 );

        assertEquals(testBoard.getSection(0, 1), testShip.getShipSection(1));

    }

    @Test
    public void testAddShipsPlacesSecondShipSectionOnBoardVertically(){
        Board testBoard = new Board("test");
        Ship testShip = new Ship("testShip", 3);
        testShip.rotate();

        // ship, x-coord, y-coord
        testBoard.addShip(testShip, 0, 0 );

        assertEquals(testBoard.getSection(1, 0), testShip.getShipSection(1));

    }
}