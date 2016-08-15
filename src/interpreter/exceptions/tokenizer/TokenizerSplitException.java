package interpreter.exceptions.tokenizer;

public class TokenizerSplitException extends TokenizerException {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	public TokenizerSplitException(String message) {
		super(message);
	}
	
	public TokenizerSplitException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
