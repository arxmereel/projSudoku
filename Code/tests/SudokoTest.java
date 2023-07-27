/** 
 * JUnit test cases for the Sudoko Model
 * 
 * @author Eyad Alhetairshi
 */
package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import model.*;

class SudokoTest {

	@Test
	void makeMoveTest() {
		GameManager game = new GameManager();
		game.generateSudokuForTesting();
		//Make valid but wrong move
		assertFalse(game.makeMove(2, 2, 2));
		//Make valid and right move
		assertTrue(game.makeMove(0,0,1));
		
		
	}
	@Test
	void candidatesTests() {
		GameManager game = new GameManager();
		game.generateSudokuForTesting();
		int[] cands = {1,2,3};
		game.addCandidates(1, 1, cands);
		Set<Integer> candidates = new HashSet<>();
		candidates.add(1);
		candidates.add(2);
		candidates.add(3);
		assertEquals(candidates,game.getGameBoard().getCell(1, 1).getCandidates());
		candidates.remove(3);
		game.delCandidate(1, 1, 3);
		assertEquals(candidates,game.getGameBoard().getCell(1, 1).getCandidates());
		game.getCellCands(0, 0);
		game.getCellVal(0, 0);
		candidates.remove(1);
		candidates.remove(2);
		game.printAnswer();
		game.printGameBoard();
		game.clearCandidates(1, 1);
		assertEquals(candidates,game.getGameBoard().getCell(1, 1).getCandidates());
		assertFalse(game.isGameOver());
		
	}
	
	@Test
	void boardManipulationTests() {
		GameManager game = new GameManager();
		game.generateSudoku();
		assertFalse(game.getGameBoard().isComplete());
		game.getGameBoard().getSection(1,2);
		assertTrue(game.getGameBoard().isValid());
		Board board = game.getanswerBoard();
		assertTrue(board.isComplete());
		
	}
	
	@Test
	void sectionAndCellManipulationTests() {
		GameManager game = new GameManager();
		game.generateSudoku();
		Section sec = new Section();
		Cell cell = new Cell(1);
		sec.setCell(0, cell);
		sec.missingVals();
		assertEquals(cell, sec.getCell(0));
		cell.setEditable(true);
		assertTrue(cell.isEditable());
		cell.setEditable(false);
		cell.setValue(0);
		
	}
		
		

}
