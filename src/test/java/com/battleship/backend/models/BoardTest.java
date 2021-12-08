package com.battleship.backend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Board testBoard;
    Ship testShip;
    @BeforeEach
    void initTestBoardandShip(){
        testBoard = new Board("TestBoard");
        testShip = new Ship("TestShip", 3);
    }
    @Test
    public void testAddShipsPlacesFirstShipSectionOnBoard(){
        testBoard.addShip(testShip, 0, 0 );

        assertEquals(testBoard.getGrid()[0][0], testShip.getShipSections()[0]);
    }

    @Test
    public void testAddShipsPlacesSecondShipSectionOnBoardHorizontally(){
        testBoard.addShip(testShip, 0, 0 );

        assertEquals(testBoard.getSection(0, 1), testShip.getShipSection(1));

    }

    @Test
    public void testAddShipsPlacesSecondShipSectionOnBoardVertically(){

        testShip.rotate();
        testBoard.addShip(testShip, 0, 0 );

        assertEquals(testBoard.getSection(1, 0), testShip.getShipSection(1));
    }

    @Test
    public void testAddShipAddsTheShipToTheShipList(){
        testBoard.addShip(testShip, 0,0);
        assertTrue(testBoard.shipList.contains(testShip));
    }

    @Test
    void testGetGrid() {
        assertEquals(testBoard.grid, testBoard.getGrid());
    }

    @Test
    void testGetName() {
        assertEquals("TestBoard", testBoard.getName());
    }

    @Test
    void testHasShipsReturnsTrueIfShipsOnBoard(){
        testBoard.addShip(testShip, 0 ,0);
        assertTrue(testBoard.hasShips());
    }

    @Test
    void testHasShipsReturnsFalseIfNoShipsOnBoard(){
        assertFalse(testBoard.hasShips());
    }

    @Test
    void testGetSection() {
        assertEquals(testBoard.grid[0][0], testBoard.getSection(0,0));
    }

    @Test
    void testAddSection() {
        Sectionable testShipSection = new ShipSection("test");
        testBoard.addSection(testShipSection, 0, 0);
        assertEquals(testShipSection, testBoard.getSection(0,0));
    }

    @Test
    void testHitSectionChangesTheSectionToHit() {
        testBoard.hitSection(0,0);
        assertTrue(testBoard.getSection(0,0).getIsHit());
    }

    @Test
    void testToString() {
        assertEquals("Name: TestBoard" + ", grid: 10", testBoard.toString());
    }
}