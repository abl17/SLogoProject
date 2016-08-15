package interpreter.blocks.turtleCommands;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.RootBlock;
import interpreter.exceptions.blocks.BlockArgumentsException;

public class Home extends TurtleCommandZeroArg {
	public Home(Deque<RootBlock> queue) {
		super(queue);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockArgumentsException {
		String myAction = TurtleCommandFactory.getAction(this);
		return pm.getTurtleBundle().evaluate(myAction);
	}

	@Override
	public void print() {
		System.out.println(TurtleCommandFactory.getAction(this) + " " + "DISTANCE");
	}
}
