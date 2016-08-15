package interpreter.blocks.turtleCommands;

import java.util.Deque;

import interpreter.blocks.RootBlock;

public class Left extends TurtleCommandOneArg {
	public Left(Deque<RootBlock> queue) {
		super(queue);
	}
	
	public Left(RootBlock arg1) {
		super(arg1);
	}
}
