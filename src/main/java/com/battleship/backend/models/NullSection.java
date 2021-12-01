package com.battleship.backend.models;

public class NullSection implements Sectionable {
    boolean isHit;

    public NullSection() {
        isHit = false;
    }

    public void receiveHit() {
        isHit = true;
    }

    public boolean getIsHit() {
        return isHit;
    }

    public String getShip() {
        return null;
    }
}
