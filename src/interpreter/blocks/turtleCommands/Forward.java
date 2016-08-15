package interpreter.blocks.turtleCommands;

import java.util.Deque;

import interpreter.blocks.RootBlock;

public class Forward extends TurtleCommandOneArg {
	public Forward(Deque<RootBlock> queue) {
		super(queue);
	}
	
	public Forward (RootBlock arg1) {
		super(arg1);
	}
}
