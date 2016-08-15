package view;

import java.util.Observable;
import java.util.Observer;

import controller.Project;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import view.Commands.UserCommand;
import view.util.PropertiesManager;

public class UserCommandsSection extends ViewSection implements Observer{
	
	Console myConsole;
	PropertiesManager myProperties;
	
	public UserCommandsSection(View myView, double widht, double height){
		super("User-Defined Commands", widht, height);
		myProperties = new PropertiesManager("resources.ViewSections.usercommands");
		myConsole = myView.getMyConsole();
		addClearLinesButton(myView);
	}
	
	private void addClearLinesButton(View myView) {
		Button clearLinesButton = new Button(myProperties.getProperty("ClearLinesButtonName"));
		clearLinesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event-> clearLines(myView));
		this.getChildren().add(clearLinesButton);
		
	}
	
	private void clearLines(View myView){
		((TurtleScreen) myView.getMyAlterTurtleScreen()).clearLines();
		((TurtleScreen) myView.getMyAlterTurtleScreen()).resetLineMarks(
				myView
				.getController()
				.getCurrentProject()
				.getTurtleBundle());
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Project pm = (Project) arg0;
		removeAllNodes();
		
		for (String userInstruction : pm.getUserInstructionMap().keySet()) {
			updateScrollPane(userInstruction, userInstruction);
		}
	}
	
	private void updateScrollPane(String commandName, String command){
		UserCommand currentCommand = new UserCommand(commandName, command, myConsole);
		addNodeToPane(currentCommand.getButton());
	}
	
	public void setConsole(Console console){
		myConsole = console;
	}
	
}
