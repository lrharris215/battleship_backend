package com.battleship.backend.models;

public class Ship {
    int width;
    int height;

    String name;
    String status;



    public Ship(String name, int length){
        this.name = name;
        this.height = 1;
        this.width = length;
        this.status = "OK";
    }

    public void rotate(){
        int temp = getWidth();
        setWidth(getHeight());
        setHeight(temp);
    }

    public void receiveHit(){}


    // getters
    public String getName(){
        return name;
    }

    public String getStatus() {
        return status;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void setWidth(int newWidth){
        width = newWidth;
    }
    public void setHeight(int newHeight){
        height = newHeight;
    }


}
