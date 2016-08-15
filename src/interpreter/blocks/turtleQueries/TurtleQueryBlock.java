package interpreter.blocks.turtleQueries;

import java.util.Deque;

import interpreter.blocks.RootBlock;
import interpreter.blocks.ZeroArgBlock;

public abstract class TurtleQueryBlock extends ZeroArgBlock {
	public TurtleQueryBlock (Deque<RootBlock> queue) {
		if (queue != null) {
			queue.pop();
		}
	}
}
