package interpreter.blocks.turtleCommands;

import java.util.Deque;

import interpreter.blocks.RootBlock;

public class SetXY extends TurtleCommandTwoArg {
	public SetXY(Deque<RootBlock> queue) {
		super(queue);
	}
	
	public SetXY (RootBlock arg1, RootBlock arg2) {
		super(arg1, arg2);
	}
}
