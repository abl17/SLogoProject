package interpreter;

import java.util.ResourceBundle;

import interpreter.exceptions.blocks.BlockArgumentsException;
import interpreter.exceptions.blocks.BlockException;
import interpreter.exceptions.blocks.BlockNullException;

public class BlockInfoManager implements Cloneable {
	private static final String CLASS = "class";
	private static final String ARGUMENTS = "args";
	private static final String DESCRIPTION = "description";
	private static final String PATTERN_DELIMITER = ",";
	private static final String PARAMETER_DELIMITER = ".";
	
	private int myArguments;
	private Class<?> myClass;
	private String[] myPatterns;
	private String myDescription, myName, myToken;
	
	private boolean creationValue = true;
	
	/**
	 * Obtains the releveant information for a single command (i.e. all its command information, description,
	 * name, patterns, etc.) needed for its creation.
	 * 
	 * @param name
	 * @param resource
	 * @throws BlockArgumentsException
	 */
	public BlockInfoManager (String name, ResourceBundle resource, ResourceBundle languageBundle) 
			throws BlockException {
		setArguments(Integer.parseInt(resource.getString(name + PARAMETER_DELIMITER + ARGUMENTS)));
		setDescription(resource.getString(name + PARAMETER_DELIMITER + DESCRIPTION));
		setName(name);
		
		setPatterns(languageBundle.getString(name).split(PATTERN_DELIMITER));
		
		// Reflection
		try {
			setBlockClass(Class.forName(resource.getString(name + "." + CLASS)));
		} catch (ClassNotFoundException e) {
			throw new BlockNullException("Class doesn't exist", e.getMessage());
		}
	}
	
	/**
	 * Has stuff associated with each command
	 * 
	 * @param inputName
	 * @param inputPatterns
	 * @param inputClass
	 * @param inputArguments
	 * @param inputDescription
	 * @param inputToken
	 */
	public BlockInfoManager (String inputName, String[] inputPatterns, Class<?> inputClass,
			int inputArguments, String inputDescription, String inputToken) {
		setName(inputName);
		setPatterns(inputPatterns);
		setBlockClass(inputClass);
		setArguments(inputArguments);
		setDescription(inputDescription);
		setToken(inputToken);
	}
	
	/**
	 * Implements cloneable to be able to make a new instance of this
	 */
	@Override
	public Object clone () {
		return new BlockInfoManager(myName, myPatterns, myClass,
				myArguments, myDescription, myToken);
	}
	
	/**
	 * Overrides equal to match string to pattern
	 */
	@Override
	public boolean equals (Object obj) {
		if (obj instanceof String && obj != null) {
			
			boolean isMatched = false;
			
			for (String pattern : myPatterns) {
				if (((String) obj).matches(pattern)) {
					isMatched = true;
					break;
				}
			}
			return isMatched;
			
		} else {
			return false;
		}
	}

	/**
	 * Getters and Setters
	 */
	
	public boolean isValidToCreate () { return creationValue; }
	public void setValidityToCreate (boolean creationValue) { this.creationValue = creationValue; }

	public String getName () { return myName; }
	public void setName (String myName){ this.myName = myName; }
	
	public int getArguments () { return myArguments; }
	public void setArguments (int myArguments) { this.myArguments = myArguments; }

	public Class<?> getBlockClass () { return myClass; }
	public void setBlockClass (Class<?> myClass) { this.myClass = myClass; }

	public String[] getPatterns () { return myPatterns; }
	public void setPatterns (String[] myPatterns) { this.myPatterns = myPatterns; }

	public String getDescription () { return myDescription; }
	public void setDescription (String myDescription) { this.myDescription = myDescription; }

	public String getToken () { return myToken; }
	public void setToken (String myToken) { this.myToken = myToken; }
}
