package interpreter.exceptions.tokenizer;

public abstract class TokenizerException extends Exception {

	/**
	 * The serializable class TokenizerException does not declare a static final 
	 * serialVersionUID field of type long.
	 * 
	 * Hence, I provided a default serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates the TokenizerException subclasses with their unique exception message
	 * No need to reinvent the wheel by having a constructor that stores a message.
	 * The class "Exception" already has a way to store a message. Just pass the message parameter
	 * to the superclass.
	 * 
	 * Message is saved for later retrieval by the Throwable.getMessage() method.
	 * 
	 * @param message: Have an input exception message
	 */
	public TokenizerException (String message) {
		super(message);
	}
	
	/**
	 * Instantiates the TokenizerException subclasses with their exception message as well as a
	 * way to throw errors.
	 * 
	 * @param message
	 * @param throwable
	 */
	public TokenizerException (String message, Throwable throwable) {
		super(message, throwable);
	}
}
