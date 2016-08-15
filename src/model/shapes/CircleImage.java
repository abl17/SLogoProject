package model.shapes;

import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import model.Turtle;

public class CircleImage implements ShapeImage {
	
	private static final String NAME = "CIRCLE";
	
	public CircleImage () {
		
	}
	
	@Override
	public Node makeShape(Turtle turtle) {
		double size = turtle.getTurtleSize();
		Shape myShape = new Circle(size/2, size/2, size/4);
		return myShape;
	}

	@Override
	public String getName() {
		return NAME;
	}

}
