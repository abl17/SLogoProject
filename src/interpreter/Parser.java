package interpreter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import interpreter.blocks.RootBlock;
import interpreter.blocks.TokenBlock;
import interpreter.blocks.UserInstruction;
import interpreter.exceptions.blocks.BlockArgumentsException;
import interpreter.exceptions.blocks.BlockException;
import interpreter.exceptions.blocks.BlockNullException;
import model.exceptions.NumberException;
import util.ClassMakerUtility;

public class Parser {

	private static final int INDEX_OFFSET = 1;
	private static final int LIST_START_OFFSET = 2;
	private static final int CONTENTS_OFFSET = 3;
	
	private ParsingTable parsingTable;
	public static String myLanguage;

	public Parser (String language) {
		myLanguage = language;
		parsingTable = ParsingTable.getThis();

	}

	/**
	 * Creates a block based on stuff
	 * 
	 * @param token
	 * @param blockInfo
	 * @param parameters
	 * @return RootBlock
	 * @throws NumberException 
	 * @throws BlockArgumentsException
	 */
	private RootBlock createBlock (String token, BlockInfoManager blockInfo, Deque<RootBlock> parameters) 
			throws BlockException, NumberException {

		TokenBlock myTB = new TokenBlock(token);

		if (blockInfo.getName().equals("Command") && parsingTable.getUserInstruction(token) == null) { 
			throw new BlockArgumentsException("Not a valid command", blockInfo.getName()); 
		} else if (!blockInfo.isValidToCreate()) { 
			return myTB; 
		} else if (blockInfo.getArguments() == 0) { 
			parameters.push(myTB); 
		}

		if (parameters.size() >= blockInfo.getArguments()) {
			try {
				return ClassMakerUtility.createBlockInstance(blockInfo.getBlockClass(), parameters);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				throw new BlockArgumentsException("Argument errors have occured", blockInfo.getName());
			}
		} else {
			throw new BlockArgumentsException("Size of inputted arguments is incorrect", blockInfo.getName());
		}
	}

	/**
	 * Simply evaluates the list of tokens into a list of trees
	 * @param tokens
	 * @return a list of trees
	 * @throws NumberException 
	 * @throws BlockArgumentsException
	 */
	public List<RootBlock> evaluate (List<String> tokens) throws BlockException, NumberException {
		Collections.reverse(tokens);
		List<BlockInfoManager> blocks = tokensToBlocks(tokens);
		Deque<RootBlock> queue = setCommandsInQueue(tokens, blocks);
		List<RootBlock> evaluationBlocks = new ArrayList<RootBlock>();

		while (!queue.isEmpty()) {
			evaluationBlocks.add(queue.pop());
		}

		return evaluationBlocks;
	}

	/**
	 * @param tokens
	 * @param blocks
	 * @return Queue of Commands
	 * @throws NumberException 
	 * @throws BlockArgumentsException
	 */
	private Deque<RootBlock> setCommandsInQueue(List<String> tokens, List<BlockInfoManager> blocks)
			throws BlockException, NumberException {

		Deque<RootBlock> queue = new ArrayDeque<RootBlock>();

		for (int i = 0; i < blocks.size(); i++) {
			if (blocks.get(i) != null) {
				RootBlock myRootBlock = createBlock(tokens.get(i), blocks.get(i), queue);
				queue.push(myRootBlock);
			} else {
				throw new BlockNullException("One of the blocks is null. Cannot evaluate", tokens.get(i));
			}
		}

		return queue;
	}

	/**
	 * Does conversion from tokens to blocks
	 * 
	 * @param tokens
	 * @return BlockInfoManager
	 */
	private List<BlockInfoManager> tokensToBlocks(List<String> tokens) throws BlockException {
		List<BlockInfoManager> blocks = new ArrayList<BlockInfoManager>();

		for (int i = 0; i < tokens.size(); i++) {
			BlockInfoManager block = parsingTable.getBlockClass(tokens.get(i));

			/**
			 * This error is thrown when one tries to convert the tokens to blocks, and there is an incorrect token specified
			 * This can be resulting from an unknown character or format: e.g. repeat235098 (with numbers)
			 */
			if (block == null) {
				throw new BlockNullException("The format of the block is incorrect!", 
						"Resulted from an unknown character processed");
			}

			blocks.add(block);

			if (block.getName() == null) {
				throw new BlockNullException("The name of this block is null!",
						"Resulted from having a null name");
			}

			if (block.getName().equals("MakeUserInstruction")) {
				parsingTable.addUserInstruction(createUserInstruction(blocks, i));
			}
		}
		return blocks;
	}

	private UserInstruction createUserInstruction (List<BlockInfoManager> blocks, int i) throws BlockException {
		if (i < 2) {
			throw new BlockArgumentsException("Incorrect format for command! Too few arguments!", "Too few arguments!");
		}

		BlockInfoManager myBIM = blocks.get(i - INDEX_OFFSET);
		BlockInfoManager myBIM2 = blocks.get(i - LIST_START_OFFSET);
		myBIM.setValidityToCreate(false);

		String uiName = myBIM.getToken();

		testListStartValidity(myBIM2);

		int argCount = 0;

		/**
		 * Recall, this is in reverse
		 */
		for (int j = i - CONTENTS_OFFSET; j > 0; j--) {
			myBIM2 = blocks.get(j);
			if (myBIM2.getName().equals("ListEnd")) {
				return new UserInstruction(uiName, argCount);
			}
			argCount++;
		}

		throw new BlockArgumentsException("The input arguments for the User Instruction Command are not in the right format", 
				"This is caused by incorrect bracing");
	}

	private void testListStartValidity(BlockInfoManager myBIM2) throws BlockArgumentsException {
		if (!myBIM2.getName().equals("ListStart")) {
			throw new BlockArgumentsException("User Instruction command definition is incorrect",
					"User Instruction must include a list start and a list end");
		}
	}

	public void setLanguage (String newLanguage) {
		myLanguage = newLanguage;
		parsingTable = new ParsingTable(newLanguage);
	}
}
