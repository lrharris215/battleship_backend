package com.battleship.backend.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipTest {
    Ship testShip;
    int testWidth;
    @BeforeEach
    void initTestShip() {
        testWidth = 3;
        testShip = new Ship("Test", testWidth);
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
    void testCheckIsSunkReturnsTrueIfAllShipSectionsAreHit() {
        sinkShip(testShip);
        assertTrue(testShip.checkIsSunk());
    }

    @Test
    void testCheckIsSunkReturnsFalseIfNoSectionsAreHit() {
        assertFalse(testShip.checkIsSunk());
    }
    @Test
    void testCheckIsSunkReturnsFalseIfOnly1SectionIsHit() {
        testShip.getShipSections()[0].receiveHit();

        assertFalse(testShip.checkIsSunk());
    }

    @Test
    void testGetName() {
        assertEquals("Test", testShip.getName());
    }

    @Test
    void testGetWidth() {
        assertEquals(testWidth, testShip.getWidth());
    }

    @Test
    void testGetHeight() {
        assertEquals(1, testShip.getHeight());
    }

    @Test
    void testGetShipSections() {
        assertArrayEquals(testShip.shipSections, testShip.getShipSections());
    }

    @Test
    void testGetShipSection() {
        assertEquals(testShip.shipSections[1], testShip.getShipSection(1));
    }

    @Test
    void testSetWidth() {
        testShip.setWidth(4);
        assertEquals(4, testShip.getWidth());
    }

    @Test
    void testSetHeight() {
        testShip.setHeight(4);
        assertEquals(4, testShip.getHeight());
    }

    void sinkShip(Ship ship){
        for(ShipSection shipSection : ship.getShipSections()) {
            shipSection.receiveHit();
        }
    }
}