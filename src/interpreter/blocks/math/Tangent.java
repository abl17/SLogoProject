package interpreter.blocks.math;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.OneArgBlock;
import interpreter.blocks.RootBlock;
import interpreter.exceptions.blocks.BlockException;

public class Tangent extends OneArgBlock implements MathBlock {

	public Tangent(Deque<RootBlock> queue) {
		super(queue);
	}

	public Tangent(RootBlock arg1) {
		super(arg1);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockException { 
		double degrees = getArgument().evaluate(pm);
		
		// Probably a more elegant way to check for this
		if ((degrees % 90) == ZERO && ((int) degrees/90) % 2 == ONE) {
			return ZERO;
		}
		
		double radians = Math.toRadians(degrees);
		return Math.tan(radians);
	}

	@Override
	public void print() {
		try {
			double degrees = getArgument().evaluate(null);
			
			// Probably a more elegant way to check for this
			if ((degrees % 90) == ZERO && ((int) degrees/90) % 2 == ONE) {
				System.out.println(ZERO);
			}
			double radians = Math.toRadians(degrees);
			System.out.println(Math.tan(radians));
		} catch (BlockException e) {
			System.out.println("Failed to print");
		}
	}

}
