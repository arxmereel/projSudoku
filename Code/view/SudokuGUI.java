/**
 * File: SudokuGUI.java
 * @author: Dylan Wilson
 * Course: CSC335
 * Description:
 * Class for a GUI that plays Sudoku.
 * WASD are used to navigate the board. Arrow keys can also be used, but CTRL must be held down.
 * The current row, column, and section is highlighted in a light blue,
 * and the current cell is highlighted in a slightly darker blue.
 */

package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.GameManager;

public class SudokuGUI extends Application {
	
	private GridPane mainPane;
	private GridPane boardPane; // holds the board of the game
	private GridPane buttonPane; // pane to hold buttonOptionsPane and buttonNumbersPane
	private GridPane buttonOptionsPane; // pane to hold options buttons
	private GridPane buttonNumbersPane; // pane to hold the numberButtons, the buttons to input answers
	
	private GridPane sectionPane[][]; // 3x3 of sections (used for borders)
	
	private Label cellLabel[][]; // 9x9 of cell labels for correct answer and notes
	private Label mistakesLabel; // displays current number of mistakes made
	private Label infoLabel; // explains how to play the game and displays when the game is won
	
	private Button[][] numberButtons; // 3x3 of buttons to press to input
	private Button newGame; // begins a new game
	private Button modeToggle; // toggles between note-taking mode and answer mode
	
	private int col; // x value of current cell
	private int row; // y value of current cell
	private int mistakes; // current number of mistakes the player has made
	
	private boolean noteMode; // boolean to determine if note taking mode is on or off
	
	private int cellStatus[][]; // determine if the cell is empty (0), uneditable (1), or filled with notes (2)
	
	private GameManager game;
	
	public static void main(String args[]) {
		launch();
	}
	
	@Override
	public void start(Stage stage) {
		stage.setTitle("Sudoku");
		layoutGUI();
		
		Scene scene = new Scene(mainPane, 1000, 600);
		
		registerKeyPresses(scene);
		registerButtons();
		newGame();
		
		stage.setScene(scene);
		stage.show();
	}
	
