package experiment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	private ArrayList<ArrayList<TestBoardCell>> board = new ArrayList<ArrayList<TestBoardCell>>();
	private Set<TestBoardCell> targets = new HashSet<>();
	
	private int NUM_ROWS = 4;
    private int NUM_COLS = 4;
	
	public TestBoard() {
		super();

	    for (int i = 0; i < NUM_ROWS; i++) {
	        ArrayList<TestBoardCell> row = new ArrayList<>();
	        for (int j = 0; j < NUM_COLS; j++) {
	            row.add(new TestBoardCell(i, j));  // Assuming TestBoardCell has a constructor
	        }
	        board.add(row);
	    }
	}
	
	public void calcTargets(TestBoardCell startCell, int pathLength) {
		
	}
	
	public TestBoardCell getCell(int row, int col) {
		return board.get(row).get(col);
	}
	
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
}
