package interpreter.blocks.control;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.RootBlock;
import interpreter.blocks.TwoArgBlock;
import interpreter.blocks.basic.VariableBlock;
import interpreter.exceptions.blocks.BlockArgumentsException;
import interpreter.exceptions.blocks.BlockException;

public class MakeVariable extends TwoArgBlock {

	public MakeVariable(Deque<RootBlock> queue) {
		super(queue);
	}
	
	public MakeVariable (RootBlock arg1, RootBlock arg2) {
		super(arg1, arg2);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		if (!(getFirstArgument() instanceof VariableBlock)) {
			//TODO: Throw another variable
			throw new BlockArgumentsException("Wrong!", "Wrong!");
		} else {
			VariableBlock myVariableBlock = (VariableBlock) getFirstArgument();
			RootBlock myAssignmentConstant = getSecondArgument();
			double myConstant = myAssignmentConstant.evaluate(pm);
			
			pm.getVariables().setVariable(myVariableBlock.getVariableName(), myConstant);
			return myConstant;
		}
	}

	@Override
	public void print() {
		System.out.println("Hi"); 
	}

}
