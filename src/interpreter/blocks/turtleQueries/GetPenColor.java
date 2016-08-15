package interpreter.blocks.turtleQueries;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.RootBlock;
import interpreter.blocks.turtleCommands.TurtleCommandBlock;
import interpreter.blocks.turtleCommands.TurtleCommandFactory;
import interpreter.exceptions.blocks.BlockException;

public class GetPenColor extends TurtleQueryBlock implements TurtleCommandBlock {
	public GetPenColor(Deque<RootBlock> queue) {
		super(queue);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		String myString = TurtleCommandFactory.getAction(this);
		return pm.getTurtleBundle().evaluate(myString);
	}

	@Override
	public void print() {
		System.out.println("Getting pen color");
	}

}
