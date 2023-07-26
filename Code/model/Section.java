package model;


/**
 * Basic code for the Section Class for Sudoku.
 * 
 * @author maxtung
 */

public class Section {
    protected Cell[] cells;
    protected boolean valid;

    public Section(){
        this.cells = new Cell[9];
        for (int i = 0; i <9; i++) {
            this.cells[i] = new Cell();
        }
        this.valid = false;
    }

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

    
}
