package interpreter.blocks;

/**
 * Commands that take 0 arguments, like constants 
 * 
 * @author Austin
 *
 */

public abstract class ZeroArgBlock extends RootBlock {
	private static final int INPUT_NUMBER = 0;
	
	public int getInputSize () {
		return INPUT_NUMBER;
	}
}
