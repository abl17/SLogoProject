package interpreter.blocks.turtleCommands;

import java.util.Deque;

import interpreter.blocks.RootBlock;

public class SetHeading extends TurtleCommandOneArg {
	public SetHeading(Deque<RootBlock> queue) {
		super(queue);
	}
	
	public SetHeading(RootBlock arg1) {
		super(arg1);
	}
}
