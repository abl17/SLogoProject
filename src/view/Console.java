

package view;

import java.util.Observable;
import java.util.Observer;

import controller.Controller;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import view.Commands.ConsoleCommand;
import view.util.PropertiesManager;

/**
 * 
 * @author emanuele macchi
 *
 */


public class Console extends ViewSection implements Observer{
	
	private TextArea userInput;
	private int noInputs;
	private Controller myController;
	private PropertiesManager myProperties;
	
	public Console(View myView, String title, double height){
		super(title, myView.getDefaultWidth(), height);
		myProperties = new PropertiesManager("resources.ViewSections.console");
		initializeConsoleView(myView.getDefaultWidth());
		this.setPrefHeight(myView.getDefaultHeight()/4);
		myController = myView.getController();
		noInputs=0;
	}
	
	private void updateCommandHistory(String input){
		ConsoleCommand currentCommand = new ConsoleCommand(input, noInputs, this);
		addNodeToPane(currentCommand.returnCommandBlock());
	}
	
	private void initializeConsoleView(double width){
		HBox myInputBox = new HBox();
		initializeTextInput();
		Button run = createRunButton();
		myInputBox.getChildren().addAll(userInput, run);
		this.getChildren().add(myInputBox);
		
	}

	private Button createRunButton() {
		Button run = new Button(myProperties.getProperty("ConsoleButton"));
		run.setOnAction(event -> informParser());
		run.setPrefSize(myProperties.getDoubleProperty("ButtonWidth"), myProperties.getDoubleProperty("ButtonHeight"));
		return run;
	}

	private void initializeTextInput() {
		userInput = new TextArea();
		userInput.setPrefSize(myProperties.getDoubleProperty("ConsoleWidth"), myProperties.getDoubleProperty("InputAreaHeight"));
		userInput.setEditable(true);
	}
	
	private void resetInputField(){
		userInput.clear();
	}
	
	private void informParser(){
		String input = userInput.getText();
		resetInputField();
		sendInputToController(input);
	}
	

	private void sendInputToController(String input) {
		noInputs++;
		updateCommandHistory(input);
		myController.executeCommand(input);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		String command = (String) arg1;
		sendInputToController(command);
	}
	
	public void appendTextToConsole(String code){
		userInput.appendText(code);
	}
}
