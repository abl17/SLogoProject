package interpreter.blocks.basic;

import interpreter.IProjectManager;
import interpreter.blocks.ZeroArgBlock;
import interpreter.exceptions.blocks.BlockArgumentsException;

public class CommentBlock extends ZeroArgBlock {

	@Override
	public double evaluate(IProjectManager pm) throws BlockArgumentsException {
		return 0;
	}

	@Override
	public void print() {
		
	}

}
