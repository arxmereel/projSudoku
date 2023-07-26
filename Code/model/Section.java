package model;

import java.util.HashSet;
import java.util.Set;

/**
 * Basic code for the Section Class for Sudoku.
 * 
 * @author maxtung
 */

public class Section {
    protected Cell[] cells;
    protected boolean complete;

    public Section(){
        this.cells = new Cell[9];
        for (int i = 0; i <9; i++) {
            this.cells[i] = new Cell();
        }
        this.complete = false;
    }

    //For Indicies when the Section is used as a 3x3 Area, refer to Documents/Indicies MAP.png
    //For Row form, 0-8 from left to right. For Col form, top to down.
    public Cell getCell(int index){
        if (index >= 0 && index < 9) {
            return this.cells[index];
        }
        throw new IllegalArgumentException("Index out of bounds for Section");
    }

    public void setCell(int index, Cell cell) {
        if (index >= 0 && index < 9) {
            this.cells[index] = cell;
        } else {
            throw new IllegalArgumentException("Index out of bounds for Section");
        }
    }

    //PSA: A SECTION CAN BE VALID WITH ZEROS (EMPTY CELLS) in them!!!
    //USE TO CHECK IF A MOVE WORKS
    public boolean isValid() {
        Set<Integer> dupeDetect = new HashSet<>();
        for (Cell cell : this.cells) {
                if (cell.getValue() != 0 && dupeDetect.add(cell.getValue()) == false){
                    return false;
                }
        }
        return true;
    }
}
