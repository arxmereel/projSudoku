package model;


/**
 * Basic code for the Game Manager Class for Sudoku.
 * 
 * @author maxtung, chiu ?????
 */

public class GameManager {
    protected Board gameBoard;

    public GameManager(){
        this.gameBoard = new Board();
    }


    //Create a dupe board, try the move, if valid, make edit to actual board and return true. If not, return false.

    //IMPORTANT PSA: A move can be valid but still be the incorrect move
    //Possbily generate whole puzzle and keep a backup board to directly check answers in place???

    public boolean makeMove(int row, int col, int val){
        Board tempBoard = new Board(this.gameBoard);

            Section rowSection = new Section(tempBoard.getRow(row));
            Section colSection = new Section(tempBoard.getCol(col));
            Section secSection = new Section(tempBoard.getSection(row, col));

            if(rowSection.isValid() && colSection.isValid() && secSection.isValid()){
                gameBoard.getCell(row, col).setValue(val);
            }
        return false;
    }

    public void addCandidates(int row, int col, int[] cands){
        gameBoard.getCell(row, col).addCandidates(cands);
    }

    public void clearCandidates(int row, int col){
        gameBoard.getCell(row, col).clearCandidates();
    }

    public void delCandidate(int row, int col, int del){
        gameBoard.getCell(row, col).delCandidate(del);
    }

    //for GUI Parese
    public void getCellVal(int row, int col){
        gameBoard.getCell(row, col).getValue();
    }

    //for GUI Parese
    public void getCellCands(int row, int col){
        gameBoard.getCell(row, col).getCandidates();
    }

}
