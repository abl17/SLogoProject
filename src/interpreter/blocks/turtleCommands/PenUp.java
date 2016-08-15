package interpreter.blocks.turtleCommands;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.RootBlock;
import interpreter.exceptions.blocks.BlockArgumentsException;

public class PenUp extends TurtleCommandZeroArg {
	public PenUp(Deque<RootBlock> queue) {
		super(queue);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockArgumentsException {
		String myAction = TurtleCommandFactory.getAction(this);
		pm.getTurtleBundle().evaluate(myAction);
		return 0;
	}

	@Override
	public void print() {
		String myAction = TurtleCommandFactory.getAction(this);
		System.out.println(myAction + " 0");
	}
}
