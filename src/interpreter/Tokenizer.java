package interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * The purpose of this class is to turn characters into symbols, so that later stages of compilation
 * can deal with the program as a whole.
 * 
 * Logo commands can be formatted over any number of lines, including multiple commands on the same line, but
 * they must be separated by one or more spaces.
 * 
 * @author Austin
 *
 */

public class Tokenizer {

	// Splits on spaces (multiple as well as single), specifically for Logo.
	private static final String INTERPRETER_RESOURCE = "interpreter.resources.";
	private static final String DEFAULT_SYNTAX = "DefaultSyntax";
	private static final ResourceBundle RESOURCE = ResourceBundle
			.getBundle(INTERPRETER_RESOURCE + DEFAULT_SYNTAX);
	private static final String REGEX = RESOURCE.getString("REGEX");

	/**
	 * This method takes in a string, and separates it into tokens based on a delimiter.
	 * Tokens can be null valued... length = 0. Need to account for this.
	 * 
	 * @param input
	 * @param delim
	 * @return List of tokens
	 */
	public static List<String> split (String input, String delim) {
		ArrayList<String> myTokens = new ArrayList<String>();
		Pattern p;

		try {
			p = Pattern.compile(delim);
		}
		catch (PatternSyntaxException e) {
			e.printStackTrace();
			System.out.println("Default to space delimiter");
			p = Pattern.compile(REGEX);
		}
		String[] items = p.split(input);

		// Only add the non-null tokens to the Token-list.
		// We don't want null-expressions.
		for (String item : items) {
			if (!item.isEmpty()) {
				myTokens.add(item);
			}
		}
		
		return myTokens;
	}

	/**
	 * Takes in a string and separates it based on the default delimiter: spaces
	 * 
	 * @param input
	 * @return List of tokens
	 */
	public static List<String> split (String input) {
		return split(input, REGEX);
	}

	/**
	 * Prints out the lists of tokens created by the tokenizer
	 * 
	 * @param input
	 * @param delim
	 */
	public static void print (String input, String delim) {
		List<String> myTokens = split(input, delim);
		for (String token : myTokens) {
			System.out.println(token);
		}
	}

	/**
	 * Prints out the list of tokens created by the tokenizer based on the
	 * default delimiter: spaces
	 * 
	 * @param input
	 */
	public static void print (String input) {
		print(input, REGEX);
	}
}
