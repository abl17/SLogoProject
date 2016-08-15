package view;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import controller.Controller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import view.util.PropertiesManager;

/**
 * 
 * @author Emanuele + Austin
 *
 */

public class TopMenu extends Observable{

	private static final String LANGUAGE_LIST_SEPARATOR = ",";
	private String AVAILABLE_LANGUAGES = "AvailableLanguages";
	private String LANGUAGE_RESOURCE_FOLDER = "resources.languages.AvailableLanguages";
	private ResourceBundle availableLanguagesBundle;
	private PropertiesManager myProperties; 
	private MenuBar myMenuBar;
	private Controller Controller;

	public TopMenu(Controller myController){
		myProperties = new PropertiesManager("resources.ViewSections.menubar");
		LANGUAGE_RESOURCE_FOLDER = myProperties.getProperty("AvailableLanguagesURL");
		availableLanguagesBundle = ResourceBundle.getBundle(LANGUAGE_RESOURCE_FOLDER);
		myMenuBar = new MenuBar();
		initializeFileOption();
		initializeOptionsOption(myController);
		initializeHelpOption();
		Controller = myController;
	}

	private void initializeOptionsOption(Controller myController){
		Menu optionsMenu = new Menu(myProperties.getProperty("Options"));
		MenuItem language = new MenuItem(myProperties.getProperty("Language"));
		language.setOnAction(event ->  {
			launchLanguageDialog(myController);
		});
		optionsMenu.getItems().add(language);
		myMenuBar.getMenus().addAll(optionsMenu);
	}

	private void launchLanguageDialog(Controller myController) {
		List<String> languageChoices = new ArrayList<>();

		String[] myLanguages = availableLanguagesBundle.getString(AVAILABLE_LANGUAGES).split(LANGUAGE_LIST_SEPARATOR);
		languageChoices.addAll(Arrays.asList(myLanguages));

		ChoiceDialog<String> languageDialog = new ChoiceDialog<>("English", languageChoices);
		languageDialog.setTitle(myProperties.getProperty("LanguageDialogTitle"));
		languageDialog.setContentText(myProperties.getProperty("LanguageDialogContent"));

		Optional<String> result = languageDialog.showAndWait();
		if (result.isPresent()){
			myController.setLanguage(result.get());
		}
	}

	private void initializeFileOption() {
		Menu fileMenu = new Menu(myProperties.getProperty("File"));
		MenuItem newTabItem = new MenuItem(myProperties.getProperty("NewTab"));
		setNewTabCreation(newTabItem);
		MenuItem saveMenuItem = createSaveMenuItem();	
		MenuItem loadMenuItem = createLoadMenuItem();
		MenuItem exitMenuItem = createExitMenuItem();
		MenuItem saveFuncsVarsMenuItem = createSaveFunVarMenuItem();
		MenuItem loadFuncsVarsMenuItem = createLoadFunVarMenuItem();
		fileMenu.getItems().addAll(newTabItem, saveMenuItem, saveFuncsVarsMenuItem, loadMenuItem, loadFuncsVarsMenuItem, exitMenuItem);
		myMenuBar.getMenus().addAll(fileMenu);
	}

	private MenuItem createLoadFunVarMenuItem() {
		MenuItem loadFuncsVarsMenuItem = new MenuItem(myProperties.getProperty("LoadFunVar"));
		loadFuncsVarsMenuItem.setOnAction(event -> {
			TextInputDialog dialog = new TextInputDialog("default-name");
			initializeDialog("Load", dialog);
			Optional<String> result = dialog.showAndWait();
			result.ifPresent(name -> loadUserCommandsandVars(name));
		});
		return loadFuncsVarsMenuItem;
	}

	private MenuItem createSaveFunVarMenuItem() {
		MenuItem saveFuncsVarsMenuItem = new MenuItem(myProperties.getProperty("SaveFunVar"));
		saveFuncsVarsMenuItem.setOnAction(event -> {
			TextInputDialog dialog = new TextInputDialog("default-name");
			initializeDialog("Save", dialog);
			Optional<String> result = dialog.showAndWait();
			result.ifPresent(name -> saveUserCommandsandVars(name));
		});
		return saveFuncsVarsMenuItem;
	}

