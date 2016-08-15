package view;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Background implements Observer {
	private Canvas myBackground;
	private GraphicsContext myBackGC;

	private Color myBackgroundColor;

	
	public Background(double width, double height){
		myBackgroundColor = Color.WHITE;
		myBackground = new Canvas(width, height);
		myBackGC = myBackground.getGraphicsContext2D();
	}
	
	public void setBackgroundColor() {
		myBackGC.setFill(myBackgroundColor);
		myBackGC.fillRect(0,0,myBackground.getWidth(),myBackground.getHeight());
	}
	
	public void setBackgroundColor(Color color) {
		myBackgroundColor = color;
		setBackgroundColor();
	}

	public Canvas getBackground() {
		return myBackground;
	}

	@Override
	public void update(Observable o, Object arg) {
		setBackgroundColor((Color) arg);
	}

}
