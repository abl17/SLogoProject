package interpreter.blocks.control;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.RootBlock;
import interpreter.blocks.ThreeArgBlock;
import interpreter.exceptions.blocks.BlockException;

public class IfElse extends ThreeArgBlock {

	public IfElse(Deque<RootBlock> queue) {
		super(queue);
	}
	
	public IfElse(RootBlock arg1, RootBlock arg2, RootBlock arg3) {
		super(arg1, arg2, arg3);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		double myBool = getFirstArgument().evaluate(pm);
		if (myBool == 0) {
			return getThirdArgument().evaluate(pm);
		} else {
			return getSecondArgument().evaluate(pm);
		}
	}

	@Override
	public void print() {
		System.out.println("IfELSE");
	}

}
