package model;

import java.util.Random;

/**
 * Basic code for the Game Manager Class for Sudoku.
 * 
 * @author maxtung, Chiu Yeh Chen
 */

public class GameManager {
    protected Board gameBoard;
    protected Board answerBoard;
    private static final int SIZE = 9;
    private static final int BOX_SIZE = 3;
    private static final int EMPTY_CELL = 0;
    private static final int CELL_TO_REMOVE = 40; // 50 = hard, 40 = normal make it really small for testing
    private int zeroCounter = 0;
    private Random random;

    public GameManager() {
        this.gameBoard = new Board();
        this.random = new Random();
        zeroCounter = CELL_TO_REMOVE;
    }

    // Create a dupe board, try the move, if valid, make edit to actual board and
    // return true. If not, return false.

    // IMPORTANT PSA: A move can be valid but still be the incorrect move
    // Possbily generate whole puzzle and keep a backup board to directly check
    // answers in place???

    // public boolean makeMove(int row, int col, int val) {
    //     Board tempBoard = new Board(this.gameBoard);

    //     Section rowSection = new Section(tempBoard.getRow(row));
    //     Section colSection = new Section(tempBoard.getCol(col));
    //     Section secSection = new Section(tempBoard.getSection(row, col));

    //     if (rowSection.isValid() && colSection.isValid() && secSection.isValid()) {
    //         gameBoard.getCell(row, col).setValue(val);
    //     }
    //     return false;
    // }

    /**
     * fill in the cell used given value.
     * 
     * @param row The row index of the cell .
     * @param col The column index of the cell.
     * @param val the 1-9 number user want to input.
     * @return true if successful(right answer), false otherwise.
     */
    public boolean makeMove(int row, int col, int val){
        if (val < 1 || val > 9) throw new IllegalArgumentException("val index out of range");
        if(answerBoard.getCell(row, col).getValue() == val){
            gameBoard.getCell(row, col).setValue(val);
            zeroCounter --;
            return true;
        }
        return false;
    }

    public void addCandidates(int row, int col, int[] cands) {
        gameBoard.getCell(row, col).addCandidates(cands);
    }

    public void clearCandidates(int row, int col) {
        gameBoard.getCell(row, col).clearCandidates();
    }

    public void delCandidate(int row, int col, int del) {
        gameBoard.getCell(row, col).delCandidate(del);
    }

    // for GUI Parese
    public void getCellVal(int row, int col) {
        gameBoard.getCell(row, col).getValue();
    }

    // for GUI Parese
    public void getCellCands(int row, int col) {
        gameBoard.getCell(row, col).getCandidates();
    }

    public Board getGameBoard() {
		return gameBoard;
	}
    public Board getanswerBoard() {
		return answerBoard;
	}

    /* for debug */
    public void printAnswer(){
        System.out.println("answerBoard:");
        System.out.println(answerBoard);
    }

    /* for debug */
    public void printGameBoard(){
        System.out.println("gameBoard:");
        System.out.println(gameBoard);
    }

    public boolean isGameOver(){
        if (zeroCounter == 0) return true; 
        return false;  
    }

    /**
     * Generates a Sudoku puzzle.
     */
    public void generateSudoku() {
        fillFirstRoll();
        solveSudoku(0, 0, gameBoard);
        answerBoard = new Board(gameBoard);
        removeNumbers(gameBoard);   
        
    }

    /**
     * Generates a hard coded Sudoku puzzle.
     */
    public void generateSudokuForTesting() {
    	int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    	for (int i = 0; i < 9; i++) {
            getGameBoard().getCell(0, i).setValue(numbers[i]);}
        solveSudoku(0, 0, getGameBoard());
        answerBoard = new Board(getGameBoard());
        removeNumbers(getGameBoard());   
        
    }

    /**
     * Fills the first roll in the Sudoku grid with random valid numbers from 1-9.
     *
     * @param row The starting row index of the box.
     * @param col The starting column index of the box.
     */
    private void fillFirstRoll() {
        int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        shuffleArray(numbers);

        for (int i = 0; i < 9; i++) {
            gameBoard.getCell(0, i).setValue(numbers[i]);
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
    private boolean solveSudoku(int row, int col, Board board) {
        if (row == SIZE) {
            row = 0;
            if (++col == SIZE) {
                return true; // Reached the end of the grid
            }
        }
        if (board.getCell(row, col).getValue() != EMPTY_CELL) {
            return solveSudoku(row + 1, col, board);
        }
        for (int num = 1; num <= SIZE; num++) {
            if (isValidPlacement(row, col, num, board)) {
                board.getCell(row, col).setValue(num);
                if (solveSudoku(row + 1, col, board)) {
                    return true;
                }
                board.getCell(row, col).setValue(EMPTY_CELL);
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
    private boolean isValidPlacement(int row, int col, int num, Board board) {
        for (int i = 0; i < SIZE; i++) {
            if (board.getCell(row, i).getValue() == num || board.getCell(i, col).getValue() == num) {
                return false;
            }
        }
        int boxRowStart = row - row % BOX_SIZE;
        int boxColStart = col - col % BOX_SIZE;
        for (int i = boxRowStart; i < boxRowStart + BOX_SIZE; i++) {
            for (int j = boxColStart; j < boxColStart + BOX_SIZE; j++) {
                if (board.getCell(i, j).getValue() == num) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Removes numbers from the completed grid to achieve the desired difficulty
     * level while ensuring a unique solution.
     * 
     * @param board The Sudoku board to remove numbers from.
     */
    private void removeNumbers(Board board) {
        int count = 0;

        while (count < CELL_TO_REMOVE) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);
            Cell cell = board.getCell(row, col);

            if (cell.getValue() != EMPTY_CELL) {
                int temp = cell.getValue();
                cell.setValue(EMPTY_CELL); // Temporarily remove the number from the cell

                Board tempBoard = new Board(board);
                int solutionCount = countSolutions(0, 0, tempBoard);

                if (solutionCount == 1) {
                    // Keep the removal since there is still a unique solution
                    count++;
                } else {
                    // Undo the removal by restoring the original number in the cell
                    cell.setValue(temp);
                }
            }
        }
    }

    /**
     * Counts the number of solutions for the current Sudoku board using
     * backtracking.
     *
     * @param row   The row index of the cell to start solving from.
     * @param col   The column index of the cell to start solving from.
     * @param board The Sudoku board to count solutions for.
     * @return The number of valid solutions for the Sudoku board.
     */
    private int countSolutions(int row, int col, Board board) {
        if (row == SIZE) {
            row = 0;
            if (++col == SIZE) {
                return 1; // Reached the end of the grid, a solution is found
            }
        }
        if (board.getCell(row, col).getValue() != EMPTY_CELL) {
            return countSolutions(row + 1, col, board);
        }

        int solutions = 0;
        for (int num = 1; num <= SIZE; num++) {
            if (isValidPlacement(row, col, num, board)) {
                board.getCell(row, col).setValue(num);
                solutions += countSolutions(row + 1, col, board);
                board.getCell(row, col).setValue(EMPTY_CELL);
            }
        }
        return solutions;
    }

}
