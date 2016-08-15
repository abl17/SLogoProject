package interpreter.blocks.math;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.RootBlock;
import interpreter.blocks.TwoArgBlock;
import interpreter.exceptions.blocks.BlockException;

public class ArcTangent2 extends TwoArgBlock implements MathBlock {

	public ArcTangent2(Deque<RootBlock> queue) {
		super(queue);
	}
	
	public ArcTangent2 (RootBlock arg1, RootBlock arg2) {
		super(arg1, arg2);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		return Math.toDegrees(Math.atan2(getFirstArgument().evaluate(pm), getSecondArgument().evaluate(pm)));
	}

	@Override
	public void print() {
		try {
			System.out.println(Math.toDegrees
					(Math.atan2(getFirstArgument().evaluate(null), getSecondArgument().evaluate(null))));
		} catch (BlockException e) {
			System.out.println("Failed to print");
		}
	}

}
