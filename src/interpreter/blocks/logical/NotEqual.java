package interpreter.blocks.logical;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.RootBlock;
import interpreter.blocks.TwoArgBlock;
import interpreter.exceptions.blocks.BlockException;

public class NotEqual extends TwoArgBlock implements BooleanBlock {
	public NotEqual(Deque<RootBlock> queue) {
		super(queue);
	}

	public NotEqual (RootBlock arg1, RootBlock arg2) {
		super(arg1, arg2);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		if (getFirstArgument().evaluate(pm) != getSecondArgument().evaluate(pm)) {
			return TRUE;
		} else {
			return FALSE;
		}
	}

	@Override
	public void print() {
		try {
			if (getFirstArgument().evaluate(null) != getSecondArgument().evaluate(null)) {
				System.out.println(TRUE);
			} else {
				System.out.println(FALSE);
			}
		} catch (BlockException e) {
			System.out.println("Failed to print");
		}
	}
}
