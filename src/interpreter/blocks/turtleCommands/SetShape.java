package interpreter.blocks.turtleCommands;

import java.util.Deque;

import interpreter.blocks.RootBlock;

public class SetShape extends TurtleCommandOneArg {

	public SetShape(Deque<RootBlock> queue) {
		super(queue);
	}
	
	public SetShape (RootBlock arg1) {
		super(arg1);
	}

}
