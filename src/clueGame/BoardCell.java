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

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	private final int row;
	private final int col;
	
	private final char initial;

	private DoorDirection doorDirection;

	private boolean roomLabel;
	private boolean roomCenter;

	private char secretPassage;

	private final Set<BoardCell> adjList;

	public BoardCell(int row, int col, char initial) throws BadConfigFormatException {
		super();
		this.row = row;
		this.col = col;
		this.initial = initial;

		adjList = new HashSet<>();
	}

	public char getInitial() {
		return initial;
	}

	public boolean isDoorway() {
		return doorDirection != null;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}

	public boolean isLabel() {
		return roomLabel;
	}

	public void setIsLabel(boolean isRoomLabel) {
		roomLabel = isRoomLabel;
	}

	public boolean isRoomCenter() {
		return roomCenter;
	}

	public void setIsRoomCenter(boolean isRoomCenter) {
		roomCenter = isRoomCenter;
	}

	public char getSecretPassage() {
		return secretPassage;
	}

	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}

	public void addAdj(BoardCell cell) {
		adjList.add(cell);
	}
}