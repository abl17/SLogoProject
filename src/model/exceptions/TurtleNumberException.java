package model.exceptions;

public class TurtleNumberException extends TurtleException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Returns an error with a message
	 * @param message
	 */
	public TurtleNumberException (String message) {
		super(message);
	}
	
}
