package com.battleship.backend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NullSectionTest {

    NullSection testNullSection;

    @BeforeEach
    void initNewNullSection(){
        testNullSection = new NullSection();
    }
    @Test
    void testReceiveHitChangesIsHitToTrue() {
        testNullSection.receiveHit();
        assertTrue(testNullSection.isHit);
    }

    @Test
    void testGetIsHit() {
        assertEquals(testNullSection.isHit, testNullSection.getIsHit());
    }

    @Test
    void testGetIsShipIsAlwaysFalse() {
        assertFalse(testNullSection.getIsShip());
    }
}