package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import model.shapes.*;

public class ShapeBundle extends Observable {
	private static final Map<Integer, ShapeImage> shapes = new HashMap<Integer,ShapeImage>();
	
	private static final ShapeImage[] DEFAULT_SHAPES = new ShapeImage[] {
			new DefaultImage(),
			new CircleImage(),
			new TriangleImage(),
			new SquareImage(),
			new DiamondImage()
	};
	
	private static final int SIZE = DEFAULT_SHAPES.length;
	
	public ShapeBundle () {
		for (int i = 0; i < SIZE; i++) {
			shapes.put(i, DEFAULT_SHAPES[i]);
		}
	}
	
	public static ShapeImage getShape (int index) {
		if (index >= SIZE || index < 0) {
			return DEFAULT_SHAPES[0];
		}
		ShapeImage myShape = DEFAULT_SHAPES[index];
		return myShape;
	}
	
	public static int getIndex (ShapeImage shape) {
		for (int i = 0; i < DEFAULT_SHAPES.length; i++) {
			if (DEFAULT_SHAPES[i].getName().equals(shape.getName())) {
				return i;
			}
		}
		
		return -1;
	}
	
	public static int getSize () {
		return SIZE;
	}
}
