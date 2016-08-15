package interpreter.blocks;

import java.util.HashMap;
import java.util.Map;

/**
 * This block is the father of all syntax blocks that require parameters
 * OneArgBlock
 * TwoArgBlock
 * ThreeArgBlock
 * FourArgBlock
 * .
 * .
 * .
 * 
 * @author Austin
 *
 */

public abstract class ArgumentBlock extends RootBlock {
	private Map <Integer, RootBlock> myBlocks = new HashMap<Integer, RootBlock>();
	
	/**
	 * Do we want this?
	 * Add a block to a particular index
	 * 
	 * @param index
	 * @param block
	 */
	public void add (int index, RootBlock block) {
		myBlocks.put(index, block);
	}
	
	/**
	 * Get the RootBlock residing at a particular index
	 * 
	 * @param index
	 * @return specific block at an index
	 */
	public RootBlock get (int index) {
		return myBlocks.get(index);
	}
	
	/**
	 * Return size of the Argument Block. 1 for OneArgBlock, ...
	 * 
	 * @return size
	 */
	public int size () {
		return myBlocks.size();
	}
}
