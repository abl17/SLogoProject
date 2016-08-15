package interpreter.blocks.basic;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import interpreter.IProjectManager;
import interpreter.ParsingTable;
import interpreter.blocks.RootBlock;
import interpreter.blocks.TokenBlock;
import interpreter.blocks.UserInstruction;
import interpreter.blocks.ZeroArgBlock;
import interpreter.exceptions.blocks.BlockArgumentsException;
import interpreter.exceptions.blocks.BlockException;

public class CommandBlock extends ZeroArgBlock {

	private String name;
	private UserInstruction userInstruction;
	
	private List<RootBlock> arguments;
	
	public CommandBlock (Deque<RootBlock> queue) {
		this(((TokenBlock) queue.pop()).getToken());
		
		if (userInstruction != null &&
				userInstruction.getNumberArgs() <= queue.size()) {
			for (int i = 0; i < userInstruction.getNumberArgs(); i++) {
				arguments.add(queue.pop());
			}
		}
	}
	
	public CommandBlock (String name) {
		this.name = name;
		arguments = new ArrayList<RootBlock>();
		
		userInstruction = ParsingTable.getThis().getUserInstruction(name);
	}
	
	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		ArrayList<Double> myVariables = new ArrayList<Double>();
		
		for (int i = 0; i < userInstruction.getNumberArgs(); i++) {
			try {
			myVariables.add(arguments.get(i).evaluate(pm));
			} catch (IndexOutOfBoundsException e) {
				throw new BlockArgumentsException("Custom Command needs more commands", "Array index out of bounds");
			}
		}
		
		for (int i = 0; i < userInstruction.getNumberArgs(); i++) {
			pm.getVariables().setVariable(userInstruction.getArguments().get(i).getVariableName(), 
					myVariables.get(i));
		}
		
		return userInstruction.getCommands().evaluate(pm);
	}

	@Override
	public void print() {
		System.out.println("Ham");
	}
	
	public String getName () {
		return name;
	}
	
	public List<RootBlock> getArguments () {
		return arguments;
	}

}
