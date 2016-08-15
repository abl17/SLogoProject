package model;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class StampMark {
	private Point2D location;
	private Color markColor;
	private ImageView stampClip;
	private double stampDirection;
	
	public StampMark (Point2D location, Color markColor, ImageView stampClip, Node clipping, double stampDirection) {
		this.location = location;
		this.markColor = markColor;
		
		initializeStampClip(markColor, stampClip, clipping);
		
		this.stampDirection = stampDirection;
	}

	private void initializeStampClip(Color markColor, ImageView stampClip, Node clipping) {
		initializeStampClipDimensions(stampClip, clipping);
		 setStampClipEffects(markColor);
	}

	private void setStampClipEffects(Color markColor) {
		ColorAdjust monochrome = new ColorAdjust();
	        monochrome.setSaturation(-1.0);

	        Blend blush = new Blend(
	                BlendMode.MULTIPLY,
	                monochrome,
	                new ColorInput(
	                        0,
	                        0,
	                        this.stampClip.getFitWidth(),
	                        this.stampClip.getFitHeight(),
	                        markColor
	                )
	        );
		
		this.stampClip.setEffect(blush);
	}

	private void initializeStampClipDimensions(ImageView stampClip, Node clipping) {
		this.stampClip = new ImageView(stampClip.getImage());
		this.stampClip.setFitHeight(stampClip.getFitHeight());
		this.stampClip.setFitWidth(stampClip.getFitWidth());
		this.stampClip.setClip(clipping);
	}
	
	public StampMark (double xLoc, double yLoc, Color markColor, ImageView stampClip, Node clipping, double stampDirection) {
		this(new Point2D(xLoc, yLoc), markColor, stampClip, clipping, stampDirection);
	}
	
	public double getX () {
		return location.getX();
	}

	public double getY () {
		return location.getY();
	}
	
	public Point2D getLocation() {
		return location;
	}
	
	public Color getMarkColor () {
		return markColor;
	}
	
	public ImageView getStampClip () {
		return stampClip;
	}
	
	public double getStampDirection () {
		return stampDirection;
	}
}
