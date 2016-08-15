package interpreter.blocks.basic;

import java.util.Deque;

import interpreter.blocks.RootBlock;
import interpreter.blocks.TokenBlock;

public class ListEndBlock extends TokenBlock {

	public ListEndBlock (Deque<RootBlock> queue) {
		this(((TokenBlock) queue.pop()).getToken());
	}

	public ListEndBlock(String token) {
		super(token);
	}
}
