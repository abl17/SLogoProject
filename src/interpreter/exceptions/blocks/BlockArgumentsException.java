package interpreter.exceptions.blocks;

/**
 * This describes an error in the number of arguments entered for a block
 * Example: 1 argument of addition --> what?
 * 
 * @author Austin
 *
 */

public class BlockArgumentsException extends BlockException {

	/**
	 * Default Serial Version ID
	 */
	private static final long serialVersionUID = 1L;
	private static final String ERROR = "There is an error in the arguments";
	private static final String TOKEN = "The cause of the error is from an invalid input command";
	
	/**
	 * Returns an exception object based on error msg and token that caused it
	 * 
	 * @param err
	 * @param token
	 */
	public BlockArgumentsException (String err, String token) {
		super(String.format(err, token));
	}
	
	public BlockArgumentsException () {
		super(String.format(ERROR, TOKEN));
	}

}
