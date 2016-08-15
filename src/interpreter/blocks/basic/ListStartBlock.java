package interpreter.blocks.basic;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import interpreter.IProjectManager;
import interpreter.blocks.RootBlock;
import interpreter.exceptions.blocks.BlockException;

public class ListStartBlock extends RootBlock {
	
	private List<RootBlock> myList;

	public ListStartBlock (Deque<RootBlock> queue) {
		myList = new ArrayList<RootBlock>();
		
		while (!(queue.isEmpty())) {
			RootBlock myBlock = queue.pop();
			if (!(myBlock instanceof ListEndBlock)) {
				myList.add(myBlock);
			} else {
				break;
			}
		}
	}
	
	public ListStartBlock (String token) {
		
	}
	
	public List<RootBlock> getList () {
		return myList;
	}
	
	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		double myValue = 0;
		for (RootBlock myBlock : myList) {
			myValue = myBlock.evaluate(pm);
		}
		return myValue;
	}

	@Override
	public void print() {
		for (RootBlock myBlock : myList) {
			myBlock.print();
		}
	}

}
