/*
 * Class: BoardCell
 * 
 * Purpose: The BoardCell class represents a single cell on the game board. It contains information about the cell's
 * 
 * Responsibilities: The BoardCell class is responsible for storing information about a single cell on the game board, including its row and column, the initial of the room it is in, whether it is a doorway, the direction of the doorway, whether it is a room label, whether it is a room center, the secret passage in the room, and the cells adjacent to it. It is also responsible for providing access to this information.
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
	
	private Room room;
	private boolean roomLabel;
	private boolean roomCenter;
	private char secretPassage;

	private final Set<BoardCell> adjList;
	
	private boolean isRoom = false;
	private boolean isOccupied = false;

	public BoardCell(int row, int col, char initial) throws BadConfigFormatException {
		super();
		this.row = row;
		this.col = col;
		this.initial = initial;
		this.adjList = new HashSet<>();
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public char getInitial() {
		return initial;
	}
	
	public Room getRoom() {
		return room;
	}
	
	public void setRoom(Room room) {
		this.room = room;
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
	
	public boolean isSecretPassage() {
		return secretPassage != 0;
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
	
	public Set<BoardCell> getAdjList() {
		return adjList;
	}
	
	public boolean isRoom() {
		return isRoom;
	}
	
	public void setIsRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	
	public boolean isOccupied() {
		return isOccupied;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
}