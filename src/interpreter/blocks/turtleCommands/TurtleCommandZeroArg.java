package interpreter.blocks.turtleCommands;

import java.util.Deque;

import interpreter.blocks.RootBlock;
import interpreter.blocks.ZeroArgBlock;

public abstract class TurtleCommandZeroArg extends ZeroArgBlock implements TurtleCommandBlock {
	public TurtleCommandZeroArg (Deque<RootBlock> queue) {
		if (queue != null) {
			queue.pop();
		}
	}
}
