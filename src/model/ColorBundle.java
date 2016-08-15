package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.scene.paint.Color;

/**
 * Concept adopted from: https://msdn.microsoft.com/en-us/library/dd355244.aspx
 * 
 * The RGB color model is used for specifying colors. This model specifies the intensity of red, green, and blue on a scale of 0 to 255, 
 * with 0 (zero) indicating the minimum intensity. The settings of the three colors are converted to a single integer value by using this
 * formula:
 *				RGB value= Red + (Green*256) + (Blue*256*256)
 * 
 * @author Austin
 *
 */

public class ColorBundle extends Observable {
	
	private static final int MAX_RGB_INT = 256*256*255 + 256*255 + 255;
	private static final int MIN_RGB_INT = 0;
	private static final int MAX_COLOR_INT = 256;
	private static final Color DEFAULT_COLOR = Color.WHITE;
	
	private Map<Integer, Color> customColors;
	private Color backgroundColor;

	public ColorBundle () {
		customColors = new HashMap<Integer, Color>();
		backgroundColor = DEFAULT_COLOR;
	}
	
	public static Color getColor (int redIntensity, int blueIntensity, int greenIntensity) {
		return Color.rgb(redIntensity, greenIntensity, blueIntensity);
	}
	
	public static Color getColor (int rgbInteger) {
		if (rgbInteger < MIN_RGB_INT || rgbInteger > MAX_RGB_INT) {
			return null;
		}
		
		int blue = rgbInteger / (MAX_COLOR_INT * MAX_COLOR_INT);
		int green = (rgbInteger - MAX_COLOR_INT*MAX_COLOR_INT*blue) / MAX_COLOR_INT;
		int red = (rgbInteger - MAX_COLOR_INT*MAX_COLOR_INT*blue - MAX_COLOR_INT*green);
		
		return Color.rgb(red, green, blue);
	}
	
	public void defineCustomColor (int index, Color color) {
		customColors.put(index, color);
		update();
	}
	public Map<Integer, Color> getColorMap(){
		return customColors;
	}
	public Color getCustomColor (int index) {
		return customColors.get(index);
	}
	
	public Color getBackgroundColor () {
		return backgroundColor;
	}
	
	public void setBackgroundColor (Color color) {
		backgroundColor = color;
		update();
	}
	
	public void update () {
		setChanged();
		notifyObservers(backgroundColor);
	}
}
