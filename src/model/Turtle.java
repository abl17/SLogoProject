package model;


import javafx.geometry.Point2D;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.shapes.ShapeImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * 
 * TODO: modify how images work, we store too many variables. 
 * The turtle only needs to contain the image view. 
 *
 */

public class Turtle extends Observable {
	private Image turtleImage;
	private Image hiddenTurtleImage =  new Image(getClass().getClassLoader().getResourceAsStream("Blank_Turtle.png"));
	private Image primaryImage;
	private ImageView turtleImageView;
	private Tooltip TurtleInfoToolTip;

	private int turtleSize;

	private double turtleDirection;
	private Point2D myTurtleLocation;

	private boolean hiddenStatus;
	private boolean penDrawing;
	private Pen myPen;

	private ShapeImage myShape;
	private List<StampMark> myStamps;
	
	private int myID;
	
	private boolean activeStatus;

	public Turtle () {
		this(0, 0);
	}
	
	public Turtle(double xLoc, double yLoc){
		turtleImage = new Image(getClass().getClassLoader().getResourceAsStream("MaoriTurtle.jpg"));
		myStamps = new ArrayList<StampMark>();

		primaryImage = turtleImage;

		myTurtleLocation = new Point2D(xLoc, yLoc);
		turtleSize = 100;
		myPen = new Pen();
		turtleDirection = 0.0;

		penDrawing = true;
		hiddenStatus = false;

		setImageView(turtleImage);
		
		myID = 0;

		setShape(0);
		
		activeStatus = true;

		initializeImgViewInfoScreen();
	}


	/**
	 * Turtle Commands: These MUST MATCH the right hand commands listed in interpreter.resources.TurtleCommands
	 */

	/**
	 * moves turtle backward in its current heading by pixels distance
	 * @param dist
	 * @return the value of pixels
	 */
	public double backward (double pixels) {
		move(-1*pixels);
		return pixels;
	}

	/**
	 * erases turtle's trails and sends it to the home position
	 * @return the distance turtle moved
	 */
	public double clearScreen () {
		double distanceMoved = myTurtleLocation.distance(0, 0);
		myTurtleLocation = new Point2D(0,0);
		// Reset Turtle Direction
		turtleDirection = 0.0;

		// TODO: Make sure front end makes use of this
		myPen.clearMarkings();
		myStamps.clear();

		return distanceMoved;
	}

	/**
	 * moves turtle forward in its current heading by pixels distance
	 * @param pixels
	 * @return the value of pixels
	 */
	public double forward (double pixels) {
		move(pixels);
		return pixels;
	}

	/**
	 * makes turtle invisible
	 * @return 0
	 */
	public double hide () {
		primaryImage = hiddenTurtleImage;
		hiddenStatus = true;
				
		return 0;
	}

	/**
	 * moves turtle to the center of the screen (0 0)
	 * @return the distance turtle moved
	 */
	public double setHome () {
		double distanceMoved = myTurtleLocation.distance(0, 0);

		Point2D initialLocation = myTurtleLocation;
		Point2D finalLocation = new Point2D(0, 0);

		myTurtleLocation = finalLocation;

		myPen.addMarking(new Mark(initialLocation, finalLocation, myPen.getColor(), myPen.getThickness(), myPen.getMarkType()));

		return distanceMoved;
	}

	/**
	 * turns turtle counterclockwise by degrees angle
	 * @param degrees
	 * @return the value of degrees
	 */
	public double left (double degrees) {
		turn(degrees*-1);
		return degrees;
	}

	/**
	 * puts pen down such that when the turtle moves, it leaves a trail
	 * @return 1
	 */
	public double setPenDown () {

		penDrawing = true;
		myPen.setWriting(true);
		return 1;
	}

	/**
	 * puts pen up such that when the turtle moves, it does not leave a trail
	 * @return 0
	 */
	public double setPenUp () {

		penDrawing = false;
		myPen.setWriting(false);
		return 0;
	}

	/**
	 * turns turtle clockwise by degrees angle
	 * @param degrees
	 * @return the value of degrees
	 */
	public double right (double degrees) {
		turn(degrees*1);
		return degrees;
	}

	/**
	 * turns turtle to an absolute heading
	 * @param degrees
	 * @return number of degrees moved
	 */
	public double setHeading (double degrees) {

		double degreesMoved = Math.abs(degrees - turtleDirection);
		turtleDirection = fixAngle(degrees);
		return degreesMoved;
	}

	/**
	 * moves turtle to an absolute screen position, where (0, 0) is the center of the screen
	 * @param x
	 * @param y
	 * @return returns the distance turtle moved
	 */
	public double setPosition (double x, double y) {
		double distanceTraveled = myTurtleLocation.distance(x, y);

		Point2D initialLocation = myTurtleLocation;
		Point2D finalLocation = new Point2D(x, y);

		myTurtleLocation = finalLocation;

		myPen.addMarking(new Mark(initialLocation, finalLocation, myPen.getColor(), myPen.getThickness(), myPen.getMarkType()));

		return distanceTraveled;
	}

	/**
	 * makes turtle visible
	 * @return 1
	 */
	public double show () {
		primaryImage = turtleImage;
		hiddenStatus = false;
		
		return 1;
	}

	/**
	 * turns turtle to face the point (x, y), where (0, 0) is the center of the screen
	 * @param x
	 * @param y
	 * @return the number of degrees turtle turned
	 */
	public double setTowards (double x, double y) {

		if(x != myTurtleLocation.getX() && y != myTurtleLocation.getY()){
			double dy = y - myTurtleLocation.getY();
			double dx = x - myTurtleLocation.getX();
			double newDir = Math.toDegrees(Math.atan(dy/dx));
			setHeading(newDir);
			return newDir;
		}
		else
			return turtleDirection;

	}

