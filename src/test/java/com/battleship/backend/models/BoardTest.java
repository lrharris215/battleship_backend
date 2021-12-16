package com.battleship.backend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
    void testRemoveShipRemovesShipFromBoard(){
        testBoard.addShip(testShip, 0, 0);
        testBoard.removeShip(testShip);

        assertFalse(isShipOnBoard(testBoard, testShip));
    }

    @Test
    void testRemoveShipRemovesShipFromBoardShipList(){
        testBoard.addShip(testShip, 0, 0);
        testBoard.removeShip(testShip);

        assertFalse(testBoard.shipList.contains(testShip));
    }

    @Test
    void testHasShip(){
        testBoard.addShip(testShip, 0 ,0);
        assertTrue(testBoard.hasShip(testShip));
    }

    @Test
    void testIsPermutationOfShipListReturnsTrueIfAllShipNamesMatch(){
        ArrayList<Ship> testList = new ArrayList<Ship>();
        testList.add(new Ship("test1", 2));
        testList.add(new Ship("test2", 2));

        testBoard.addShip(new Ship("test1", 2), 0 ,0);
        testBoard.addShip(new Ship("test2", 2), 1 ,0);

        assertTrue(testBoard.isPermutationOfShipList(testList));
    }

    @Test
    void testIsPermutationOFShipListReturnsFalseIfNamesDoNotMatch(){
        ArrayList<Ship> testList = new ArrayList<Ship>();
        testList.add(new Ship("test1", 2));
        testList.add(new Ship("test2", 2));

        testBoard.addShip(new Ship("test1", 2), 0 ,0);
        testBoard.addShip(new Ship("test3", 2), 1 ,0);

        assertFalse(testBoard.isPermutationOfShipList(testList));
    }

   private boolean isShipOnBoard(Boardable board, Ship ship){
        for(int i = 0; i < board.getGrid().length; i++){
            for(int j = 0; j < board.getGrid()[i].length; j++){
                Sectionable section =  board.getSection(i, j);
                if(section.getShipName() != null && section.getShipName().equals(ship.getName())){
                    return true;
                }
            }
        }
        return false;
    }


    // Getter tests

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
    void testToString() {
        assertEquals("Name: TestBoard" + ", grid: 10", testBoard.toString());
    }
}