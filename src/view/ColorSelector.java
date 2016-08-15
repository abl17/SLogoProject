package view;

import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ColorSelector extends Dialog<String>{
	
	VBox content; 
	Color currentColor;
	
	public ColorSelector(){
		this.setTitle("Select color");
		content = new VBox();
		initializeBasicButtons();
		addColorPicker();
		getDialogPane().setContent(content);
	}
	
	private void addColorPicker(){
		ColorPicker myPicker = new ColorPicker();
		currentColor = myPicker.getValue();
		content.getChildren().add(myPicker);
		myPicker.setOnAction(event -> {
			currentColor = myPicker.getValue();
		});
	}
	
	private void initializeBasicButtons(){
		
		ButtonType selectColor = new ButtonType("Apply", ButtonData.OK_DONE);
		this.getDialogPane().getButtonTypes().addAll(selectColor, ButtonType.CANCEL);
		this.setResultConverter(dialogButton ->{
			return getColorString();
		});
		
	}
	
	private String getColorString(){
		
		int r = (int) Math.floor(currentColor.getRed()*255);
		int g = (int) Math.floor(currentColor.getGreen()*255);
		int b = (int) Math.floor(currentColor.getBlue()*255);
		
		int rgbValue = b*256*256 + g*256 + r;
		
		return Integer.toString(rgbValue);
	}

}
