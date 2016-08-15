package interpreter.blocks.math;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.OneArgBlock;
import interpreter.blocks.RootBlock;
import interpreter.exceptions.blocks.BlockException;

public class Minus extends OneArgBlock implements MathBlock {

	public Minus(Deque<RootBlock> queue) {
		super(queue);
	}

	public Minus (RootBlock arg1) {
		super(arg1);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		return -1 * getArgument().evaluate(pm);
	}

	@Override
	public void print() {
		try {
			System.out.println(-1*getArgument().evaluate(null));
		} catch (BlockException e) {
			System.out.println("Failed to print");
		}
	}

}
