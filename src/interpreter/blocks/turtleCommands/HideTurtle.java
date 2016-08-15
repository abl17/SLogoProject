package interpreter.blocks.turtleCommands;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.RootBlock;
import interpreter.exceptions.blocks.BlockArgumentsException;

public class HideTurtle extends TurtleCommandZeroArg {
	public HideTurtle(Deque<RootBlock> queue) {
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
		System.out.println(TurtleCommandFactory.getAction(this) + " 0");
	}
}
