package experiment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	private ArrayList<ArrayList<TestBoardCell>> board = new ArrayList<ArrayList<TestBoardCell>>();
	private Set<TestBoardCell> targets = new HashSet<>();
	
	public TestBoard() {
		super();
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
