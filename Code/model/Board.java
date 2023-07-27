package model;

/**
 * Basic code for the Board Class for Sudoku.
 * 
 * @author maxtung
 */

public class Board {
    protected Cell[][] board;

    public Board(){
        this.board = new Cell[9][9];
        for (int i = 0; i <9; i++) {
            for (int j = 0; j <9; j++) {
                this.board[i][j] = new Cell();
            }
        }
    }

    // Clone constructor
    public Board(Board otherBoard) {
        this.board = new Cell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.board[i][j] = new Cell(otherBoard.getCell(i,j));
            }
        }
    }

    public Cell getCell(int row, int col){
        return board[row][col];
    }

    public Cell[] getRow(int row){
        if (row >= 0 && row < 9){
            return this.board[row];
        }else{
            throw new IllegalArgumentException("Row index out of bounds");
        }
    }

    public Cell[] getCol(int col){
        if (col >= 0 && col < 9){
            Cell[] column = new Cell[9];

            for (int i = 0; i < 9; i++) {
                column[i] = this.board[i][col];
            }

            return column;
        }else{
            throw new IllegalArgumentException("col index out of bounds");
        }
    }

    //Use for checking move validity
    public Cell[] getSection(int row, int col) {
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        
        Cell[] section = new Cell[9];
        int index = 0;
        
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                section[index] = board[i][j];
                index++;
            }
        }
        return section;
    }


    //USE when Checking WHOLE BOARD
    //Mapping for Cells is the same as a 3x3 Area, refer to Documents/Indicies MAP.png
    public Cell[] getSection(int section){
        if (section >= 0 && section < 9){
            Cell[] sectionCells = new Cell[9];
            int index = 0;

            int startRow = (section / 3) * 3;
            int startCol = (section % 3) * 3;

            for (int row = startRow; row < startRow + 3; row++) {
                for (int col = startCol; col < startCol + 3; col++) {
                    sectionCells[index++] = this.board[row][col];
                }
            }

            return sectionCells;
        }else{
            throw new IllegalArgumentException("col index out of bounds");
        }
    }

    //SAME RUES AS SECTION
    public boolean isValid() {
        for (int i = 0; i < 9; i++) {
            Section rowSection = new Section(getRow(i));
            Section colSection = new Section(getCol(i));
            Section secSection = new Section(getSection(i));
            if (rowSection.isValid()== false || colSection.isValid()== false || secSection.isValid()== false) {
                return false;
            }
        }
        return true;
    }

    //SAME RULES AS SECTION
    public boolean isComplete() {
        for (int i = 0; i < 9; i++) {
            Section rowSection = new Section(getRow(i));
            Section colSection = new Section(getCol(i));
            Section secSection = new Section(getSection(i));
            if (rowSection.isComplete() == false || colSection.isComplete() == false || secSection.isComplete() == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * Prints the generated Sudoku grid to the console.
     */
    public String toString(){
        String str = "";
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                str += getCell(i,j).getValue() + " ";
            }
            str += "\n";
        }
        return str;    
    }
}
