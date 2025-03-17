/*
 * Class: Board
 * 
 * Purpose: The Board class is a singleton that represents the game board.
 * 
 * Responsibilities: The Board class is responsible for loading the layout and setup config files, initializing the board, and storing the grid of cells and room map.
 * 
 * Authors: Aragorn Wang, Anya Streit
 */

package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	private static Board theInstance;

	private int numRows;
	private int numCols;

	private ArrayList<ArrayList<BoardCell>> grid;

	private String layoutConfigFile;
	private String setupConfigFile;

	private Map<Character, Room> roomMap;
	
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;

	public Board() {
		super();
		theInstance = this;
	}

	public void initialize() {
		try {
			loadSetupConfig();
			loadLayoutConfig();
			calcAdjLists();
		} catch (BadConfigFormatException | FileNotFoundException exception) {
			exception.printStackTrace();
		}
	}

	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException {
		roomMap = new HashMap<>();

		File setupFile = new File(setupConfigFile);
		Scanner scanner = new Scanner(setupFile);

		while (scanner.hasNextLine()) {
			String splitLine = scanner.nextLine();
			if (splitLine.substring(0, 2).equals("//")) {
				continue;
			}

			try {
				String[] markerInfo = splitLine.split(", ");
				String roomType = markerInfo[0];
				String roomLabel = markerInfo[1];
				char initial = markerInfo[2].charAt(0);

				if (roomType.equals("Room") || roomType.equals("Space")) {
					roomMap.put(initial, new Room(roomLabel));
				} else {
					throw new Exception("Invalid room type \"" + roomType + "\" in setup config file.");
				}
			} catch (Exception exception) {
				scanner.close();
				throw new BadConfigFormatException(exception.getMessage());
			}
		}

		scanner.close();
	}

	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {
		grid = new ArrayList<>();

		File layoutFile = new File(layoutConfigFile);
		Scanner scanner = new Scanner(layoutFile);

		int rowIndex = 0;
		int oldNumCols = -1;
		while (scanner.hasNextLine()) {
			String[] splitLine = scanner.nextLine().split(",");
			numCols = splitLine.length;
			
			ArrayList<BoardCell> row = new ArrayList<>();
			
			try {
				int colIndex = 0;
				for (String marker: splitLine) {
					char initial = marker.charAt(0);
					// if room is null, then the initial is not a valid room
					Room room = roomMap.get(initial);
					if (room == null) {
						// if initial is not a valid room, then the cell is invalid
						throw new Exception("Invalid room " + initial + " in layout config file.");
					}
					
					BoardCell cell = new BoardCell(rowIndex, colIndex, initial);
					cell.setRoom(room);
					
					if (marker.length() == 2) {
						char special = marker.charAt(1);
						switch (special) {
							case '#' -> {
								cell.setIsLabel(true);
								room.setLabelCell(cell);
							}
							case '*' -> {
								cell.setIsRoomCenter(true);
								room.setCenterCell(cell);
							}
							case '^' -> cell.setDoorDirection(DoorDirection.UP);
							case '>' -> cell.setDoorDirection(DoorDirection.RIGHT);
							case 'v' -> cell.setDoorDirection(DoorDirection.DOWN);
							case '<' -> cell.setDoorDirection(DoorDirection.LEFT);
							default -> {
								if (roomMap.containsKey(special)) {
									cell.setSecretPassage(special);
								} else {
									// if special is not a valid room, then the cell is invalid
									throw new Exception("Invalid cell " + marker + " in layout config file.");
								}
							}
						}
					}
					
					row.add(cell);
					colIndex++;
				}

				if (oldNumCols != -1 && oldNumCols != numCols) {
					// if the number of columns is inconsistent, then the layout is invalid
					throw new Exception("Inconsistent number of columns in layout config file found at row " + rowIndex + ".");
				}
				oldNumCols = numCols;
			} catch (Exception exception) {
				scanner.close();
				throw new BadConfigFormatException(exception.getMessage());
			}

			grid.add(row);
			rowIndex++;
		}

		numRows = rowIndex;
		scanner.close();
	}
	
	private void calcAdjLists() {
		for (int rowIndex = 0; rowIndex < grid.size(); rowIndex++) {
			ArrayList<BoardCell> row = grid.get(rowIndex);
			
			for (int colIndex = 0; colIndex < row.size(); colIndex++) {
				BoardCell cell = row.get(colIndex);
				
				if (cell.isRoomCenter()) {
					Room room = cell.getRoom();
					
					for (int otherRowIndex = 0; otherRowIndex < grid.size(); otherRowIndex++) {
						ArrayList<BoardCell> otherRow = grid.get(otherRowIndex);
						for (int otherColIndex = 0; otherColIndex < otherRow.size(); otherColIndex++) {
							BoardCell otherCell = otherRow.get(otherColIndex);
							if (otherCell.isDoorway()) {
								// Room centers are adjacent to corresponding doorway walkways
								
								int doorwayRoomRowIndex = otherRowIndex;
								int doorwayRoomColIndex = otherColIndex;	
								switch (otherCell.getDoorDirection()) {
									case DoorDirection.UP -> doorwayRoomRowIndex--;
									case DoorDirection.RIGHT -> doorwayRoomColIndex--;
									case DoorDirection.DOWN -> doorwayRoomRowIndex++;
									case DoorDirection.LEFT -> doorwayRoomColIndex++;
								}
								
								Room otherRoom = grid.get(doorwayRoomRowIndex).get(doorwayRoomColIndex).getRoom(); 
								if (otherRoom.equals(room)) {
									cell.addAdj(otherCell);
								}
							} else if (otherCell.getRoom().equals(room) && otherCell.isSecretPassage()) {
								// Room centers are adjacent to secret passage room centers
								
								Room secretRoom = roomMap.get(otherCell.getSecretPassage());
								cell.addAdj(secretRoom.getCenterCell());
							}
						}
					}
				} else if (cell.getRoom().equals(Room.WALKWAY)) {
					// Walkways are adjacent to walkways and doorway walkways
					
					if (rowIndex > 0) {
						BoardCell cellBelow = grid.get(rowIndex - 1).get(colIndex);
						if (cellBelow.getRoom().equals(Room.WALKWAY)) {
							cell.addAdj(cellBelow);
						}
					}
					if (rowIndex < grid.size() - 1) {
						BoardCell cellAbove = grid.get(rowIndex + 1).get(colIndex);
						if (cellAbove.getRoom().equals(Room.WALKWAY)) {
							cell.addAdj(cellAbove);
						}
					}
					if (colIndex > 0) {
						BoardCell cellToLeft = grid.get(rowIndex).get(colIndex - 1);
						if (cellToLeft.getRoom().equals(Room.WALKWAY)) {
							cell.addAdj(cellToLeft);
						}
					}
					if (colIndex < row.size() - 1) {
						BoardCell cellToRight = grid.get(rowIndex).get(colIndex + 1);
						if (cellToRight.getRoom().equals(Room.WALKWAY)) {
							cell.addAdj(cellToRight);
						}
					}
					
					// Doorway walkways are adjacent to corresponding room center and walkways
					
					if (cell.isDoorway()) {
						int doorwayRoomRowIndex = rowIndex;
						int doorwayRoomColIndex = colIndex;	
						switch (cell.getDoorDirection()) {
							case DoorDirection.UP -> doorwayRoomRowIndex--;
							case DoorDirection.RIGHT -> doorwayRoomColIndex--;
							case DoorDirection.DOWN -> doorwayRoomRowIndex++;
							case DoorDirection.LEFT -> doorwayRoomColIndex++;
						}
						
						Room room = grid.get(doorwayRoomRowIndex).get(doorwayRoomColIndex).getRoom();
						cell.addAdj(room.getCenterCell());
					}
				}
			}
		}
	}

	public static Board getInstance() {
		if (theInstance == null) {
			theInstance = new Board();
		}
		return theInstance;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numCols;
	}

	public BoardCell getCell(int row, int col) {
		return grid.get(row).get(col);
	}

	public Room getRoom(char cellInitial) {
		return roomMap.get(cellInitial);
	}

	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial());
	}

	public void setConfigFiles(String layoutConfigFile, String setupConfigFile) {
		this.layoutConfigFile = layoutConfigFile;
		this.setupConfigFile = setupConfigFile;
	}

	public Set<BoardCell> getAdjList(int row, int col) {
		return new HashSet<BoardCell>();
	}

	public void calcTargets(BoardCell cell, int roll) {
		visited = new HashSet<>();
		targets = new HashSet<>();
		visited.add(cell);
		findAllTargets(cell, roll);
	}
	
	private void findAllTargets(BoardCell cell, int roll) {			
		for (BoardCell adjCell: cell.getAdjList()) {
			// If the cell is a room, add it to the targets and return early since the player can't move through rooms
			if (cell.isRoom()) {
				targets.add(cell);
				return;
			}
			
			if (visited.contains(adjCell)) {
				continue;
			}
			
			visited.add(adjCell);
			
			// We only add the cell to the targets if it is not occupied since the player can't move to or through an occupied cell
			if (!adjCell.isOccupied()) {
				if (roll == 1) {
					targets.add(adjCell);
				} else {
					findAllTargets(adjCell, roll - 1);
				}
			}
			
			visited.remove(adjCell);
		}
	}

	public Set<BoardCell> getTargets() {
		return new HashSet<BoardCell>();
	}
}