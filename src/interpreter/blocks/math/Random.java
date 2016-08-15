package interpreter.blocks.math;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.OneArgBlock;
import interpreter.blocks.RootBlock;
import interpreter.exceptions.blocks.BlockException;

public class Random extends OneArgBlock implements MathBlock {
	
	public Random(Deque<RootBlock> queue) {
		super(queue);
	}
	
	public Random (RootBlock arg1) {
		super(arg1);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		return Math.random() * getArgument().evaluate(pm);
	}

	@Override
	public void print() {
		try {
			System.out.println(Math.random()*getArgument().evaluate(null));
		} catch (BlockException e) {
			System.out.println("Failed to print");
		}
	}
}
