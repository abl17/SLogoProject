package view.Commands;

import java.util.Observable;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.Console;
import view.util.PropertiesManager;

public class ConsoleCommand extends Observable{
	
	private VBox commandBlock;
	private HBox commandOptions;
	private Label commandCode;
	private String command;
	private PropertiesManager myProperties;
	
	public ConsoleCommand(String input, int noInput, Console myConsole){
		addObserver(myConsole);
		myProperties = new PropertiesManager("resources.ViewSections.commands");
		createCommandOptions(noInput);
		createCommandCode(input);
		commandBlock = new VBox(myProperties.getDoubleProperty("VBoxSpacing"), commandOptions, commandCode);
	}

	private void createCommandOptions(int inputNo) {
		commandOptions = new HBox(myProperties.getDoubleProperty("HBoxSpacing"));
		commandOptions.setFillHeight(true);
		commandOptions.setAlignment(Pos.CENTER_LEFT);
		Button executeCommand = new Button(myProperties.getProperty("CommandButton"));
		Label userInputNo = new Label(myProperties.getProperty("UserInputText") + inputNo);
		executeCommand.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> notifyConsole());
		commandOptions.getChildren().addAll(userInputNo, executeCommand);
		
	}

	private void notifyConsole() {
		setChanged();
		notifyObservers(command);
	}

	private void createCommandCode(String input) {
		command = input;
		commandCode = new Label(input);
	}
	
	public VBox returnCommandBlock(){
		return commandBlock;
	}
}
