package model.exceptions;

public class NumberException extends Exception{

	private static final String ERROR_MSG = "Number is either invalid or too large";
	
	private static final long serialVersionUID = 1L;
	public NumberException (String message) {
		super(ERROR_MSG);
	}

}
