package view.Commands;

import java.util.Observable;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import view.Console;
import view.util.PropertiesManager;


/**
 * 
 * @author emanuele
 *
 */

public class UserCommand extends Observable{
	
	private Button myCommandButton;
	private String myCommand;
	private PropertiesManager myProperties;
	
	public UserCommand(String commandName, String command, Console myConsole){
		addObserver(myConsole);
		myProperties = new PropertiesManager("resources.ViewSections.commands");
		myCommand = command;
		initializeButton(commandName);
	}
	
	private void initializeButton(String commandName) {
		myCommandButton = new Button(commandName);
		myCommandButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> notifyConsole());
		myCommandButton.setPrefWidth(myProperties.getDoubleProperty("ButtonWidth"));
		myCommandButton.setAlignment(Pos.CENTER_LEFT);
	}
		
	private void notifyConsole() {
		setChanged();
		notifyObservers(myCommand);
	}
	
	public Button getButton(){
		return myCommandButton;
	}

}
