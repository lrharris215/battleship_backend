package com.battleship.backend.models;

public class Board implements Boardable{
    Sectionable[][] grid;
    String name;

    public Board(String name){
        this.name = name;
        grid = new Sectionable[10][10];

        setUp();
    }

    public Sectionable[][] getGrid(){
        return grid;
    }

    public String getName(){
        return name;
    }

    public Sectionable getSection(int row, int col){
        return grid[row][col];
    }

    public void addShip(Ship ship, int row, int col){
        for(int i = 0; i < ship.getWidth(); i++){
            addSection(ship.getShipSection(i), row, col + i);
        }
        for(int i = 0; i < ship.getHeight(); i++){
            addSection(ship.getShipSection(i), row + i, col);
        }
    }

    public void addSection(Sectionable section, int row, int col){
        grid[row][col] = section;
    }

    private void setUp() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new NullSection();
            }
        }
    }

    @Override
    public String toString() {
        return "Name: " + getName() + ", grid: " + getGrid().length;
    }

}
