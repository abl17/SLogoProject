package view;

import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.input.MouseEvent;
import model.Variables;
import view.Commands.VariableCommand;
import view.util.PropertiesManager;


/**
 * 
 * @author michaelogez + emanuelemacchi
 *
 */


public class VariableList extends ViewSection implements Observer{
	Console myConsole;
	PropertiesManager myProperties;
	
	public VariableList(View myView, double width, double height){
		super("Variables", width, height);
		myProperties = new PropertiesManager("resources.ViewSections.variablelist");
		addColorSelectorButton();
		addShapeSelectorButton();
	}
	
	private void addColorSelectorButton() {
		Button selectColor = new Button(myProperties.getProperty("ColorButton"));
		selectColor.addEventHandler(MouseEvent.MOUSE_CLICKED, event-> createColorDialog());
		this.getChildren().add(selectColor);
	}
	
	private void addShapeSelectorButton(){
		Button selectShape = new Button(myProperties.getProperty("ShapeButton"));
		selectShape.addEventHandler(MouseEvent.MOUSE_CLICKED, event-> createShapeDialog());
		this.getChildren().add(selectShape);
	}
	
	private void createDialog(Dialog dialog){
		Optional<String> result = dialog.showAndWait();
		result.ifPresent(input -> myConsole.appendTextToConsole(input));
	}
	
	private void createShapeDialog(){
		ResourceBundle myResources = ResourceBundle.getBundle(myProperties.getProperty("ImageProperties"));
		List<String> possibleShapes = getPossibleShapes(myResources);
		ChoiceDialog<String> myShapeSelectorDialog = new ChoiceDialog<String>(possibleShapes.get(0), possibleShapes);
		myShapeSelectorDialog.setResultConverter(shape ->{
			return myResources.getString(myShapeSelectorDialog.getSelectedItem());
		});
		createDialog(myShapeSelectorDialog);
	}
	
	private void createColorDialog(){
		ColorSelector myColorSelectorDialog = new ColorSelector();
		createDialog(myColorSelectorDialog);
	}
	
	private List<String> getPossibleShapes(ResourceBundle resources){
		String[] shapes = resources.getString("Shapes").split(" ");
		List<String> poop = Arrays.asList(shapes);
		for(String s: poop){
			System.out.println(s);
		}
		return Arrays.asList(shapes);
	} 
	
	@Override
	public void update(Observable arg0, Object arg1) {
		Variables v = (Variables) arg0;
		
		removeAllNodes();
		
		for (String variable : v.getVariableList()) {
			double value = v.getVariable(variable);
			updateScrollPane(variable, value);
		}
	}
	
	private void updateScrollPane(String variableName, double variableValue){
		VariableCommand currentCommand = new VariableCommand(variableName, variableValue, myConsole);
		addNodeToPane(currentCommand.getButton());
	}
	
	public void setConsole(Console console){
		myConsole = console;
	}
}