	private void layoutGUI() {
		
		mainPane = new GridPane();
		boardPane = new GridPane();
		buttonPane = new GridPane();
		buttonNumbersPane = new GridPane();
		buttonOptionsPane = new GridPane();
		
		sectionPane = new GridPane[3][3];
		
		cellLabel = new Label[9][9];
		
		mistakesLabel = new Label();
		infoLabel = new Label();
		
		numberButtons = new Button[3][3];
		newGame = new Button("New Game");
		modeToggle = new Button("Notes: OFF");
		
		// arrange game
		mainPane.add(boardPane, 0, 0);
		mainPane.add(buttonPane, 1, 0);
		buttonPane.add(buttonNumbersPane, 0, 0);
		buttonPane.add(buttonOptionsPane, 0, 1);
		buttonPane.add(mistakesLabel, 0, 2);
		buttonPane.add(infoLabel, 0, 3);
		buttonOptionsPane.add(newGame, 0, 0);
		buttonOptionsPane.add(modeToggle, 1, 0);
		
		// arrange sections on board
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				sectionPane[i][j] = new GridPane();
				boardPane.add(sectionPane[i][j], i, j);
				sectionPane[i][j].setStyle("-fx-border-color: black;");
			}
		}
		
		// initialize cellLabels and add them to the board and set style
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				cellLabel[i][j] = new Label();
				cellLabel[i][j].setStyle("-fx-font: normal bold 30px 'serif'; -fx-padding: 5px;"
						+ "-fx-border-color: lightgray; -fx-background-color: white;");
				sectionPane[j/3][i/3].add(cellLabel[i][j], j%3, i%3);
				cellLabel[i][j].setMaxHeight(60);
				cellLabel[i][j].setMinHeight(60);
				cellLabel[i][j].setMaxWidth(60);
				cellLabel[i][j].setMinWidth(60);
				cellLabel[i][j].setAlignment(Pos.CENTER);
				cellLabel[i][j].setWrapText(true);
			}
		}
		
		// arrange number buttons and make them look pretty
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				numberButtons[i][j] = new Button(String.valueOf(i*3 + j+1));
				numberButtons[i][j].setStyle("-fx-background-color: lightblue; -fx-text-fill: white;"
						+ "-fx-font-size: 50;");
				buttonNumbersPane.add(numberButtons[i][j], j, i);
				numberButtons[i][j].setMaxHeight(100);
				numberButtons[i][j].setMinHeight(100);
				numberButtons[i][j].setMaxWidth(100);
				numberButtons[i][j].setMinWidth(100);
			}
		}
		
		// make the panes pretty
		mainPane.setStyle("-fx-padding: 20px;");
		buttonNumbersPane.setStyle("-fx-padding: 20px;");
		buttonNumbersPane.setHgap(5);
		buttonNumbersPane.setVgap(5);
		boardPane.setStyle("-fx-border-color: black;");
		buttonOptionsPane.setStyle("-fx-padding: 20px;");
		buttonOptionsPane.setHgap(6);
		
		// make the new game and toggle buttons pretty
		newGame.setStyle("-fx-background-color: blue; -fx-text-fill: white;"
				+ "-fx-font-size: 20");
		newGame.setMaxHeight(50);
		newGame.setMinHeight(50);
		newGame.setMaxWidth(152);
		newGame.setMinWidth(152);
		modeToggle.setStyle("-fx-background-color: white; -fx-text-fill: black;"
				+ "-fx-font-size: 20");
		modeToggle.setMaxHeight(50);
		modeToggle.setMinHeight(50);
		modeToggle.setMaxWidth(152);
		modeToggle.setMinWidth(152);
		
		mistakesLabel.setStyle("-fx-padding: 10px; -fx-font: normal 15px 'serif';");
		infoLabel.setStyle("-fx-padding: 10px; -fx-font: normal 15px 'serif';");
	}
	
	/**
	 * Registers buttons and assigns functions and events to each.
	 */
	private void registerButtons() {
		newGame.setOnAction(e -> {
			newGame();
		});
		modeToggle.setOnAction(e -> {
			toggleMode();
		});
		numberButtons[0][0].setOnAction(e -> {
			if (noteMode) addNote(1);
			else makeMove(1);
		});
		numberButtons[0][1].setOnAction(e -> {
			if (noteMode) addNote(2);
			else makeMove(2);
		});
		numberButtons[0][2].setOnAction(e -> {
			if (noteMode) addNote(3);
			else makeMove(3);
		});
		numberButtons[1][0].setOnAction(e -> {
			if (noteMode) addNote(4);
			else makeMove(4);
		});
		numberButtons[1][1].setOnAction(e -> {
			if (noteMode) addNote(5);
			else makeMove(5);
		});
		numberButtons[1][2].setOnAction(e -> {
			if (noteMode) addNote(6);
			else makeMove(6);
		});
		numberButtons[2][0].setOnAction(e -> {
			if (noteMode) addNote(7);
			else makeMove(7);
		});
		numberButtons[2][1].setOnAction(e -> {
			if (noteMode) addNote(8);
			else makeMove(8);
		});
		numberButtons[2][2].setOnAction(e -> {
			if (noteMode) addNote(9);
			else makeMove(9);
		});
	}
	
	/**
	 * Registers the key presses and assigns key presses to functions.
	 * @param scene: The scene object. Required to read key presses.
	 */
	private void registerKeyPresses(Scene scene) {
		scene.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) {
				moveCurr("left");
			} else if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) {
				moveCurr("right");
			} else if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.W) {
				moveCurr("up");
			} else if (e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.S) {
				moveCurr("down");
			} else if (e.getCode() == KeyCode.ALT) {
				toggleMode();
			} else if (e.getCode() == KeyCode.DIGIT1 || e.getCode() == KeyCode.NUMPAD1) {
				if (noteMode) addNote(1);
				else makeMove(1);
			} else if (e.getCode() == KeyCode.DIGIT2 || e.getCode() == KeyCode.NUMPAD2) {
				if (noteMode) addNote(2);
				else makeMove(2);
			} else if (e.getCode() == KeyCode.DIGIT3 || e.getCode() == KeyCode.NUMPAD3) {
				if (noteMode) addNote(3);
				else makeMove(3);
			} else if (e.getCode() == KeyCode.DIGIT4 || e.getCode() == KeyCode.NUMPAD4) {
				if (noteMode) addNote(4);
				else makeMove(4);
			} else if (e.getCode() == KeyCode.DIGIT5 || e.getCode() == KeyCode.NUMPAD5) {
				if (noteMode) addNote(5);
				else makeMove(5);
			} else if (e.getCode() == KeyCode.DIGIT6 || e.getCode() == KeyCode.NUMPAD6) {
				if (noteMode) addNote(6);
				else makeMove(6);
			} else if (e.getCode() == KeyCode.DIGIT7 || e.getCode() == KeyCode.NUMPAD7) {
				if (noteMode) addNote(7);
				else makeMove(7);
			} else if (e.getCode() == KeyCode.DIGIT8 || e.getCode() == KeyCode.NUMPAD8) {
				if (noteMode) addNote(8);
				else makeMove(8);
			} else if (e.getCode() == KeyCode.DIGIT9 || e.getCode() == KeyCode.NUMPAD9) {
				if (noteMode) addNote(9);
				else makeMove(9);
			}
		});
	}
	
	/**
	 * Begins a new game. Unfinished.
	 */
	private void newGame() {
		
		// reset colors and labels
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				cellLabel[i][j].setText("");
				cellLabel[i][j].setStyle("-fx-background-color: white; -fx-font: normal bold 30px 'serif';"
						+ "-fx-padding: 5px; -fx-border-color: lightgray;");
			}
		}
		
		// initialize variables and toggle button, or reset them
		infoLabel.setText("Navigate the board with WASD or arrow keys, use buttons or \nnumber"
				+ " keys to input answers. Toggle notes mode with alt.");
		row = 4;
		col = 4;
		mistakes = 0;
		mistakesLabel.setText("Mistakes: " + mistakes);
		modeToggle.setStyle("-fx-background-color: white; -fx-text-fill: black;"
				+ "-fx-font-size: 20");
		modeToggle.setText("Notes: OFF");
		noteMode = false;
		cellStatus = new int[9][9];
		game = new GameManager();
		
		game.generateSudoku();
		game.printGameBoard();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (game.getCellVal(i, j) != 0) {
					cellLabel[i][j].setText(String.valueOf(game.getCellVal(i, j)));
					cellStatus[i][j] = 1;
				} else {
					cellStatus[i][j] = 0;
				}
			}
		}
		
		changeColor(true);

	}
	
	/**
	 * Toggles between note-taking mode and answer mode.
	 */
	private void toggleMode() {
		if (noteMode == false) {
			modeToggle.setStyle("-fx-background-color: blue; -fx-text-fill: white;"
					+ "-fx-font-size: 20");
			modeToggle.setText("Notes: ON");
			noteMode = true;
		} else {
			modeToggle.setStyle("-fx-background-color: white; -fx-text-fill: black;"
					+ "-fx-font-size: 20");
			modeToggle.setText("Notes: OFF");
			noteMode = false;
		}
	}
	
	/**
	 * Moves the current selected cell in the given direction.
	 * Checks if the direction is valid, then changes the color of the cells.
	 * @param direction, direction to move: left, right, up, down
	 */
	private void moveCurr(String direction) {
		
		// if direction is valid
		if ((col != 0 && direction.equals("left")) || (col != 8 && direction.equals("right")) ||
			(row != 0 && direction.equals("up")) || (row != 8 && direction.equals("down"))) {
			
			changeColor(false);
			if (direction.equals("left")) col -= 1;
			else if (direction.equals("right")) col += 1;
			else if (direction.equals("up")) row -= 1;
			else if (direction.equals("down")) row += 1;
			changeColor(true);
			System.out.println("X: " + col + " Y: " + row);
		}
	}
	
	/**
	 * Makes a move at the current cell. Checks if move is correct.
	 * @param input the input value of the move
	 */
	private void makeMove(int input) {
		if (cellStatus[row][col] != 1) {
			if (game.makeMove(row, col, input)) {
				cellLabel[row][col].setText(String.valueOf(input));
				cellStatus[row][col] = 1;
				cellLabel[row][col].setStyle("-fx-background-color: #c0dffb; -fx-font: normal bold 30px 'serif'; "
						+ "-fx-padding: 5px; -fx-border-color: lightgray;");
			} else {
				cellLabel[row][col].setText(String.valueOf(input));
				mistakes++;
				cellStatus[row][col] = 0;
				mistakesLabel.setText("Mistakes: " + mistakes);
				cellLabel[row][col].setStyle("-fx-background-color: #c0dffb; -fx-font: normal bold 30px 'serif'; "
						+ "-fx-padding: 5px; -fx-border-color: lightgray; -fx-text-fill: red;");
			}
		}
		
		if (game.isGameOver()) {
			infoLabel.setText("You won!");
		}
	}
	
	/**
	 * Adds a note to the given cell. Checks if note already exists. If it does then deletes that note.
	 * @param val the value of the note to add
	 */
	private void addNote(int val) {
		if (cellStatus[row][col] != 1) {
			if (game.getCellCands(row, col).contains(val)) {
				game.delCandidate(row, col, val);
				if (game.getCellCands(row, col).size() == 0) {
					cellLabel[row][col].setText("");
				} else {
					cellLabel[row][col].setText(game.getCellCands(row, col).toString());
				}
			} else {
				game.addCandidate(row, col, val);
				cellLabel[row][col].setStyle("-fx-background-color: #c0dffb; -fx-font: normal bold 12px 'serif';"
								+ "-fx-padding: 2px; -fx-border-color: lightgray; -fx-text-fill: gray;");
				cellLabel[row][col].setText(game.getCellCands(row, col).toString());
				cellStatus[row][col] = 2;
			}
		}
	}
	
	/**
	 * Changes the color of the current cell's row, column, and section by changing the style.
	 * setStyle() function is not very robust so it takes a lot of extra code.
	 * Takes into account whether or not cells to change are incorrect, notes, or correct.
	 * Also makes sure to know the difference between adding color and removing color.
	 * 
	 * @param add boolean of whether or not color is being added.
	 */
	private void changeColor(boolean add) {
		
		// row and column
		for (int i = 0; i < 9; i++) {
			if (add) {
				// checks if cell is empty/wrong (0), taken/uneditable (1), or filled with notes (2)
				if (cellStatus[i][col] == 0) {
					cellLabel[i][col].setStyle("-fx-background-color: #e3ecf3; -fx-font: normal bold 30px 'serif';"
							+ "-fx-padding: 5px; -fx-border-color: lightgray; -fx-text-fill: red;");
				} else if (cellStatus[i][col] == 1) {
					cellLabel[i][col].setStyle("-fx-background-color: #e3ecf3; -fx-font: normal bold 30px 'serif';"
							+ "-fx-padding: 5px; -fx-border-color: lightgray;");
				} else {
					cellLabel[i][col].setStyle("-fx-background-color: #e3ecf3; -fx-font: normal bold 12px 'serif';"
							+ "-fx-padding: 2px; -fx-border-color: lightgray; -fx-text-fill: gray;");
				}
				
				if (cellStatus[row][i] == 0) {
					cellLabel[row][i].setStyle("-fx-background-color: #e3ecf3; -fx-font: normal bold 30px 'serif';"
							+ "-fx-padding: 5px; -fx-border-color: lightgray; -fx-text-fill: red;");
				} else if (cellStatus[row][i] == 1) {
					cellLabel[row][i].setStyle("-fx-background-color: #e3ecf3; -fx-font: normal bold 30px 'serif';"
							+ "-fx-padding: 5px; -fx-border-color: lightgray;");
				} else {
					cellLabel[row][i].setStyle("-fx-background-color: #e3ecf3; -fx-font: normal bold 12px 'serif';"
							+ "-fx-padding: 2px; -fx-border-color: lightgray; -fx-text-fill: gray;");
				}
				
				
			} else {
				if (cellStatus[i][col] == 0) {
					cellLabel[i][col].setStyle("-fx-background-color: white; -fx-font: normal bold 30px 'serif';"
							+ "-fx-padding: 5px; -fx-border-color: lightgray; -fx-text-fill: red;");
				} else if (cellStatus[i][col] == 1) {
					cellLabel[i][col].setStyle("-fx-background-color: white; -fx-font: normal bold 30px 'serif';"
							+ "-fx-padding: 5px; -fx-border-color: lightgray;");
				} else {
					cellLabel[i][col].setStyle("-fx-background-color: white; -fx-font: normal bold 12px 'serif';"
							+ "-fx-padding: 2px; -fx-border-color: lightgray; -fx-text-fill: gray;");
				}
				
				if (cellStatus[row][i] == 0) {
					cellLabel[row][i].setStyle("-fx-background-color: white; -fx-font: normal bold 30px 'serif';"
							+ "-fx-padding: 5px; -fx-border-color: lightgray; -fx-text-fill: red;");
				} else if (cellStatus[row][i] == 1) {
					cellLabel[row][i].setStyle("-fx-background-color: white; -fx-font: normal bold 30px 'serif';"
							+ "-fx-padding: 5px; -fx-border-color: lightgray;");
				} else {
					cellLabel[row][i].setStyle("-fx-background-color: white; -fx-font: normal bold 12px 'serif';"
							+ "-fx-padding: 2px; -fx-border-color: lightgray; -fx-text-fill: gray;");
				}
			}
		}
				
		// determine which section to color
		int startX = 0, endX = 0, startY = 0, endY = 0;
		if (col < 3 && row < 3) {
			startX = 0;
			endX = 3;
			startY = 0;
			endY = 3;
		} else if ((col >= 3 && col < 6) && (row < 3)) {
			startX = 3;
			endX = 6;
			startY = 0;
			endY = 3;
		} else if ((col >= 6) && (row < 3)) {
			startX = 6;
			endX = 9;
			startY = 0;
			endY = 3;
		} else if ((col < 3) && (row >= 3 && row < 6)) {
			startX = 0;
			endX = 3;
			startY = 3;
			endY = 6;
		} else if ((col >= 3 && col < 6) && (row >= 3 && row < 6)) {
			startX = 3;
			endX = 6;
			startY = 3;
			endY = 6;
		} else if ((col >= 6) && (row >= 3 && row < 6)) {
			startX = 6;
			endX = 9;
			startY = 3;
			endY = 6;
		} else if ((col < 3) && (row >= 6)) {
			startX = 0;
			endX = 3;
			startY = 6;
			endY = 9;
		} else if ((col >= 3 && col < 6) && (row >= 6)) {
			startX = 3;
			endX = 6;
			startY = 6;
			endY = 9;
		} else if ((col >= 6) && (row >= 6)) {
			startX = 6;
			endX = 9;
			startY = 6;
			endY = 9;
		}
		
		// color that section
		for (int i = startX; i < endX; i++) {
			for (int j = startY; j < endY; j++) {
				if (add) {
					if (cellStatus[j][i] == 0) {
						cellLabel[j][i].setStyle("-fx-background-color: #e3ecf3; -fx-font: normal bold 30px 'serif';"
								+ "-fx-padding: 5px; -fx-border-color: lightgray; -fx-text-fill: red;");
					} else if (cellStatus[j][i] == 1) {
						cellLabel[j][i].setStyle("-fx-background-color: #e3ecf3; -fx-font: normal bold 30px 'serif';"
								+ "-fx-padding: 5px; -fx-border-color: lightgray;");
					} else {
						cellLabel[j][i].setStyle("-fx-background-color: #e3ecf3; -fx-font: normal bold 12px 'serif';"
								+ "-fx-padding: 2px; -fx-border-color: lightgray; -fx-text-fill: gray;");
					}
				} else {
					if (cellStatus[j][i] == 0) {
						cellLabel[j][i].setStyle("-fx-background-color: white; -fx-font: normal bold 30px 'serif';"
								+ "-fx-padding: 5px; -fx-border-color: lightgray; -fx-text-fill: red;");
					} else if (cellStatus[j][i] == 1) {
						cellLabel[j][i].setStyle("-fx-background-color: white; -fx-font: normal bold 30px 'serif';"
								+ "-fx-padding: 5px; -fx-border-color: lightgray;");
					} else {
						cellLabel[j][i].setStyle("-fx-background-color: white; -fx-font: normal bold 12px 'serif';"
								+ "-fx-padding: 2px; -fx-border-color: lightgray; -fx-text-fill: gray;");
					}
				}
			}
		}
		
		// current cell
		if (add) {
			if (cellStatus[row][col] == 0) {
				cellLabel[row][col].setStyle("-fx-background-color: #c0dffb; -fx-font: normal bold 30px 'serif'; "
						+ "-fx-padding: 5px; -fx-border-color: lightgray; -fx-text-fill: red;");
			} else if (cellStatus[row][col] == 1) {
				cellLabel[row][col].setStyle("-fx-background-color: #c0dffb; -fx-font: normal bold 30px 'serif'; "
						+ "-fx-padding: 5px; -fx-border-color: lightgray;");
			} else {
				cellLabel[row][col].setStyle("-fx-background-color: #c0dffb; -fx-font: normal bold 12px 'serif';"
						+ "-fx-padding: 2px; -fx-border-color: lightgray; -fx-text-fill: gray;");
			}
		} else {
			if (cellStatus[row][col] == 0) {
				cellLabel[row][col].setStyle("-fx-background-color: white; -fx-font: normal bold 30px 'serif'; "
						+ "-fx-padding: 5px; -fx-text-fill: red;");
			} else if (cellStatus[row][col] == 1) {
				cellLabel[row][col].setStyle("-fx-background-color: white; -fx-font: normal bold 30px 'serif'; "
						+ "-fx-padding: 5px;");
			} else {
				cellLabel[row][col].setStyle("-fx-background-color: white; -fx-font: normal bold 12px 'serif';"
						+ "-fx-padding: 2px; -fx-border-color: lightgray; -fx-text-fill: gray;");
			}
			
		}
	}
}
