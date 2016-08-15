package model.shapes;

import javafx.scene.Node;
import model.Turtle;

public interface ShapeImage {
	 
	public Node makeShape (Turtle turtle);
	
	public String getName ();
	
}
