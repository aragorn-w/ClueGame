/*
 * Class: BadConfigFormatException
 * 
 * Purpose: The BadConfigFormatException class is an exception that is thrown when the setup or layout config file is invalid.
 * 
 * Responsibilities: This custom checked exception is responsible for throwing an exception when the setup or layout config file is invalid.
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