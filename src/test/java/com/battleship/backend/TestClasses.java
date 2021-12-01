package com.battleship.backend;

import com.battleship.backend.models.Boardable;
import com.battleship.backend.models.NullSection;
import com.battleship.backend.models.Sectionable;
import com.battleship.backend.models.Ship;

public class TestClasses {
    public static class TestBoard implements Boardable{
            String name;
            Sectionable[][] grid;
            public TestBoard(){
                name = "testBoard";
                grid = new Sectionable[3][3];
                setUpBoard();
            }
            public String getName() {
                return name;
            }

            public Sectionable getSection(int row, int col) {
                return grid[row][col];
            }

            public Sectionable[][] getGrid() {
                return grid;
            }

            public void addShip(Ship ship, int row, int col) {
                grid[0][0] = ship.getShipSection(0);
                grid[0][1] = ship.getShipSection(1);
            }

            public void addSection(Sectionable section, int row, int col) {

            }
            private void setUpBoard() {
                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid[i].length; j++) {
                        grid[i][j] = new NullSection();
                    }
                }
            }
    }
}
