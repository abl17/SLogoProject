package interpreter.blocks.control;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.RootBlock;
import interpreter.blocks.TwoArgBlock;
import interpreter.exceptions.blocks.BlockException;

public class Repeat extends TwoArgBlock {

	public Repeat(Deque<RootBlock> queue) {
		super(queue);
	}
	
	public Repeat (RootBlock arg1, RootBlock arg2) {
		super(arg1, arg2);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		double n = getFirstArgument().evaluate(pm);
		double myValue = 0;
		
		for (int i = 0; i < n; i++) {
			pm.getVariables().setVariable(":repcount", i + 1);
			myValue = getSecondArgument().evaluate(pm);
		}
		
		return myValue;
	}

	@Override
	public void print() {
		System.out.println("Repeat");
	}

}
