package interpreter.blocks.turtleQueries;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.RootBlock;
import interpreter.blocks.turtleCommands.TurtleCommandBlock;
import interpreter.blocks.turtleCommands.TurtleCommandFactory;
import interpreter.exceptions.blocks.BlockException;

public class GetShape extends TurtleQueryBlock implements TurtleCommandBlock {

	public GetShape(Deque<RootBlock> queue) {
		super(queue);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		String myString = TurtleCommandFactory.getAction(this);
		return pm.getTurtleBundle().evaluate(myString);
	}

	@Override
	public void print() {
		System.out.println("Getting shape");
	}

}
