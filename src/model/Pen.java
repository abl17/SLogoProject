package model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

public class Pen {
	
	// TODO getStrokeDashArray
	
	private static final double DEFAULT_THICKNESS = 1.0;
	private static final Color DEFAULT_COLOR = Color.BLACK;

	private boolean isWriting;
	private List<Mark> markings;
	private double thickness;
	private Color color;
	private MarkType markType;

	public Pen () {
		this(DEFAULT_THICKNESS, DEFAULT_COLOR);
	}

	public Pen (double thickness, Color color) {
		this.thickness = thickness;
		this.color = color;
		this.isWriting = true;
		this.markings = new ArrayList<Mark>();
		this.markType = MarkType.SOLID;
	}

	public void addMarking (Mark newMark) {
		if (isWriting) {
			markings.add(newMark);
		}
	}

	public List<Mark> getMarkings () {
		return markings;
	}

	public void clearMarkings () {
		markings.clear();
	}

	public boolean getWritingStatus () { 
		return isWriting;
	}

	public void setColor (Color newColor) {
		color = newColor;
	}

	public void setThickness (double thickness) {
		this.thickness = thickness;
	}
	
	public void setMarkType (String str) {
		markType = MarkType.valueOf(str);
	}
	
	public double getThickness () {
		return thickness;
	}

	public Color getColor () {
		return color;
	}
	
	public MarkType getMarkType () {
		return markType;
	}

	public void setWriting (boolean isWriting) {
		this.isWriting = isWriting;
	}
}
