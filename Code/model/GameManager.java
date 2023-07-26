package model;
import java.util.Random;

/**
 * Basic code for the Game Manager Class for Sudoku.
 * 
 * @author maxtung, chiu ?????
 */

public class GameManager {
    protected Board gameBoard;
    private static final int SIZE = 9;
    private static final int BOX_SIZE = 3;
    private static final int EMPTY_CELL = 0;
    private Random random;

    public GameManager(){
        this.gameBoard = new Board();
        this.random = new Random();
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

    /**
     * Generates a Sudoku puzzle.
     */
    public void generateSudoku() {
        // fillDiagonalBoxes();
        fillFirstRoll();
        solveSudoku(0, 0);
        System.out.println(gameBoard);
        // removeNumbers();
    }

    /**
     * Fills a 3x3 box in the Sudoku grid with random valid numbers.
     *
     * @param row The starting row index of the box.
     * @param col The starting column index of the box.
     */
    private void fillFirstRoll() {
        int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        shuffleArray(numbers);
    
        for (int i = 0; i < 9; i++) {
            gameBoard.getCell(0,i).setValue(numbers[i]);
            // grid[i][0] = numbers[index++];
        }
    }

    /**
     * Shuffles an array using the Fisher-Yates algorithm.
     *
     * @param array The array to be shuffled.
     */
    private void shuffleArray(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    /**
     * Solves the Sudoku puzzle using backtracking algorithm.
     *
     * @param row The row index of the cell to start solving from.
     * @param col The column index of the cell to start solving from.
     * @return {@code true} if a solution is found, {@code false} otherwise.
     */
    private boolean solveSudoku(int row, int col) {
        if (row == SIZE) {
            row = 0;
            if (++col == SIZE) {
                return true; // Reached the end of the grid
            }
        }
        if (gameBoard.getCell(row, col).getValue() != EMPTY_CELL) {
            return solveSudoku(row + 1, col);
        }
        for (int num = 1; num <= SIZE; num++) {
            if (isValidPlacement(row, col, num)) {
                gameBoard.getCell(row, col).setValue(num);
                if (solveSudoku(row + 1, col)) {
                    return true;
                }
                gameBoard.getCell(row, col).setValue(EMPTY_CELL);
            }
        }
        return false;
    }

    /**
     * Checks if placing a number at a specific cell is valid.
     *
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @param num The number to be placed.
     * @return {@code true} if the placement is valid, {@code false} otherwise.
     */
    private boolean isValidPlacement(int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (gameBoard.getCell(row, i).getValue() == num || gameBoard.getCell(i, col).getValue() == num) {
                return false;
            }
        }
        int boxRowStart = row - row % BOX_SIZE;
        int boxColStart = col - col % BOX_SIZE;
        for (int i = boxRowStart; i < boxRowStart + BOX_SIZE; i++) {
            for (int j = boxColStart; j < boxColStart + BOX_SIZE; j++) {
                if (gameBoard.getCell(i, j).getValue() == num) {
                    return false;
                }
            }
        }
        return true;
    }


    

}
