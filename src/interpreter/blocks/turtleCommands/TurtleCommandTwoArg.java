package interpreter.blocks.turtleCommands;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.RootBlock;
import interpreter.blocks.TwoArgBlock;
import interpreter.exceptions.blocks.BlockException;

public abstract class TurtleCommandTwoArg extends TwoArgBlock implements TurtleCommandBlock {
	public TurtleCommandTwoArg(Deque<RootBlock> queue) {
		super(queue);
	}
	
	public TurtleCommandTwoArg (RootBlock arg1, RootBlock arg2) {
		super(arg1, arg2);
	}
	
	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		String myAction = TurtleCommandFactory.getAction(this);
		return pm.getTurtleBundle().evaluate(myAction, getFirstArgument().evaluate(pm), 
				getSecondArgument().evaluate(pm));
	}

	@Override
	public void print() {
		try {
			String myAction = TurtleCommandFactory.getAction(this);
			Double myValue1 = getFirstArgument().evaluate(null);
			Double myValue2 = getSecondArgument().evaluate(null);
			System.out.println(myAction + " " + myValue1 + " " + myValue2);
		} catch (BlockException e) {
			System.out.println("Failed to print");
		}
	}
}
