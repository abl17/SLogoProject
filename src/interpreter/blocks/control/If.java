package interpreter.blocks.control;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.RootBlock;
import interpreter.blocks.TwoArgBlock;
import interpreter.exceptions.blocks.BlockException;

public class If extends TwoArgBlock {

	public If(Deque<RootBlock> queue) {
		super(queue);
	}
	
	public If(RootBlock arg1, RootBlock arg2) {
		super(arg1, arg2);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		double myBool = getFirstArgument().evaluate(pm);
		if (myBool == 0) {
			return 0;
		} else {
			return getSecondArgument().evaluate(pm);
		}
	}

	@Override
	public void print() {
		System.out.println("If statement");
	}
	
}
