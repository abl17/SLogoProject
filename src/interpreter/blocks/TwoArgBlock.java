package interpreter.blocks;

import java.util.Deque;

public abstract class TwoArgBlock extends ArgumentBlock {
	private static final int FIRST_ARGUMENT = 0;
	private static final int SECOND_ARGUMENT = 1;
	
	/**
	 * Adds arguments from a queue structure to the map
	 * 
	 * @param queue
	 */
	public TwoArgBlock (Deque<RootBlock> queue) {
		this(queue.pop(), queue.pop());
	}
	
	/**
	 * Adds inputted arguments to map. A constructor
	 * 
	 * @param arg1
	 * @param arg2
	 */
	public TwoArgBlock (RootBlock arg1, RootBlock arg2) {
		add(FIRST_ARGUMENT, arg1);
		add(SECOND_ARGUMENT, arg2);
	}
	
	/**
	 * @return first inputted argument
	 */
	public RootBlock getFirstArgument () {
		return get(FIRST_ARGUMENT);
	}
	
	/**
	 * 
	 * @return second inputted argument
	 */
	public RootBlock getSecondArgument () {
		return get(SECOND_ARGUMENT);
	}
}
