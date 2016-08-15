package model;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Mark {
	private Point2D initialLocation;
	private Point2D finalLocation;
	private Color markColor;
	private double markThickness;
	private MarkType markType;
	
	public Mark () {
		this(0, 0, 0, 0, Color.BLACK, 1.0, MarkType.SOLID);
	}
	
	public Mark (Point2D initialLocation, Point2D finalLocation, 
			Color color, double thickness, MarkType markType) {
		this.initialLocation = initialLocation;
		this.finalLocation = finalLocation;
		markColor = color;
		markThickness = thickness;
		this.markType = markType;
	}
	
	public Mark (double xInitial, double yInitial, double xFinal, double yFinal, 
			Color color, double thickness, MarkType markType) {
		this(new Point2D(xInitial, yInitial), new Point2D(xFinal, yFinal), color, thickness, markType);
	}
	
	public double getXInitial () {
		return initialLocation.getX();
	}

	public double getYInitial () {
		return initialLocation.getY();
	}
	
	public double getXFinal () {
		return finalLocation.getX();
	}
	
	public double getYFinal () {
		return finalLocation.getY();
	}
	
	public Point2D getInitialLocation() {
		return initialLocation;
	}
	
	public Point2D getFinalLocation() {
		return finalLocation;
	}
	
	public Color getMarkColor () {
		return markColor;
	}
	
	public double getMarkThickness () {
		return markThickness;
	}
	public MarkType getMarkType(){
		return markType;
	}
}
