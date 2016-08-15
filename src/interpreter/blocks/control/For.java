package interpreter.blocks.control;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.RootBlock;
import interpreter.blocks.TwoArgBlock;
import interpreter.blocks.basic.ListStartBlock;
import interpreter.blocks.basic.VariableBlock;
import interpreter.exceptions.blocks.BlockException;

public class For extends TwoArgBlock {

	public For(Deque<RootBlock> queue) {
		super(queue);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		ListStartBlock conditionArg = (ListStartBlock) getFirstArgument();
		double start, end, increment, myValue;
		start = conditionArg.getList().get(1).evaluate(pm);
		end = conditionArg.getList().get(2).evaluate(pm);
		increment = conditionArg.getList().get(3).evaluate(pm);
		
		RootBlock myListOfCommands = ((ListStartBlock) getFirstArgument()).getList().get(0);
		String variableName = ((VariableBlock) myListOfCommands).getVariableName();
		
		myValue = 0;
		
		for (double i = start; i < end; i += increment) {
			pm.getVariables().setVariable(variableName, i);
			myValue = getSecondArgument().evaluate(pm);
		}
		
		return myValue;
	}

	@Override
	public void print() {
		
	}

}
