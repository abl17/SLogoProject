package interpreter.blocks.turtleCommands;

import java.util.ResourceBundle;

public class TurtleCommandFactory {
	
	private static final String LOCATION = "interpreter.resources.TurtleCommands";
	private static final ResourceBundle rb = ResourceBundle.getBundle(LOCATION);
	
	public static String getAction (TurtleCommandBlock tcb) {
		Class<? extends TurtleCommandBlock> myTCBClass = tcb.getClass();
		String className = myTCBClass.getSimpleName();
		return rb.getString(className);
	}
}
