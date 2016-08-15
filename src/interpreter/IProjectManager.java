package interpreter;

import interpreter.blocks.UserInstruction;
import javafx.scene.paint.Color;
import model.TurtleBundle;
import model.Variables;

/**
 * This interface provides a template for an individual 'Project' in the controller
 * This is the environment where the "executor" lies
 * 
 * @author Austin
 *
 */
public interface IProjectManager {
	public TurtleBundle getTurtleBundle ();
	
	public Variables getVariables ();

	public void addUserInstruction (UserInstruction userInstruction);
	
	public UserInstruction getUserInstruction(String token);
	
	public double setBackground (int index);
	
	public double setCustomColor (int index, Color color);
}
