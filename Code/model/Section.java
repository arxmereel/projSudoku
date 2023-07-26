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

    public Section(){
        this.cells = new Cell[9];
        for (int i = 0; i <9; i++) {
            this.cells[i] = new Cell();
        }
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

    // A Section can only be Complete if all cells are not zero and the section is Valid.
    public boolean isComplete() {
        if(isValid()){
            for (Cell cell : this.cells) {
                if (cell.getValue() == 0){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    //Implemented for future implementation of hints.
    public Set<Integer> missingVals(){
        Set<Integer> missingIntegers = new HashSet<>();
        Set<Integer> presentIntegers = new HashSet<>();

        for (Cell cell : this.cells) {
            if (cell.getValue() != 0) {
                presentIntegers.add(cell.getValue());
            }
        }

        for (int i = 1; i <= 9; i++) {
            if (!presentIntegers.contains(i)) {
                missingIntegers.add(i);
            }
        }
        return missingIntegers;
    }
}
