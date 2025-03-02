/*
 * Class: BoardCell
 * 
 * Purpose: 
 * 
 * Responsibilities: 
 * 
 * Authors: Aragorn Wang, Anya Streit
 */

package clueGame;

import java.util.Set;

public class BoardCell {
	private int row;
	private int col;
	
	private char initial;

	private DoorDirection doorDirection;

	private boolean roomLabel;
	private boolean roomCenter;

	private char secretPassage;

	private Set<BoardCell> adjList;

	public BoardCell() {
		super();


	}

	public char getInitial() {
		return initial;
	}

	public boolean isDoorway() {
		return doorDirection != DoorDirection.NONE;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public boolean isLabel() {
		return roomLabel;
	}

	public boolean isRoomCenter() {
		return roomCenter;
	}

	public char getSecretPassage() {
		return secretPassage;
	}

	public void addAdj(BoardCell cell) {
		
	}
}