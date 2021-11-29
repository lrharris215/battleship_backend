package com.battleship.backend.models;

public class Board {
    Sectionable[][] grid;
    String name;

    public Board(String name){
        this.name = name;
        grid = new Sectionable[10][10];
    }

    public Sectionable[][] getGrid(){
        return grid;
    }

    private void setUpBoard() {

    }




}
