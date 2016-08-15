package model.exceptions;

public class VariableValueException extends VariableException {

	private static final long serialVersionUID = 1L;
	private static final String ERROR_MESSAGE = "Variable Value is Invalid";
	
	public VariableValueException(String message) {
		super(ERROR_MESSAGE);
	}

}
