package interpreter.blocks.turtleCommands;

import java.util.Deque;

import interpreter.blocks.RootBlock;

public class SetPenColor extends TurtleCommandOneArg {

	public SetPenColor(Deque<RootBlock> queue) {
		super(queue);
	}
	
	public SetPenColor (RootBlock arg1) {
		super(arg1);
	}

}
