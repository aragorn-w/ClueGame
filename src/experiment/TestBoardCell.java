package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	private int row;
	private int col;
	
	private Set<TestBoardCell> adjCells = new HashSet<>();
	
	private boolean isRoom = false;
	private boolean occupied = false;
	
	public TestBoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}
	
	public void addAdjacency(TestBoardCell cell) {
		adjCells.add(cell);
	}
	
	public Set<TestBoardCell> getAdjList() {
		return adjCells;
	}
	
	public void setRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	
	public boolean isRoom() {
		return isRoom;
	}
	
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	
	public boolean getOccupied() {
		return occupied;
	}
}
