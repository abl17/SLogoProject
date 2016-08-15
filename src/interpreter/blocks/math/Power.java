package interpreter.blocks.math;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.RootBlock;
import interpreter.blocks.TwoArgBlock;
import interpreter.exceptions.blocks.BlockException;

public class Power extends TwoArgBlock implements MathBlock {

	public Power(Deque<RootBlock> queue) {
		super(queue);
	}

	public Power (RootBlock arg1, RootBlock arg2) {
		super(arg1, arg2);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		return Math.pow(getFirstArgument().evaluate(pm), getSecondArgument().evaluate(pm));
	}

	@Override
	public void print() {
		try {
			Double result = Math.pow(getFirstArgument().evaluate(null), getSecondArgument().evaluate(null));
			System.out.println(result);
		} catch (BlockException e) {
			System.out.println("Failed to print");
		}
	}

}