	private void initializeDialog(String type, Dialog dialog){
		dialog.setTitle(myProperties.getProperty(type + "DialogTitle"));
		dialog.setHeaderText(myProperties.getProperty(type + "DialogHeader"));
		dialog.setContentText(myProperties.getProperty(type + "DialogContent"));
	}
	private MenuItem createExitMenuItem() {
		MenuItem exitMenuItem = new MenuItem(myProperties.getProperty("Exit"));
		exitMenuItem.setOnAction(event -> {
				Platform.exit();
				System.exit(0);
		});
		return exitMenuItem;
	}

	private MenuItem createSaveMenuItem() {
		MenuItem saveMenuItem = new MenuItem(myProperties.getProperty("SaveAll"));
		saveMenuItem.setOnAction(event -> {
				File file = obtainWorkplaceFile();
				if (file != null) {
					saveWorkspace(file);
				}	    		
		});
		return saveMenuItem;
	}

	private MenuItem createLoadMenuItem () {
		MenuItem loadMenuItem = new MenuItem(myProperties.getProperty("Load"));
		loadMenuItem.setOnAction(event -> {
				File file = obtainWorkplaceFile();
				if (file != null) {
					loadWorkspace(file);
				}	    		
		});
		return loadMenuItem;
	}

	private File obtainWorkplaceFile() {
		FileChooser fileChooser = new FileChooser();

		String userDirectoryString = System.getProperty("user.home");
		File userDirectory = new File(userDirectoryString);
		fileChooser.setInitialDirectory(userDirectory);

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		File file = fileChooser.showOpenDialog(null);
		return file;
	}

	private void loadUserCommandsandVars(String filename){

		try {
			Controller.loadVars(filename);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Do we need this?
	 * @param filename
	 */
	private void saveWorkspace(String filename) {
		saveWorkspace (new File(filename));
	}

	private void saveWorkspace(File file) {
		try {
			String[] tokens = file.getName().split("\\.");
			String fileShortName = tokens[0];
			String originalName = Controller.getCurrentProject().getName();

			Controller.getCurrentProject().setName(fileShortName);
			Controller.saveProject(Controller.getCurrentProject(), file);
			Controller.getView().getCurrentTab().setText(fileShortName);
			Controller.addProject(fileShortName, Controller.getCurrentProject());
			Controller.removeProject(originalName);
		} catch (DOMException | IllegalArgumentException | IllegalAccessException | ParserConfigurationException
				| TransformerException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void loadWorkspace (File file) {
		try {
			Controller.loadProject(file);
			
			Controller.getView().getCurrentTab().setText(Controller.getCurrentProject().getName());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException
				| ClassNotFoundException | NoSuchMethodException | InstantiationException | InvocationTargetException
				| ParserConfigurationException | SAXException | IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private void saveUserCommandsandVars(String filename){
		Controller.getProject().saveFuncsAndVars(filename);
	}
	private void setNewTabCreation(MenuItem newTab){
		newTab.setOnAction(event -> {
			setChanged();
			notifyObservers();
		});
	}

	private void initializeHelpOption() {
		Menu helpMenu = new Menu("Help");
		MenuItem commandsMenuItem = new MenuItem("Commands");
		helpMenu.getItems().add(commandsMenuItem);
		helpMenu.setOnAction(event -> {
				launchCommandHelpDialog();
		});
		myMenuBar.getMenus().addAll(helpMenu);
	}

	private void launchCommandHelpDialog(){
		Dialog helpCommands = new Dialog();
		helpCommands.setTitle(myProperties.getProperty("HelpCommandsTitle"));
		HTMLdisplayer myHTML = new HTMLdisplayer(myProperties.getProperty("HelpCommandsURL"),
				myProperties.getDoubleProperty("HelpWindowWidth"), 
				myProperties.getDoubleProperty("HelpWindowHeight"));
		helpCommands.getDialogPane().setContent(myHTML);
		ButtonType loginButtonType = new ButtonType("DONE", ButtonData.OK_DONE);
		helpCommands.getDialogPane().getButtonTypes().add(loginButtonType);
		helpCommands.getDialogPane().setPrefSize(myProperties.getDoubleProperty("HelpWindowWidth"), 
				myProperties.getDoubleProperty("HelpWindowHeight"));
		helpCommands.showAndWait();
	}

	public MenuBar getMenuBar(){
		return myMenuBar;
	}

}
