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

    /**
     * Base Constructor
     */
    public Section(){
        this.cells = new Cell[9];
        for (int i = 0; i <9; i++) {
            this.cells[i] = new Cell();
        }
    }

    /** Constructor for an existing array of cells.
     * 
     * @param cells array of cells
     */
    public Section(Cell[] cells){
        this.cells = new Cell[9];
        int index = 0;
        
        for (Cell cell : cells) {
            this.cells[index] = new Cell(cell);
            index++;
        }
    }

    
    /** Retrived a cell based on it's index. For Indicies when the Section is used as a 3x3 Area, 
     * refer to Documents/Indicies MAP.png For Row form, 0-8 from left to right. For Col form, top to down.
     * Will throw Illegal argument exception if index is out of bounds.
     * 
     * @param index index of the cell to be retrived
     * @return Cell cell
     */

    public Cell getCell(int index){
        if (index >= 0 && index < 9) {
            return this.cells[index];
        }
        throw new IllegalArgumentException("Index out of bounds for Section");
    }

    /** Set's a particular cell's value bcased on it's index. For Indicies when the Section is used as a 3x3 Area, 
     * refer to Documents/Indicies MAP.png For Row form, 0-8 from left to right. For Col form, top to down.
     * Will throw Illegal argument exception if index is out of bounds.
     * 
     * @param index
     * @param cell
     */

    public void setCell(int index, Cell cell) {
        if (index >= 0 && index < 9) {
            this.cells[index] = cell;
        } else {
            throw new IllegalArgumentException("Index out of bounds for Section");
        }
    }

    /** Checks if the section is valid. PSA: Validity means that te section only contains one instance of 
     * each number 1-9. Therefore, an incomplete section (with zeros) can still be valid.
     * 
     * @return if the section is Valid
     */
    public boolean isValid() {
        Set<Integer> dupeDetect = new HashSet<>();
        for (Cell cell : this.cells) {
                if (cell.getValue() != 0 && dupeDetect.add(cell.getValue()) == false){
                    return false;
                }
        }
        return true;
    }

    /** Checks if the section is Complete. In order for a section to be complete, it can have no zeros 
     * and it must be valid.
     * 
     * @return if the section is Complete
     */
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
