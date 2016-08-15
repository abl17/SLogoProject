package interpreter.blocks.math;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.OneArgBlock;
import interpreter.blocks.RootBlock;
import interpreter.exceptions.blocks.BlockException;

public class LogNatural extends OneArgBlock implements MathBlock {
	public LogNatural(Deque<RootBlock> queue) {
		super(queue);
	}

	public LogNatural(RootBlock arg1) {
		super(arg1);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		double result = Math.log(getArgument().evaluate(pm));

		if (result == UNDEFINED) {
			// Default to 0!
			// TODO: Perhaps throw a NaN exception
			return UNDEFINED;
		} else {
			return result;
		}
	}

	@Override
	public void print() {
		try {
			double resultInRadians = Math.atan(getArgument().evaluate(null));
			System.out.println(Math.toDegrees(resultInRadians));
		} catch (BlockException e) {
			System.out.println("Failed to print");
		}
	}

}
