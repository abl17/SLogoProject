package interpreter.exceptions.blocks;

public class BlockNullException extends BlockException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Returns an error with a message
	 * @param message
	 */
	public BlockNullException (String message) {
		super(message);
	}
	
	/**
	 * Returns an error extending Exception with formatted string of error and argument
	 * that caused the error.
	 * 
	 * @param err
	 * @param argument
	 */
	public BlockNullException (String err, String argument) {
		super(String.format(err, argument));
	}
}
