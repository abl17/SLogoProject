package interpreter.blocks.logical;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.OneArgBlock;
import interpreter.blocks.RootBlock;
import interpreter.exceptions.blocks.BlockException;

public class Not extends OneArgBlock implements BooleanBlock {
	public Not(Deque<RootBlock> queue) {
		super(queue);
	}

	public Not (RootBlock arg1) {
		super(arg1);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		if (getArgument().evaluate(pm) == FALSE) {
			return TRUE;
		} else {
			return FALSE;
		}
	}

	@Override
	public void print() {
		try {
			if (getArgument().evaluate(null) == FALSE) {
				System.out.println(TRUE);
			} else {
				System.out.println(FALSE);
			}
		} catch (BlockException e) {
			System.out.println("Failed to print");
		}
	}
}
