package com.battleship.backend.models;

import java.util.Arrays;

public class Ship {
    int width;
    int height;

    String name;
    boolean isSunk;

    ShipSection[] shipSections;

    public Ship(){
        this.name = "";
        this.height = 0;
        this.width = 0;
        isSunk = false;
    }
    public Ship(String name, int length) {
        this.name = name;
        this.height = 1;
        this.width = length;
        isSunk = false;

        shipSections = new ShipSection[length];
        fillShip();
    }

    public void fillShip(){
        for(int i = 0; i < shipSections.length; i++) {
            shipSections[i] = new ShipSection(name);
        }
    }

    public void rotate() {
        int temp = width;
        setWidth(getHeight());
        setHeight(temp);
    }

    public boolean checkIsSunk() {
        if(isSunk) return true;
        for (ShipSection shipSection : shipSections) {
            if (!shipSection.getIsHit()) {
                return false;
            }
        }
        isSunk = true;
        return true;
    }

    public boolean equals(Ship otherShip){
        return this.name.equals(otherShip.getName());
    }



    // getters
    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ShipSection[] getShipSections() {
        return shipSections;
    }

    public ShipSection getShipSection(int idx){
        return shipSections[idx];
    }

    public boolean getIsSunk() {
        return isSunk;
    }

    //setters
    public void setWidth(int newWidth) {
        width = newWidth;
    }
    public void setHeight(int newHeight) {
        height = newHeight;
    }




}
