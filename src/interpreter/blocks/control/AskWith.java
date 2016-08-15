package interpreter.blocks.control;

import java.util.Arrays;
import java.util.Deque;
import java.util.Set;
import java.util.TreeSet;

import interpreter.IProjectManager;
import interpreter.blocks.RootBlock;
import interpreter.blocks.TwoArgBlock;
import interpreter.blocks.basic.ListStartBlock;
import interpreter.exceptions.blocks.BlockArgumentsException;
import interpreter.exceptions.blocks.BlockException;

public class AskWith extends TwoArgBlock {
	public AskWith(Deque<RootBlock> queue) {
		super(queue);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		double result = 0;
		ListStartBlock condition = (ListStartBlock) getFirstArgument();
		try {
			Set<Integer> originalTurtleIDs = pm.getTurtleBundle().getTurtleDeployed().keySet();
			Integer[] orgTurtIDs = originalTurtleIDs.toArray(new Integer[originalTurtleIDs.size()]);
			Set<Integer> matchingTurtleIDs = new TreeSet<Integer>();
			
			for (int id : orgTurtIDs) {
				pm.getTurtleBundle().recall();
				pm.getTurtleBundle().deploy(id);
				
				double testVal = condition.evaluate(pm);
				
				if (testVal == 1) {
					matchingTurtleIDs.add(id);
				}
			}
			
			pm.getTurtleBundle().recall();
			pm.getTurtleBundle().deploy(matchingTurtleIDs);
			result = ((ListStartBlock) getSecondArgument()).evaluate(pm);
			
			pm.getTurtleBundle().recall();
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
