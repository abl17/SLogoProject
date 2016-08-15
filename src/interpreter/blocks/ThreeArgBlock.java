package interpreter.blocks;

import java.util.Deque;

public abstract class ThreeArgBlock extends ArgumentBlock {
	private static final int FIRST_ARGUMENT = 0;
	private static final int SECOND_ARGUMENT = 1;
	private static final int THIRD_ARGUMENT = 2;
	
	public ThreeArgBlock (Deque<RootBlock> queue) {
		this(queue.pop(), queue.pop(), queue.pop());
	}
	
	public ThreeArgBlock (RootBlock arg1, RootBlock arg2, RootBlock arg3) {
		add(FIRST_ARGUMENT, arg1);
		add(SECOND_ARGUMENT, arg2);
		add(THIRD_ARGUMENT, arg3);
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
	
	/**
	 * 
	 * @return third inputted argument
	 */
	public RootBlock getThirdArgument () {
		return get(THIRD_ARGUMENT);
	}
}
