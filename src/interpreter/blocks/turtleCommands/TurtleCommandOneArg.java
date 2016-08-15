package interpreter.blocks.turtleCommands;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.OneArgBlock;
import interpreter.blocks.RootBlock;
import interpreter.exceptions.blocks.BlockException;

public abstract class TurtleCommandOneArg extends OneArgBlock implements TurtleCommandBlock {
	
	public TurtleCommandOneArg(Deque<RootBlock> queue) {
		super(queue);
	}
	
	public TurtleCommandOneArg (RootBlock arg1) {
		super(arg1);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		String myAction = TurtleCommandFactory.getAction(this);
		return pm.getTurtleBundle().evaluate(myAction, getArgument().evaluate(pm));
	}

	@Override
	public void print() {
		try {
			String myAction = TurtleCommandFactory.getAction(this);
			Double myValue = getArgument().evaluate(null);
			System.out.println(myAction + myValue);
		} catch (BlockException e) {
			System.out.println("Failed to print");
		}
	}
}
