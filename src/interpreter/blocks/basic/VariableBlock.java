package interpreter.blocks.basic;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.RootBlock;
import interpreter.blocks.TokenBlock;
import interpreter.exceptions.blocks.BlockArgumentsException;

public class VariableBlock extends TokenBlock {

	private String variableName;
	
	public VariableBlock (Deque<RootBlock> queue) {
		super(queue);
		variableName = getToken();
	}
	
	public VariableBlock (String obtainedName) {
		super(obtainedName);
		variableName = getToken();
	}
	
	public String getVariableName () {
		return getToken();
	}
	
	@Override
	public double evaluate(IProjectManager pm) throws BlockArgumentsException {
		Double myValue = pm.getVariables().getVariable(variableName);
		if (myValue != null) {
			return myValue;
		} else {
			return 0;
		}
	}

	@Override
	public void print() {
		System.out.println(variableName);
	}

}
