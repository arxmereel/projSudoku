import java.util.Random;

/**
 * A class to generate a Sudoku puzzle.
 */
public class SudokuGenerator {

    private static final int SIZE = 9;
    private static final int BOX_SIZE = 3;
    private static final int EMPTY_CELL = 0;

    private int[][] grid;
    private Random random;

    /**
     * Constructs a new SudokuGenerator instance.
     */
    public SudokuGenerator() {
        this.grid = new int[SIZE][SIZE];
        this.random = new Random();
    }

    /**
     * Generates a Sudoku puzzle.
     */
    public void generateSudoku() {
        // fillDiagonalBoxes();
        fillFirstRoll();
        solveSudoku(0, 0);
        // removeNumbers();
    }

    /**
     * Fills the diagonal boxes of the Sudoku grid with random valid numbers.
     */
    private void fillDiagonalBoxes() { 
        for (int row = 0; row < SIZE; row += BOX_SIZE) {
            for (int col = 0; col < SIZE; col += BOX_SIZE) {
                fillBox(row, col);
            }
        }
    }

    private void fillFirstRoll() {
        int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        shuffleArray(numbers);
        int index = 0;
        for (int i = 0; i <= BOX_SIZE; i++) {
            grid[i][0] = numbers[index++];
        }
    }

    /**
     * Fills a 3x3 box in the Sudoku grid with random valid numbers.
     *
     * @param row The starting row index of the box.
     * @param col The starting column index of the box.
     */
    private void fillBox(int row, int col) {
        int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        shuffleArray(numbers);
        int index = 0;
        for (int i = row; i < row + BOX_SIZE; i++) {
            for (int j = col; j < col + BOX_SIZE; j++) {
                grid[i][j] = numbers[index++];
            }
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
        if (grid[row][col] != EMPTY_CELL) {
            return solveSudoku(row + 1, col);
        }
        for (int num = 1; num <= SIZE; num++) {
            if (isValidPlacement(row, col, num)) {
                grid[row][col] = num;
                if (solveSudoku(row + 1, col)) {
                    return true;
                }
                grid[row][col] = EMPTY_CELL;
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
            if (grid[row][i] == num || grid[i][col] == num) {
                return false;
            }
        }
        int boxRowStart = row - row % BOX_SIZE;
        int boxColStart = col - col % BOX_SIZE;
        for (int i = boxRowStart; i < boxRowStart + BOX_SIZE; i++) {
            for (int j = boxColStart; j < boxColStart + BOX_SIZE; j++) {
                if (grid[i][j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Removes numbers from the completed grid to achieve the desired difficulty
     * level.
     */
    private void removeNumbers() {
        // Adjust the number of clues here to control difficulty
        int cluesToKeep = 40;
        int count = SIZE * SIZE;
        while (count > cluesToKeep) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);
            if (grid[row][col] != 0) {
                grid[row][col] = 0;
                count--;
            }
        }
    }

    /**
     * Prints the generated Sudoku grid to the console.
     */
    public void printGrid() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Main method to generate and print a Sudoku puzzle.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        SudokuGenerator generator = new SudokuGenerator();
        generator.generateSudoku();
        generator.printGrid();
    }
}
