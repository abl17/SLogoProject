package interpreter.blocks.basic;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.RootBlock;
import interpreter.blocks.TokenBlock;
import interpreter.blocks.ZeroArgBlock;
import interpreter.exceptions.blocks.BlockArgumentsException;
import interpreter.exceptions.blocks.BlockNumberException;
import model.exceptions.NumberException;

/**
 * This class represents a ZeroArg block that takes in 0 arguments, and has
 * a constant double value associated with it.
 * 
 * @author Austin
 *
 */

public class ConstantBlock extends ZeroArgBlock {
	// This is the associated constant
	private double myConstant;
	
	/**
	 * ConstantBlock formulated from a queue
	 * 
	 * @param queue
	 * @throws BlockNumberException
	 * @throws NumberException 
	 */
	public ConstantBlock (Deque<RootBlock> queue) throws BlockNumberException, NumberException {
		this(((TokenBlock) queue.pop()).getToken());
	}
	
	/**
	 * ConstantBlock formulated from a String
	 * 
	 * @param constant
	 * @throws BlockNumberException
	 */
	public ConstantBlock (String constant) throws BlockNumberException, NumberException {
		try {
			myConstant = Double.parseDouble(constant);
		} catch (NumberFormatException | StackOverflowError e) {
			throw new BlockNumberException("Incorrect format", constant);
		}
		
		if (myConstant > 1e50) {
			throw new NumberException("Too large of a number");
		}
		
	}
	
	/**
	 * ConstantBlock formulated from a actual double constant
	 * @param constant
	 */
	public ConstantBlock (double constant) {
		myConstant = constant;
	}
	
	@Override
	public double evaluate(IProjectManager pm) throws BlockArgumentsException {
		return myConstant;
	}

	/**
	 * Prints out associated constant
	 */
	@Override
	public void print() {
		System.out.println(myConstant);
	}

}
