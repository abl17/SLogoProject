package interpreter.blocks;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.exceptions.blocks.BlockArgumentsException;

public class TokenBlock extends RootBlock {

	private String token;
	
	public TokenBlock (String token) {
		this.token = token;
	}
	
	public TokenBlock (Deque<RootBlock> queue) {
		TokenBlock myToken = (TokenBlock) queue.pop();
		String obtainedName = myToken.getToken();
		this.token = obtainedName;
	}
	
	@Override
	public double evaluate(IProjectManager pm) throws BlockArgumentsException {
		return 0;
	}

	@Override
	public void print() {
		System.out.println(token);
	}
	
	/**
	 * Returns inputted token
	 * 
	 * @return
	 */
	public String getToken() {
		return token;
	}

}
