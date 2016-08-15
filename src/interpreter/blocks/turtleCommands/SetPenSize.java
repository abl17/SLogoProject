package interpreter.blocks.turtleCommands;

import java.util.Deque;

import interpreter.blocks.RootBlock;

public class SetPenSize extends TurtleCommandOneArg {

	public SetPenSize(Deque<RootBlock> queue) {
		super(queue);
	}
	
	public SetPenSize(RootBlock arg1) {
		super(arg1);
	}

}
