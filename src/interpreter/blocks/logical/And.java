package interpreter.blocks.logical;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.RootBlock;
import interpreter.blocks.TwoArgBlock;
import interpreter.exceptions.blocks.BlockException;

public class And extends TwoArgBlock implements BooleanBlock {
	public And(Deque<RootBlock> queue) {
		super(queue);
	}

	public And (RootBlock arg1, RootBlock arg2) {
		super(arg1, arg2);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		if (getFirstArgument().evaluate(pm) != FALSE && 
				getSecondArgument().evaluate(pm) != FALSE) {
			return TRUE;
		} else {
			return FALSE;
		}
	}

	@Override
	public void print() {
		try {
			if (getFirstArgument().evaluate(null) != FALSE &&
					getSecondArgument().evaluate(null) != FALSE) {
				System.out.println(TRUE);
			} else {
				System.out.println(FALSE);
			}
		} catch (BlockException e) {
			System.out.println("Failed to print");
		}
	}
}
