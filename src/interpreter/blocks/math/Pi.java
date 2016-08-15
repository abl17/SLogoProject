package interpreter.blocks.math;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.RootBlock;
import interpreter.blocks.ZeroArgBlock;
import interpreter.exceptions.blocks.BlockArgumentsException;

public class Pi extends ZeroArgBlock implements MathBlock {
	
	public Pi (Deque<RootBlock> queue) {
		if (queue != null) {
			queue.pop();
		}
	}
	
	//TODO!!!
	@Override
	public double evaluate(IProjectManager pm) throws BlockArgumentsException {
		return Math.PI;
	}

	@Override
	public void print() {
			System.out.println(Math.PI);
	}

}
