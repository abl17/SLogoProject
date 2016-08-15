package interpreter.blocks.math;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.RootBlock;
import interpreter.blocks.TwoArgBlock;
import interpreter.exceptions.blocks.BlockException;

public class Remainder extends TwoArgBlock implements MathBlock {

	public Remainder(Deque<RootBlock> queue) {
		super(queue);
	}
	
	public Remainder (RootBlock arg1, RootBlock arg2) {
		super(arg1, arg2);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		if (getSecondArgument().evaluate(pm) == ZERO) {
			System.out.println("Divide by 0!");
		}
		return getFirstArgument().evaluate(pm) % getSecondArgument().evaluate(pm);
	}

	@Override
	public void print() {
		try {
			System.out.println(getFirstArgument().evaluate(null) % getFirstArgument().evaluate(null));
		} catch (BlockException e) {
			System.out.println("Failed to print");
		}
	}

}
