package interpreter.blocks.turtleCommands;

import java.util.Deque;

import interpreter.blocks.RootBlock;

public class Towards extends TurtleCommandTwoArg {

	public Towards(Deque<RootBlock> queue) {
		super(queue);
	}
	
	public Towards (RootBlock arg1, RootBlock arg2) {
		super(arg1, arg2);
	}

}
