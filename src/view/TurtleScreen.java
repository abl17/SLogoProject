package view;

import java.util.Collection;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.Mark;
import model.MarkType;
import model.ShapeBundle;
import model.StampMark;
import model.Turtle;
import model.TurtleBundle;
import model.exceptions.TurtleException;
import model.exceptions.TurtleNumberException;

public class TurtleScreen extends Pane implements Observer {

	/**
	 * 
	 * TODO: make these double variables lists, so we can keep track of various
	 * turtles TODO: we need better adjustments to the lines and shit
	 * 
	 * There are two "overlapping" canvases, one for the lines and one for the
	 * background color. You can manipulate them as you please
	 */

	private double myPrevX;
	private double myPrevY;
	private double turtleSize;
	private GraphicsContext myGC;
	private Canvas myLineCanvas;
	private Background myBackground;
	
//	private List<Color> penColors = new ArrayList<Color>();
//	private List<Color> canvasColors = new ArrayList<Color>();
	private static final int TURTLEMAXDIST =  1000000;
	
	

	public TurtleScreen() {
		this.setPrefSize(550, 440);
		turtleSize = 100;
		
		myBackground = new Background(550, 440);
	}

	@Override
	public void update (Observable o, Object arg) {
		try {
			updateScreen(o, arg);
		} catch (TurtleException e) {
			displayCommandError(e);
		}
	}
	
	public void updateScreen (Observable o, Object arg) throws TurtleException {
		@SuppressWarnings("unchecked")
		Collection<Turtle> turtles = ((Map<?, Turtle>) arg).values();
		
		this.getChildren().clear();
		initializeCanvas();
		clearLines();
		
		for (Turtle turtle : turtles) {
			updateTurtle(turtle, ((TurtleBundle) o));
		}
	}
	
