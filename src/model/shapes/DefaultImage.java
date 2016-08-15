package model.shapes;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.Turtle;

public class DefaultImage implements ShapeImage {
private static final String NAME = "DEFAULT";
	
	public DefaultImage () {}
	
	@Override
	public Node makeShape(Turtle turtle) {
		double size = turtle.getTurtleSize();
		Shape myShape = new Rectangle(size, size);
		return myShape;
	}

	@Override
	public String getName() {
		return NAME;
	}
}
