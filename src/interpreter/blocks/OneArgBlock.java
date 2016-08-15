package interpreter.blocks;

import java.util.Deque;

public abstract class OneArgBlock extends ArgumentBlock {
	private static final int ARGUMENT = 0;
	
	/**
	 * Adds arguments from a queue structure to the map
	 * 
	 * @param queue
	 */
	public OneArgBlock (Deque<RootBlock> queue) {
		this(queue.pop());
	}
	
	/**
	 * Adds inputted arguments to map. A constructor
	 * 
	 * @param arg1
	 */
	public OneArgBlock (RootBlock arg1) {
		add(ARGUMENT, arg1);
	}
	
	public RootBlock getArgument () {
		return get(ARGUMENT);
	}
}