	/**
	 * @return the turtle's heading in degrees
	 */
	public double getHeading () {
		return turtleDirection;
	}	

	/**
	 * @return 1 if turtle's pen is down, 0 if it is up
	 */
	public double isPenDown () {
		if (penDrawing) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * @return 1 if turtle is showing, 0 if it is hiding
	 */
	public double isShowing () {
		if (hiddenStatus) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * @return the turtle's X coordinate from the center of the screen
	 */
	public double getXLoc(){
		return myTurtleLocation.getX();
	}

	/**
	 * @return the turtle's Y coordinate from the center of the screen
	 */
	public double getYLoc(){
		return myTurtleLocation.getY();
	}

	/**
	 * sets color of the pen to that represented by index
	 * returns given index
	 * @param index
	 * @return
	 */
	public double setPenColor (double index) {
		int myIndex = (int) index;
		Color color = ColorBundle.getColor(myIndex);

		if (color != null) {
			myPen.setColor(color);
		}

		return index;
	}

	public double setPenSize (double pixels) {
		if (pixels < 0) { return pixels;}
		myPen.setThickness(pixels);
		return pixels;
	}

	public double setShape (double index) {
		int myIndex = (int) index;

		myShape = ShapeBundle.getShape(myIndex);

		turtleImageView.setClip(myShape.makeShape(this));

		return index;
	}

	public double getShapeIndex () {
		return ShapeBundle.getIndex(myShape);
	}
	
	public double getPenColor () {
		int r = (int) Math.floor(myPen.getColor().getRed()*255);
		int g = (int) Math.floor(myPen.getColor().getGreen()*255);
		int b = (int) Math.floor(myPen.getColor().getBlue()*255);
		
		int rgbValue = b*256*256 + g*256 + r;
		return rgbValue;
	}
	
	public double stamp () {
		myStamps.add(new StampMark(myTurtleLocation, myPen.getColor(), turtleImageView, myShape.makeShape(this), turtleDirection));
		return getShapeIndex();
	}
	
	public double clearStamps () {
		if (myStamps.size() > 0) {
			myStamps.clear();
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	public void move(double dist){
		double xchange = dist * Math.cos((turtleDirection*Math.PI)/180);
		double ychange = dist * Math.sin((turtleDirection*Math.PI)/180);

		double newx = this.getXLoc() + xchange;
		double newy = this.getYLoc() + ychange;

		Point2D initialLocation = myTurtleLocation;
		Point2D finalLocation = new Point2D(newx, newy);
		myPen.addMarking(new Mark(initialLocation, finalLocation, myPen.getColor(), myPen.getThickness(), myPen.getMarkType()));

		addXandY(newx, newy);

	}

	private void addXandY(double x, double y){
		myTurtleLocation = new Point2D(x,y);
	}

	public void turn(double dir){
		double direction = this.getHeading() + dir;
		direction = fixAngle(direction);
		setDirection(direction);
	}

	public void setDirection (double newDir) {
		turtleDirection = newDir;
	}

	private double fixAngle(double direction) {
		direction = direction % 360;
		if(direction < 0){
			direction = direction += 360;
		}
		return direction;
	}

	public Image getPrimaryImage () {
		return primaryImage;
	}

	public Image getTurtleImage() {
		return turtleImage;
	}
	public void setTurtleImage(Image turtleImage) {
		this.turtleImage = turtleImage;
	}

	public void setTurtleSize (int newTurtleSize) {
		turtleSize = newTurtleSize;
	}

	public int getTurtleSize() {
		return turtleSize;
	}

	public Pen getPen () {
		return myPen;
	}

	public ImageView getImageView(){
		return turtleImageView;
	}
	
	public List<StampMark> getStampMarks() {
		return myStamps;
	}

	public void update () {
		updateTooltip();
		setChanged();
		notifyObservers();
	}

	public double getTurtleDirection(){
		return turtleDirection;
	}

	private void initializeImgViewInfoScreen(){
		TurtleInfoToolTip = new Tooltip(generateTurtleInfo());
		Tooltip.install(turtleImageView, TurtleInfoToolTip);
	}

	// TODO Change this to modify turtle info display
	// TODO Add color of pen
	private String generateTurtleInfo() {
		String position = "Turtle position: (" + getXLoc() + ", " + getYLoc() + ") \n";
		String penUp = "Pen down: " + (isPenDown() == 1) + "\n";
		String penColor = "Pen color: " + (myPen.getColor()) + "\n";
		return position + penUp + penColor;
	}

	private void updateTooltip(){
		TurtleInfoToolTip.setText(generateTurtleInfo());
	}

	public double getID() {
		return myID;
	}

	public void setID(int myID) {
		this.myID = myID;
	}
	
	public double clearMarkings () {
		myPen.clearMarkings();
		return 1;
	}

	public boolean isActive () {
		return activeStatus;
	}
	
	public void setActiveStatus (boolean newStatus) {
		activeStatus = newStatus;
	}
	
	public void setImage(Image image){
		//turtleImageView.setImage(image);
		turtleImage = image;
	}
	
	private void setImageView(Image image){
		turtleImageView = new ImageView(image);
		turtleImageView.setFitHeight(turtleSize);
		turtleImageView.setFitWidth(turtleSize);
	}
	
}

