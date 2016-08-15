package interpreter.exceptions.blocks;

/**
 * This class describes the parent class for all the errors related to the blocks
 * of the abstract syntax tree
 * 
 * @author Austin
 *
 */

public abstract class BlockException extends Exception {

	/**
	 * Default Serial Version ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Returns an error with a message
	 * @param message
	 */
	public BlockException (String message) {
		super(message);
	}
	
	/**
	 * Returns an error extending Exception with formatted string of error and argument
	 * that caused the error.
	 * 
	 * @param err
	 * @param argument
	 */
	public BlockException (String err, String argument) {
		super(String.format(err, argument));
	}

}
