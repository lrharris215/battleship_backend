package com.battleship.backend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipSectionTest {

    ShipSection testShipSection;

    @BeforeEach
    void initNewNullSection(){
        testShipSection = new ShipSection("TestShip");
    }

    @Test
    void testReceiveHitChangesIsHitToTrue() {
        testShipSection.receiveHit();
        assertTrue(testShipSection.isHit);
    }

    @Test
    void testShipSectionStartsWithIsHitEqualsFalse(){
        assertFalse(testShipSection.isHit);
    }

    @Test
    void testGetIsHit() {
        assertEquals(testShipSection.isHit, testShipSection.getIsHit());
    }

    @Test
    void testGetIsShipAlwaysReturnsTrue() {
        assertTrue(testShipSection.getIsShip());
    }

    @Test
    void testGetShipNameReturnsNameOfParentShip() {
        assertEquals("TestShip", testShipSection.getShipName());
    }
}