package interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import interpreter.blocks.UserInstruction;
import interpreter.exceptions.blocks.BlockException;

/**
 * This ParsingTable is a HELPER CLASS... contains the nodes that are necessary for creation.
 * 
 * @author Austin
 *
 */
public class ParsingTable {
	private static final String BLOCK_LIST = "blocks";
	private static final String BLOCK_LIST_SEPARATOR = ";";

	private static final String RESOURCE_FOLDER = "interpreter.resources.PossibleBlocks";

	private List<BlockInfoManager> myBlockInfoList;
	private ResourceBundle rb;
	private ResourceBundle languageBundle;
	private IProjectManager pm;
	private static ParsingTable myTable = null;

	/**
	 * Constructor: Sets up the list of BlockInfoManagers
	 */
	public ParsingTable (String language) {

		rb = ResourceBundle.getBundle(RESOURCE_FOLDER);

		setUpLanguageBundle(language);
	}

	/**
	 * @param language
	 */
	public void setUpLanguageBundle(String language) {
		try {
			languageBundle = ResourceBundle.getBundle("resources.languages." + language);
		} catch (MissingResourceException e) {
			System.out.println("Invalid language shown. Defaulting to English");
			Parser.myLanguage = "English";
			languageBundle = ResourceBundle.getBundle("resources.languages." + "English");
		}

		myBlockInfoList = new ArrayList<BlockInfoManager>();
		String[] myTokens = rb.getString(BLOCK_LIST).split(BLOCK_LIST_SEPARATOR);

		// Try to add tokens to List of BlockInfoManagers
		for (String token : myTokens) {
			try { myBlockInfoList.add(new BlockInfoManager(token.trim(), rb, languageBundle)); } 
			catch (BlockException e) { System.out.println("Something is wrong with adding to the Parsing Table"); }
		}
	}

	/**
	 * Returns the command block associated with a string token
	 * @param token
	 * @return BlockInfoManager
	 */
	public BlockInfoManager getBlockClass (String token) {
		for (BlockInfoManager block : myBlockInfoList) {
			if (block.equals(token.toLowerCase())) {
				BlockInfoManager newBlock = (BlockInfoManager) block.clone();
				newBlock.setToken(token.toLowerCase());
				return newBlock;
			}
		}
		return null;
	}

	public static ParsingTable getThis () {
		if (myTable == null) {
			myTable = new ParsingTable(Parser.myLanguage);
		}
		return myTable;
	}

	public void addUserInstruction (UserInstruction ui) {
		if (pm != null && 
				ui != null) {
			pm.addUserInstruction(ui);
		}
	}

	public UserInstruction getUserInstruction (String name) {
		if (pm == null || name == null) {
			return null;
		} else {
			return pm.getUserInstruction(name);
		}
	}

	public void setProject (IProjectManager pm) {
		this.pm = pm;
	}

	public IProjectManager getProject () {
		return pm;
	}
}
