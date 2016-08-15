package interpreter.blocks.turtleQueries;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.RootBlock;
import interpreter.blocks.turtleCommands.TurtleCommandBlock;
import interpreter.blocks.turtleCommands.TurtleCommandFactory;
import interpreter.exceptions.blocks.BlockArgumentsException;

public class XCor extends TurtleQueryBlock implements TurtleCommandBlock {

	public XCor(Deque<RootBlock> queue) {
		super(queue);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockArgumentsException {
		String myString = TurtleCommandFactory.getAction(this);
		return pm.getTurtleBundle().evaluate(myString);
		// TODO: Determine if:
		// return pm.getTurtleBundle().evaluate(myString).getX();
		// is a better idea
	}

	@Override
	public void print() {
		String myString = TurtleCommandFactory.getAction(this);
		System.out.println(myString);
	}

}
