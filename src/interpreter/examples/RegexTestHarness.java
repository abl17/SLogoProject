package interpreter.examples;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Code adapted and modified from Oracle tutorial on regular expressions
 * 
 * Defines a reusable test harness, exploring the regular expression (regex) constructs supported by the Java API.
 * The application runs repeatedly, prompting the user for a regular expression and a string.
 * 
 * @author Austin Liu
 * @source https://docs.oracle.com/javase/tutorial/essential/regex/test_harness.html
 *
 */

public class RegexTestHarness {
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in); 

		System.out.print("Enter your regex: ");
		String regexName = sc.nextLine();

		Pattern pattern = 
				Pattern.compile(regexName);

		System.out.print("Enter input string to search: ");
		String inputString = sc.nextLine();

		Matcher matcher = 
				pattern.matcher(inputString);

		boolean found = false;
		
		while (matcher.find()) {
			System.out.format("I found the text %s " + 
					"starting at index %d " +
					"and ending at index %d.%n", 
					matcher.group(), matcher.start(), matcher.end());
			found = true;
		}
		
		if (!found){
			System.out.format("No match found.%n");
		}
		
		// Close scanner to prevent resource leak
		if (sc != null) {
			sc.close();
		}
	}
}
