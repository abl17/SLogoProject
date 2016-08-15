package interpreter.blocks.control;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.OneArgBlock;
import interpreter.blocks.RootBlock;
import interpreter.blocks.basic.ListStartBlock;
import interpreter.exceptions.blocks.BlockArgumentsException;
import interpreter.exceptions.blocks.BlockException;

public class Tell extends OneArgBlock {

	public Tell(Deque<RootBlock> queue) {
		super(queue);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		int result = 0;
		pm.getTurtleBundle().recall(); //Removes active turtles from line of duty

		try {
			for (RootBlock rb : ((ListStartBlock) getArgument()).getList()) {
				result = (int) rb.evaluate(pm);
				pm.getTurtleBundle().deploy(result);
			}
		} catch (ClassCastException e) {
			throw new BlockArgumentsException("Incorrect format of command", "args?");
		}

		return result;
	}

	@Override
	public void print() {
		System.out.println("Telling which turtles");
	}

}
