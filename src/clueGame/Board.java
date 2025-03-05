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
import java.util.Map;
import java.util.Scanner;

public class Board {
	private static Board theInstance;

	private int numRows;
	private int numCols;

	private ArrayList<ArrayList<BoardCell>> grid;

	private String layoutConfigFile;
	private String setupConfigFile;

	private Map<Character, Room> roomMap;

	public Board() {
		super();
		theInstance = this;
	}

	public void initialize() {
		try {
			loadSetupConfig();
			loadLayoutConfig();
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
				int columnIndex = 0;
				for (String marker: splitLine) {
					char initial = marker.charAt(0);
					// if room is null, then the initial is not a valid room
					Room room = roomMap.get(initial);
					if (room == null) {
						// if initial is not a valid room, then the cell is invalid
						throw new Exception("Invalid room " + initial + " in layout config file.");
					}
					
					BoardCell cell = new BoardCell(rowIndex, columnIndex, initial);
					
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
					columnIndex++;
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
}