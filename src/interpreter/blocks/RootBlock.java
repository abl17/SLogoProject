package interpreter.blocks;

import interpreter.IProjectManager;
import interpreter.exceptions.blocks.BlockArgumentsException;
import interpreter.exceptions.blocks.BlockException;

/**
 * The root of the abstract syntax tree. Each tree represents an expression.
 * This class is the father of all the leaf blocks
 * 
 * @author Austin
 *
 */

public abstract class RootBlock {
	
	/**
	 * This is an abstract block, which evaluates an expression
	 * 
	 * @param pm The project manager, or the interface where commands are run
	 * @return According to Duvall, all commands return a double
	 * @throws BlockArgumentsException
	 */
	public abstract double evaluate (IProjectManager pm) throws BlockException;
	
	/**
	 * Prints out what is in the block. Mostly for debugging
	 */
	public abstract void print ();
}
