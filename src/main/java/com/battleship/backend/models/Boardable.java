package com.battleship.backend.models;

public interface Boardable {
    String getName();
    Sectionable getSection(int row, int col);
    Sectionable[][] getGrid();
    void addShip(Ship ship, int row, int col);

    void addSection(Sectionable section, int row, int col);

}
