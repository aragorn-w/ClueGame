/*
 * Class: BadConfigFormatException
 * 
 * Purpose: 
 * 
 * Responsibilities: 
 * 
 * Authors: Aragorn Wang, Anya Streit
 */

package clueGame;

public class BadConfigFormatException extends Exception {
    public BadConfigFormatException() {
        super("Either the setup or layout config file is invalid.");
    }

    public BadConfigFormatException(String message) {
        super(message);
    }
}