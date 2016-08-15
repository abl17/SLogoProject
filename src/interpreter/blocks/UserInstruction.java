package interpreter.blocks;

import java.util.ArrayList;
import java.util.List;

import interpreter.blocks.basic.ListStartBlock;
import interpreter.blocks.basic.VariableBlock;
import interpreter.exceptions.blocks.BlockArgumentsException;

public class UserInstruction {
	
	private String name;
	private int numberArgs;
	private List<VariableBlock> myArguments;
	private ListStartBlock myCommands;
	
	public UserInstruction (String name, int numberArgs) {
		this.name = name;
		this.numberArgs = numberArgs;
		myArguments = new ArrayList<VariableBlock>();
		myCommands = null;
	}
	
	public void addInfo (ListStartBlock parameters, ListStartBlock commands) throws BlockArgumentsException {
		myArguments.clear();
		myCommands = commands;
		
		for (RootBlock singleParam : parameters.getList()) {
			if (singleParam instanceof VariableBlock) {
				myArguments.add((VariableBlock) singleParam);
			} else {
				throw new BlockArgumentsException("List must have all variables", "");
			}
		}
	}
	
	public ListStartBlock getCommands () {
		return myCommands;
	}
	
	public int getNumberArgs () {
		return numberArgs;
	}
	
	public List<VariableBlock> getArguments () {
		return myArguments;
	}
	
	public boolean isCreated () {
		return (myArguments != null); 
	}
	
	public String getName () {
		return name;
	}
}
