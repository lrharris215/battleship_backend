package com.battleship.backend.models;

public class ShipSection implements Sectionable{
    String status;
    Ship ship;

    public ShipSection(Ship ship){
        status = "OK";
        this.ship = ship;
    }

    public void receiveHit() {
        status = "HIT";
    }

    public String toString(){
        return "ShipSection of  " + ship.getName();
    }

    public String getStatus(){
        return status;
    }

    public String getShip(){
        return ship.getName();
    }
}
