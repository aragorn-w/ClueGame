/*
 * Enum: DoorDirection
 * 
 * Purpose: 
 * 
 * Responsibilities: 
 * 
 * Authors: Aragorn Wang, Anya Streit
 */

package clueGame;

public enum DoorDirection {
	UP('^'), DOWN('v'), LEFT('<'), RIGHT('>');

	private final char value;

	DoorDirection(char value) {
		this.value = value;
	}

	public char getValue() {
		return value;
	}

	public static DoorDirection getDirection(char value) throws BadConfigFormatException {
		for (DoorDirection direction : DoorDirection.values()) {
			if (direction.value == value) {
				return direction;
			}
		}

		throw new BadConfigFormatException("Invalid door direction");
	}
}