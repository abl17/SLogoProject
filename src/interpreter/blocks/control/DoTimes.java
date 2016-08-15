package interpreter.blocks.control;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.RootBlock;
import interpreter.blocks.TwoArgBlock;
import interpreter.blocks.basic.ListStartBlock;
import interpreter.blocks.basic.VariableBlock;
import interpreter.exceptions.blocks.BlockException;

public class DoTimes extends TwoArgBlock {

	public DoTimes(Deque<RootBlock> queue) {
		super(queue);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		double n = getFirstArgument().evaluate(pm);
		RootBlock myListOfCommands = ((ListStartBlock) getFirstArgument()).getList().get(0);
		String variableName = ((VariableBlock) myListOfCommands).getVariableName();
		System.out.println(variableName);
		double myValue = 0;
		
		for (int i = 0; i < n; i++) {
			pm.getVariables().setVariable(variableName, i + 1);
			myValue = getSecondArgument().evaluate(pm);
		}
		
		return myValue;
	}

	@Override
	public void print() {
		
	}

}
