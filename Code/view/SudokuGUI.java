/**
 * File: SudokuGUI.java
 * Author: Dylan Wilson
 * Course: CSC335
 * Description:
 * Class for a GUI that plays Sudoku.
 */

package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SudokuGUI extends Application {
	
	private GridPane mainPane;
	private GridPane boardPane; // holds the board of the game
	private GridPane buttonPane; // pane to hold buttonOptionsPane and buttonNumbersPane
	private GridPane buttonOptionsPane; // pane to hold options buttons
	private GridPane buttonNumbersPane; // pane to hold the numberButtons, the buttons to input answers
	
	private GridPane sectionPane[][]; // 3x3 of sections (used for borders)
	private GridPane cellPane[][]; // 9x9 of cells in GridPane format to use for note taking
	
	private Label notesLabel[][][][]; // 9x9 3x3 of individual labels for note taking for candidates
	private Label cellLabel[][]; // 9x9 of cell labels for correct answer
	
	private Button[][] numberButtons; // 3x3 of buttons to press to input
	private Button newGame; // begins a new game
	private Button modeToggle; // toggles between note-taking mode and answer mode
	
	public static void main(String args[]) {
		launch();
	}
	
	@Override
	public void start(Stage stage) {
		stage.setTitle("Sudoku");
		layoutGUI();
		startGame();
		
		Scene scene = new Scene(mainPane, 1000, 600);
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
		cellPane = new GridPane[9][9];
		
		notesLabel = new Label[9][9][3][3];
		cellLabel = new Label[9][9];
		
		numberButtons = new Button[3][3];
		newGame = new Button("New Game");
		modeToggle = new Button("Notes: OFF");
		
		// arrange game
		mainPane.add(boardPane, 0, 0);
		mainPane.add(buttonPane, 1, 0);
		buttonPane.add(buttonNumbersPane, 0, 0);
		buttonPane.add(buttonOptionsPane, 0, 1);
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
				cellLabel[i][j] = new Label(String.valueOf(i*9 + j+1));
				cellLabel[i][j].setStyle("-fx-font: normal bold 30px 'serif'; -fx-padding: 5px;"
						+ "-fx-border-color: lightgray;");
				sectionPane[j/3][i/3].add(cellLabel[i][j], j%3, i%3);
				cellLabel[i][j].setMaxHeight(60);
				cellLabel[i][j].setMinHeight(60);
				cellLabel[i][j].setMaxWidth(60);
				cellLabel[i][j].setMinWidth(60);
				cellLabel[i][j].setAlignment(Pos.CENTER);
				
				// also initialize the cellPane and notesLabel objects, 
				// but dont add them to the board until notes are toggled and used
				cellPane[i][j] = new GridPane();
				for (int m = 0; m < 3; m++) {
					for (int n = 0; n < 3; n++) {
						notesLabel[i][j][m][n] = new Label();
						notesLabel[i][j][m][n].setStyle("-fx-font: normal 10px 'serif' lightgray;"
								+ "-fx-padding: 5px;");
						cellPane[i][j].add(notesLabel[i][j][m][n], n, m);
						notesLabel[i][j][m][n].setMaxHeight(20);
						notesLabel[i][j][m][n].setMinHeight(20);
						notesLabel[i][j][m][n].setMaxWidth(20);
						notesLabel[i][j][m][n].setMinWidth(20);
						notesLabel[i][j][m][n].setAlignment(Pos.CENTER);
					}
				}
			}
		}
		
		// arrange right side buttons and make them look pretty
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
	}
	
	private void startGame() {
		for (int i = 0; i < 9; i++) {
			cellLabel[i][4].setStyle("-fx-background-color: #e3ecf3; -fx-font: normal bold 30px 'serif'; "
					+ "-fx-padding: 5px;");
			cellLabel[4][i].setStyle("-fx-background-color: #e3ecf3; -fx-font: normal bold 30px 'serif'; "
					+ "-fx-padding: 5px;");
		}
		for (int i = 3; i < 6; i++) {
			for (int j = 3; j < 6; j++) {
				cellLabel[i][j].setStyle("-fx-background-color: #e3ecf3; -fx-font: normal bold 30px 'serif'; "
						+ "-fx-padding: 5px;");
			}
		}
		cellLabel[4][4].setStyle("-fx-background-color: #c0dffb; -fx-font: normal bold 30px 'serif'; "
					+ "-fx-padding: 5px;");
	}
}
