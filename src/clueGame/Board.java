/*
 * Class: Board
 * 
 * Purpose: 
 * 
 * Responsibilities: 
 * 
 * Authors: Aragorn Wang, Anya Streit
 */

package clueGame;

import java.util.ArrayList;
import java.util.Map;

public class Board {
	private static Board theInstance = new Board();

	private int numRows;
	private int numCols;

	private ArrayList<ArrayList<BoardCell>> grid;

	private String layoutConfigFile;
	private String setupConfigFile;

	private Map<Character, Room> roomMap;

	public Board() {
		super();

		
	}

	public void initialize() {

	}

	public void loadLayoutConfig() {

	}

	public void loadSetupConfig() {

	}

	public static Board getInstance() {
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