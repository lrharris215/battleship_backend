package com.battleship.backend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ShipTest {
    Ship testShip;
    @BeforeEach
    void initTestShip(){
        testShip = new Ship("Test", 3);
    }
    @Test
    void testFillShipPlacesShipSections() {
        assertNotNull(testShip.getShipSections());
    }

    @Test
    void testRotate() {
        int width = testShip.getWidth();
        testShip.rotate();

        assertEquals(testShip.getHeight(), width);
    }

    @Test
    void checkIsSunk() {

    }

    @Test
    void testSetWidthSetsTheWidth() {
        int newWidth = 4;
        testShip.setWidth(newWidth);

        assertEquals(newWidth, testShip.getWidth());
    }

    @Test
    void testSetHeightSetsTheHeight() {
        int newHeight = 4;
        testShip.setHeight(newHeight);

        assertEquals(newHeight, testShip.getHeight());
    }
}