package interpreter.blocks.turtleCommands;

import java.util.Deque;

import interpreter.blocks.RootBlock;

public class Right extends TurtleCommandOneArg {
	public Right(Deque<RootBlock> queue) {
		super(queue);
	}
	
	public Right (RootBlock arg1) {
		super(arg1);
	}
}
