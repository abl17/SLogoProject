package interpreter.blocks.turtleCommands;

import java.util.Deque;

import interpreter.blocks.RootBlock;

public class Back extends TurtleCommandOneArg {
	public Back(Deque<RootBlock> queue) {
		super(queue);
	}

	public Back (RootBlock arg1) {
		super(arg1);
	}
}
