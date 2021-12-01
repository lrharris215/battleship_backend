package com.battleship.backend.models;

public class NullSection implements Sectionable {
    boolean isHit;
    boolean isShip;

    public NullSection() {
        isHit = false;
        isShip = false;
    }

    public void receiveHit() {
        isHit = true;
    }

    public boolean getIsHit() {
        return isHit;
    }
    public boolean getIsShip(){
        return isShip;
    }
}
