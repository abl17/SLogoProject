package interpreter.blocks.math;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.OneArgBlock;
import interpreter.blocks.RootBlock;
import interpreter.exceptions.blocks.BlockException;

public class ArcTangent extends OneArgBlock implements MathBlock {

	public ArcTangent(Deque<RootBlock> queue) {
		super(queue);
	}

	public ArcTangent(RootBlock arg1) {
		super(arg1);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		double resultInRadians = Math.atan(getArgument().evaluate(pm));

		return Math.toDegrees(resultInRadians);
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
