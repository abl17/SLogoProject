package model.shapes;

import javafx.scene.Node;
import javafx.scene.shape.Polygon;
import model.Turtle;

public class TriangleImage implements ShapeImage {
	private static final String NAME = "TRIANGLE";
	
	public TriangleImage () {
		
	}
	
	@Override
	public Node makeShape(Turtle turtle) {
		double size = turtle.getTurtleSize();
		Polygon myShape = new Polygon();
		myShape.getPoints().addAll(new Double[]{
			    size/4, size/4,
			    3*size/4, size/2,
			    size/4, 3*size/4 });

		return myShape;
	}

	@Override
	public String getName() {
		return NAME;
	}
}
