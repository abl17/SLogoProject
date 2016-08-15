package interpreter.blocks.display;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.OneArgBlock;
import interpreter.blocks.RootBlock;
import interpreter.exceptions.blocks.BlockException;

public class SetBackground extends OneArgBlock {

	public SetBackground(Deque<RootBlock> queue) {
		super(queue);
	}
	
	public SetBackground (RootBlock arg1) {
		super(arg1);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		double myValue = getArgument().evaluate(pm);
		int backgroundIndex = (int) myValue;
		pm.setBackground(backgroundIndex);
		return myValue;
	}

	@Override
	public void print() {
		System.out.println("Setting background!");
		
	}

}
