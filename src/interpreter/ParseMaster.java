package interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import interpreter.blocks.RootBlock;
import interpreter.exceptions.blocks.BlockArgumentsException;
import interpreter.exceptions.blocks.BlockException;
import model.exceptions.NumberException;

/**
 * Primary location for parse -> evaluate portion of the read-eval-print loop
 * Builds abstract syntax tree with Parser from tokens from Tokenizer
 * 
 * @author Austin
 *
 */

public class ParseMaster {
	private Parser myParser;
	private static final String COMMENT_DELIMITER = "#";
	private static final String COMMAND_DELIMITER = " ";
	private static final String NEWLINE_DELIMITER = "\n";
	
	// Creates a new parser object for parsing
	public ParseMaster (String language) {
		myParser = new Parser(language);
	}

	/**
	 * This class takes a string command and turns it into a list of trees with heads
	 * In other words, each command is represented by a RootBlock
	 * 
	 * @param input string
	 * @return list of RootBlock, i.e. parents for each tree created
	 * @throws BlockException
	 * @throws NumberException 
	 */
	public List<RootBlock> parse (String input) throws BlockException, NumberException {
		// Handle empty inputs
		if (input.isEmpty()) {
			return null;
		}
		
		List<String> myTokens = new ArrayList<String>();

		while (true) {
			int commentLocation = input.indexOf(COMMENT_DELIMITER);
			int inputSize = input.length();
			
			if (commentLocation == -1) {
				myTokens.addAll(Tokenizer.split(input));
				break;
			}
			
			myTokens.addAll(Tokenizer.split(input.substring(0, commentLocation)));
			input = input.substring(commentLocation, inputSize);
			inputSize = input.length();
			commentLocation = input.indexOf(COMMENT_DELIMITER);
			int newLineLocation = input.indexOf(NEWLINE_DELIMITER);
			
			if (newLineLocation <= commentLocation) {
				break;
			} else {
				input = input.substring(newLineLocation, inputSize);
			}
		}
		
		List<RootBlock> myNodes = myParser.evaluate(myTokens);

		return myNodes;
	}

	/**
	 * This class takes in a scanner which contains strings of commands and returns it into
	 * a list of trees with heads
	 * In other words, each command is represented by a RootBlock
	 * 
	 * @param scanner input
	 * @return list of RootBlock, i.e. parents for each tree created
	 * @throws NumberException 
	 * @throws BlockArgumentsException
	 */
	public List<RootBlock> parse (Scanner sc) throws BlockException, NumberException {

		// We are building the string here
		String myString = "";

		// Keep reading from the scanner
		while (sc.hasNext()) {
			String line = sc.nextLine();

			// This code finds locations where there are comments, and takes the stuff before that
			int commentLocation = line.indexOf(COMMENT_DELIMITER);
			if (commentLocation != -1) {
				line = line.substring(0, commentLocation);
			}

			// Remove any leading or trailing spaces, newlines, tabs, etc.
			line = line.trim();

			// Commands separated by lines are essentially just separated by spaces...
			// This was our assumption. Because we're doing this computation in a while loop,
			// separate lines need to be separated in general.
			line += COMMAND_DELIMITER;

			// Add each line to the myString
			myString += line;
		}

		// Make sure we're parsing a trimmed String with no leading or trailing spaces
		return parse(myString.trim());
	}
	
	/**
	 * Gets the parser
	 * @return
	 */
	public Parser getParser () { 
		return myParser;
	}
	
	/**
	 * Sets the language of the parser
	 * @param language
	 */
	public void setLanguage (String language) {
		myParser.setLanguage(language);
	}
}
