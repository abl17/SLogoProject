package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;

/**
 * 
 * @author emanuele
 * 
 * TODO move everything to lambda functions 
 *
 */


public abstract class ViewSection extends VBox{
	
	private ScrollPane myPane;
	private double widthDimension;
	private double heightDimension;
	
	public ViewSection(String nameOfSection, Double width, Double height){
		widthDimension = width;
		heightDimension = height;
		this.getStyleClass().add("vbox");
		crateTitleLabel(nameOfSection, width);
		initializeScrollingPane(height);
	}

	private void crateTitleLabel(String nameOfSection, Double width) {
		Label title = new Label(nameOfSection);
		title.getStyleClass().add("section-title");
		title.setPrefWidth(width);
		this.getChildren().add(title);
	}

	public void initializeScrollingPane(Double height){
		myPane = new ScrollPane();
		myPane.getStyleClass().add("scrollpane-style");
		myPane.setPrefHeight(height);
		disablePaneXScrolling();
		myPane.setContent(createContentBox());
		this.getChildren().add(myPane);
	}

	private void disablePaneXScrolling() {
		myPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		myPane.addEventFilter(ScrollEvent.SCROLL, event -> 
		{
			if (event.getDeltaX() != 0) {
                event.consume();
            }
		});
	}

	private VBox createContentBox() {
		VBox content = new VBox();
		content.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				myPane.setVvalue((Double)newValue );  
			}
		});
		return content;
	}

	public void addNodeToPane(Node node){
		((VBox) getPaneContent()).getChildren().add(node);
	}
	
	public void removeAllNodes(){
		((VBox) getPaneContent()).getChildren().clear();
	}
	
	public void clearScrollPane () {
		this.getChildren().remove(getPane());
	}
	
	private Node getPaneContent(){
		return myPane.getContent();
	}
	
	public ScrollPane getPane () {
		return myPane;
	}
	
	public double getWidthDimension () {
		return widthDimension;
	}
	
	public double getHeightDimension () {
		return heightDimension;
	}
}
