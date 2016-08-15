package interpreter.blocks.control;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.RootBlock;
import interpreter.blocks.ThreeArgBlock;
import interpreter.blocks.TokenBlock;
import interpreter.blocks.UserInstruction;
import interpreter.blocks.basic.ListStartBlock;
import interpreter.exceptions.blocks.BlockArgumentsException;

public class MakeUserInstruction extends ThreeArgBlock {

	public MakeUserInstruction(Deque<RootBlock> queue) {
		super(queue);
	}
	
	public MakeUserInstruction(RootBlock arg1, RootBlock arg2, RootBlock arg3) {
		super(arg1, arg2, arg3);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockArgumentsException {
		UserInstruction myUserInstruction = pm.getUserInstruction(((TokenBlock) getFirstArgument()).getToken());
		
		ListStartBlock parameters = (ListStartBlock) getSecondArgument();

		
		ListStartBlock commands = (ListStartBlock) getThirdArgument();

		myUserInstruction.addInfo(parameters, commands);
		
		return 1;
	}

	@Override
	public void print() {
		System.out.println("Custom Command");
	}

}
