package com.battleship.backend.models;

public class ShipSection implements Sectionable{
    boolean isHit;
    Ship ship;


    public ShipSection(Ship ship){
        isHit = false;
        this.ship = ship;
    }

    public void receiveHit() {
        isHit = true;
    }

    public String toString(){
        return "ShipSection of  " + ship.getName();
    }

    public boolean getStatus(){
        return isHit;
    }

    public String getShip(){
        return ship.getName();
    }
}
