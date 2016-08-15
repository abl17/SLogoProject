package interpreter.exceptions.blocks;

/**
 * This error represents a formatting error with numbers, i.e. when converting string to double
 * or something that has to do with blocks
 * 
 * @author Austin
 *
 */

public class BlockNumberException extends BlockException {

	/**
	 * Default Serial Version ID
	 */
	private static final long serialVersionUID = 1L;

	public BlockNumberException(String err, String argument) {
		super(err, argument);
	}

}
