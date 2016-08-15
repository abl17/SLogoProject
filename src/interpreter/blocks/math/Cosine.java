package interpreter.blocks.math;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.OneArgBlock;
import interpreter.blocks.RootBlock;
import interpreter.exceptions.blocks.BlockException;

public class Cosine extends OneArgBlock implements MathBlock {

	public Cosine(Deque<RootBlock> queue) {
		super(queue);
	}

	public Cosine (RootBlock arg1) {
		super(arg1);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		double degrees = getArgument().evaluate(pm);
		double radians = Math.toRadians(degrees);
		return Math.cos(radians);
	}

	@Override
	public void print() {
		try {
			double degrees = getArgument().evaluate(null);
			double radians = Math.toRadians(degrees);
			System.out.println(Math.cos(radians));
		} catch (BlockException e) {
			System.out.println("Failed to print");
		}
	}

}
