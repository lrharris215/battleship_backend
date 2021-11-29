package com.battleship.backend.models;

public class ShipSection implements Sectionable{
    Ship ship;
    boolean isHit;


    public ShipSection(Ship ship){
        isHit = false;
        this.ship = ship;
    }

    public String toString(){
        return "ShipSection of  " + ship.getName();
    }

    public boolean getIsHit(){
        return isHit;
    }

    public String getShip(){
        return ship.getName();
    }

    public void receiveHit() {
        isHit = true;
    }
}
