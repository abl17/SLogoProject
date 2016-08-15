package model.exceptions;

public class VariableException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Returns an error with a message
	 * @param message
	 */
	public VariableException (String message) {
		super(message);
	}
}