	private void initializeCanvas () {
		

		myLineCanvas = new Canvas(this.getWidth(), this.getHeight());
//		myLineCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> launchCanvasColorDialog());
		myGC = myLineCanvas.getGraphicsContext2D();
		myLineCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> new Dialog(myBackground));
		Canvas canvas = myBackground.getBackground();
		this.getChildren().add(canvas);
		this.getChildren().add(myLineCanvas);
	}
	
	private void addTurtle(Turtle o, TurtleBundle turtleBundle) {
		ImageView imageView = initializeTurtle(o);
		imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
	    {
	        @Override
	        public void handle(MouseEvent e)
	        {
	            if (e.getButton() == MouseButton.SECONDARY)
	            {

	            	ContextMenu contextMenu = new ContextMenu();
	            	MenuItem penColor = new MenuItem("Set Pen Color");
	            	penColor.setOnAction(event -> new Dialog(o.getPen()));
	            	MenuItem turtleImage = new MenuItem("Set Turtle Image");
	            	turtleImage.setOnAction(event -> selectTurtleImage(o, turtleBundle));
	            	contextMenu.getItems().addAll(penColor, turtleImage);
	            	contextMenu.show(imageView, Side.RIGHT, 0, 0);
	            }
	            else{
	            	changeTurtleActivation(imageView, o);
	            }
	            e.consume();
	        }
	    });		
		
		this.getChildren().add(imageView);
	}

	private void selectTurtleImage(Turtle o, TurtleBundle turtleBundle){
		ImageDialog imgDialog = new ImageDialog();
		Optional<String> result = imgDialog.showAndWait();
		//result.ifPresent(event -> System.out.println(result.get()));
		result.ifPresent(event -> {
			Image image = new Image(getClass().getClassLoader().getResourceAsStream(result.get()));
			o.setImage(image);
			turtleBundle.update();
		});
	}
	
	protected void changeTurtleActivation(ImageView imageView, Turtle o) {
		imageView.setOpacity(.5+(1-imageView.getOpacity()));
		o.setActiveStatus(!o.isActive());
		o.setTurtleImage(imageView.getImage());
	}

	private ImageView initializeTurtle(Turtle o) {
		ImageView imageView = new ImageView(o.getTurtleImage());
		imageView.setClip(ShapeBundle.getShape((int)o.getShapeIndex()).makeShape(o));
		imageView.setFitWidth(o.getTurtleSize());
		imageView.setFitHeight(o.getTurtleSize());
		
		double x = mapToScreenWidth(o.getXLoc());
		double y = mapToScreenHeight(o.getYLoc());
		imageView.setRotate(o.getTurtleDirection());
		imageView.relocate(x, y);
		return imageView;
	}
	
	public void updateTurtle(Turtle o, TurtleBundle turtleBundle) throws TurtleNumberException {
		drawStamps(o);
		
		if (o.isShowing() == 1) { 
			relocateTurtle(o, turtleBundle);
		}

		//		drawGeneralizedLine(o);
		for (Mark mark : o.getPen().getMarkings()) {
			myGC.setStroke(mark.getMarkColor());
			myGC.setLineWidth(mark.getMarkThickness());
			

			/**
			 * Determine why -mapToScreenHeightForLine(mark.getYInitial()) and -mapToScreenHeightForLine(mark.getYFinal()))
			 */
			double a = mapToScreenWidthForLine(mark.getXInitial());
			double b = -mapToScreenHeightForLine(mark.getYInitial());
			double c = mapToScreenWidthForLine(mark.getXFinal());
			double d = -mapToScreenHeightForLine(mark.getYFinal());
			if(!mark.getMarkType().equals(MarkType.SOLID)){
				double incrementX = 5;
				double slope = 0;
				if((c-a)!=0){
					slope = (d-b)/(c-a);
				}
				if(c-a<0){
					incrementX=-5;
				}
				for(int i = 0; i<Math.abs((c-a)/10);i++){
					drawLines(a+(2*i)*incrementX,b+(2*i)*incrementX*slope,a+(2*i+1)*incrementX,b+(2*i+1)*incrementX*slope);
				}
				if(c-a==0){
					for(int i = 0; i<Math.abs((d-b)/10);i++){
						drawLines(a,b+(2*i)*incrementX,a,b+(2*i+1)*incrementX);
					}
				}
			}
			else{
				drawLines(a,b,c,d);				
			}
			
		}

		myPrevX = mapToScreenWidthForLine(((Turtle) o).getXLoc());
		myPrevY = mapToScreenHeightForLine(((Turtle) o).getYLoc());
	}
	
	/**
	 * @param o
	 * @throws TurtleNumberException 
	 */
	private void relocateTurtle(Turtle o, TurtleBundle turtleBundle) throws TurtleNumberException {
		double curX = o.getXLoc() + this.getWidth() / 2;
		double curY = this.getHeight() / 2 - o.getYLoc();
		if(Math.abs(curX - myPrevX) > 1000000 || Math.abs(curY - (this.getHeight() - myPrevY)) > 1000000){
			throw new TurtleNumberException("The turtle can't draw lines that far");
		}
//		ImageView imageView = initializeTurtle(o);
//		this.getChildren().add(imageView);
		addTurtle(o, turtleBundle);
	}
	
	/**
	 * @param e
	 */
	private void displayCommandError(Exception e) {
		System.out.println(e.getMessage());
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Command Error");
		alert.setHeaderText("You entered an invalid command");
		alert.setContentText(e.getMessage());
		alert.showAndWait();
	}
	
	private void drawGeneralizedLine(Turtle o) throws TurtleNumberException {
		myGC.setLineWidth(o.getPen().getThickness());
		myGC.setStroke(o.getPen().getColor());
		double curX = o.getXLoc() + this.getWidth() / 2;
		double curY = this.getHeight() / 2 - o.getYLoc();
		drawLines(myPrevX, this.getHeight() - myPrevY, curX, curY);
	}

	private void drawStamps(Turtle o) {
		for (StampMark stamp : o.getStampMarks()) {
			ImageView node = stamp.getStampClip();
			double x = mapToScreenWidth(stamp.getX());
			double y = mapToScreenHeight(stamp.getY());
			node.setRotate(stamp.getStampDirection());
			node.relocate(x, y);

			this.getChildren().add(node);
		}
	}

	private void drawLines(double prevX, double prevY, double curX, double curY) throws TurtleNumberException {
		if(Math.abs(curX - prevX) > TURTLEMAXDIST || Math.abs(curY - prevY) > TURTLEMAXDIST){
			throw new TurtleNumberException("The turtle can't draw lines that far");
		}
		if(!isInBounds(prevX,prevY)){

			double a = adjustX(prevX);

			double b = adjustY(prevY);
			prevX += a;
			prevY += b;
			curX += a;
			curY += b;

		}

		if(!isInBounds(curX,curY)){

			double[] coordinates = findintersection(prevX,prevY, curX, curY);

			myGC.strokeLine(prevX, this.getHeight() - prevY, coordinates[0], this.getHeight() - coordinates[1]);
			double xAdj = TorusXAdjustment(coordinates[0]);
			double yAdj = TorusYAdjustment(coordinates[1]);
			drawLines(coordinates[0] + xAdj, coordinates[1] + yAdj, curX + xAdj, curY + yAdj);
		} else {
			myGC.strokeLine(prevX, this.getHeight() - prevY, curX, this.getHeight() - curY);
		}
	}

	private double adjustY(double prevY) {
		double adjust = 0;
		while (prevY < 0) {
			prevY += this.getHeight();
			adjust += this.getHeight();
		}
		while (prevY >= this.getHeight()) {
			prevY -= this.getHeight();
			adjust -= this.getHeight();
		}

		return adjust;
	}

	private double adjustX(double prevX) {
		double adjust = 0;
		while (prevX < 0) {
			prevX += this.getWidth();
			adjust += this.getWidth();
		}
		while (prevX >= this.getWidth()) {
			prevX -= this.getWidth();
			adjust -= this.getWidth();
		}

		return adjust;
	}

	private double TorusYAdjustment(double yCoordinate) {
		if (yCoordinate == 0) {
			return this.getHeight();
		}
		if (yCoordinate == this.getHeight()) {
			return -this.getHeight();
		}
		return 0;
	}

	private double TorusXAdjustment(double xCoordinate) {
		if (xCoordinate == 0) {
			return this.getWidth();
		}
		if (xCoordinate == this.getWidth()) {
			return -this.getWidth();
		}
		return 0;
	}

	private double[] findintersection(double prevX, double prevY, double curX, double curY) {
		double[] coords = new double[2];
		double slope = (curY - prevY) / (curX - prevX);
		double[] slopeToCorners = findSlopeToCorners(prevX, prevY);
		if (curX - prevX >= 0) {
			// System.out.println(prevX + " " + prevY + " " + slope);
			if (slope > slopeToCorners[1]) {
				coords = findTopintersection(prevX, prevY, slope);
			} else if (slope > slopeToCorners[2]) {
				coords = findRightintersection(prevX, prevY, slope);
			} else {
				coords = findBottomintersection(prevX, prevY, slope);
			}
		} else {
			if (prevX == 0) {
				coords = findLeftintersection(prevX, prevY, slope);
			} else if (slope < slopeToCorners[0]) {
				coords = findTopintersection(prevX, prevY, slope);
			} else if (slope < slopeToCorners[3]) {
				coords = findLeftintersection(prevX, prevY, slope);
			} else {
				coords = findBottomintersection(prevX, prevY, slope);
			}
		}
		return coords;
	}

	private double[] findLeftintersection(double prevX, double prevY, double slope) {
		double[] ret = { 0, prevY - slope * prevX };
		return ret;
	}

	private double[] findBottomintersection(double prevX, double prevY, double slope) {
		double[] ret = { prevX - prevY / slope, 0 };
		return ret;
	}

	private double[] findRightintersection(double prevX, double prevY, double slope) {
		double[] ret = { this.getWidth(), prevY + (this.getWidth() - prevX) * slope };
		return ret;
	}

	private double[] findTopintersection(double prevX, double prevY, double slope) {
		double[] topIntersection = new double[2];
		topIntersection[0] = prevX + (this.getHeight() - prevY) / slope;
		topIntersection[1] = this.getHeight();
		return topIntersection;
	}

	private double[] findSlopeToCorners(double prevX, double prevY) {
		double[] slopesToCorners = new double[4];
		slopesToCorners[0] = (this.getHeight() - prevY) / (0 - prevX);
		slopesToCorners[1] = (this.getHeight() - prevY) / (this.getWidth() - prevX);
		slopesToCorners[2] = (0 - prevY) / (this.getWidth() - prevX);
		slopesToCorners[3] = (0 - prevY) / (0 - prevX);

		return slopesToCorners;
	}

	private boolean isInBounds(double curX, double curY) {
		return isInXBounds(curX) && isInYBounds(curY);
	}

	private boolean isInYBounds(double curY) {
		return curY >= 0 && curY <= this.getHeight();

	}

	private boolean isInXBounds(double curX) {
		return curX >= 0 && curX <= this.getWidth();
	}

	private double mapToScreenHeight(double yLoc) {
		double y = (yLoc + this.getHeight() / 2 - turtleSize / 2) % (this.getHeight());
		if (y < 0) {
			return ((this.getHeight()) + y) % (this.getHeight());
		}
		return y;

	}

	private double mapToScreenWidth(double xLoc) {
		double x = (xLoc + this.getWidth() / 2 - turtleSize / 2) % (this.getWidth());
		if (x < 0) {
			return ((this.getWidth()) + x) % (this.getWidth());
		}
		return x;
	}

	private double mapToScreenHeightForLine(double yLoc) {
		return yLoc += this.getHeight() / 2;
	}

	private double mapToScreenWidthForLine(double xLoc) {
		return xLoc += this.getWidth() / 2;
	}

	public void clearLines() {
		myGC.clearRect(0, 0, myLineCanvas.getWidth(), myLineCanvas.getHeight());
		return;
	}

	public void resetLineMarks(TurtleBundle tb) {
		tb.evaluate("clearStamps");
		tb.evaluate("clearMarkings");
	}

	public Background getMyBackground () {
		return myBackground;
	}
	
}
