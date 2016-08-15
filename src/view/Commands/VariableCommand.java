package view.Commands;

import java.util.Observable;
import java.util.Optional;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import view.Console;
import view.util.PropertiesManager;

/**
 * 
 * @author emanuele
 * 
 */

public class VariableCommand extends Observable{
	
	private Button myVariableButton;
	private String myCommand;
	private String myVariableName;
	private double myValue;
	private PropertiesManager myProperties;
	
	public VariableCommand(String variableName, double variableValue,Console myConsole){
		addObserver(myConsole);
		myProperties = new PropertiesManager("resources.ViewSections.commands");
		myCommand = variableName + " = " + variableValue;
		myVariableName = variableName;
		myValue = variableValue;
		initializeButton(variableName + " = " + variableValue);
	}
	
	private void initializeButton(String commandName) {
		myVariableButton = new Button(commandName);
		myVariableButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> openForModifications());
		myVariableButton.setPrefWidth(myProperties.getDoubleProperty("ButtonWidth"));
		myVariableButton.setAlignment(Pos.CENTER_LEFT);
	}
	
	private void openForModifications(){
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Text Input Dialog");
		dialog.setContentText("Please the new value:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			String newChoice = result.get();
			try  
			  {  
			    double value = Double.parseDouble(newChoice);
			    if(value != myValue){
			    	myValue = value;
			    	myVariableButton.textProperty().set(myVariableName + " = " + myValue);
				    notifyConsole();
			    }
			  }  
			  catch(NumberFormatException nfe)  
			  {  
			    generateAlert();
			  }  
		}
	}
	
	private void generateAlert(){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(myProperties.getProperty("ErrorTitle"));
		alert.setHeaderText(myProperties.getProperty("ErrorMessage"));
		alert.showAndWait();
	}
		
	private void notifyConsole() {
		myCommand = "set " + myVariableName + " " + myValue;
		setChanged();
		notifyObservers(myCommand);
	}
	
	public Button getButton(){
		return myVariableButton;
	}

}
