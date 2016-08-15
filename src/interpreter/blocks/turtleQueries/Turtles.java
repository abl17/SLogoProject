package interpreter.blocks.turtleQueries;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.RootBlock;
import interpreter.blocks.turtleCommands.TurtleCommandBlock;
import interpreter.exceptions.blocks.BlockException;

public class Turtles extends TurtleQueryBlock implements TurtleCommandBlock {
	public Turtles(Deque<RootBlock> queue) {
		super(queue);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		return pm.getTurtleBundle().getTurtleReserves().size();
	}

	@Override
	public void print() {
		System.out.println("Getting Number of Turtles");
	}
}
