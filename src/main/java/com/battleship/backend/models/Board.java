package com.battleship.backend.models;

public class Board {
    Sectionable[][] grid;
    String name;

    public Board(String name){
        this.name = name;
        grid = new Sectionable[10][10];

        setUpBoard();
    }

    public Sectionable[][] getGrid(){
        return grid;
    }

    private void setUpBoard() {
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                grid[i][j] = new NullSection();
            }
        }
    }




}
