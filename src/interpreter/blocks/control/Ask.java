package interpreter.blocks.control;

import java.util.Arrays;
import java.util.Deque;
import java.util.Set;

import interpreter.IProjectManager;
import interpreter.blocks.RootBlock;
import interpreter.blocks.TwoArgBlock;
import interpreter.blocks.basic.ListStartBlock;
import interpreter.exceptions.blocks.BlockArgumentsException;
import interpreter.exceptions.blocks.BlockException;

public class Ask extends TwoArgBlock {
	public Ask(Deque<RootBlock> queue) {
		super(queue);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		double result = 0;
		try {
			Set<Integer> originalTurtleIDs = pm.getTurtleBundle().getTurtleDeployed().keySet();
			Integer[] orgTurtIDs = originalTurtleIDs.toArray(new Integer[originalTurtleIDs.size()]);
			
			pm.getTurtleBundle().recall();
			
			for (int i = 0; i < ((ListStartBlock) getFirstArgument()).getList().size(); i++) {
				int idVal = (int) ((ListStartBlock) getFirstArgument()).getList().get(i).evaluate(pm);
				pm.getTurtleBundle().deploy(idVal);
			}
			
			result = getSecondArgument().evaluate(pm);
			
			pm.getTurtleBundle().deploy(Arrays.asList(orgTurtIDs));
			
		} catch (ClassCastException e) {
			throw new BlockArgumentsException("Incorrect format of command", "args?");
		}
		
		return result;
	}

	@Override
	public void print() {
		System.out.println("Asking");
	}
}
