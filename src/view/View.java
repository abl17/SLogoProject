package view;

import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import controller.Controller;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import view.util.PropertiesManager;

public class View implements Observer{
	
	private Controller myController;
	private VariableList myVariableList;
	private Console myConsole;
	private UserCommandsSection myUserCommandsSection;
	private Stage myStage;
	private double DEFAULT_HEIGHT;
	private double DEFAULT_WIDTH;
	private String DEFAULT_COLOR;
	private double DEFAULT_SECTION_HEIGHT;
	private double DEFAULT_SECTION_WIDTH;
	private int numberOfWorkTabs;
	private TabPane myTabPane;
	private TurtleScreen myTurtleScreen;
	private PropertiesManager myProperties;
	
	public View(Stage stage, Controller controller){	
		initializeDefaultSettings();
		setController(controller);
		Scene myScene = initializeSceneWithTabs();
		initializeStage(stage, myScene);
	}
	
	private void setController(Controller controller){
		myController = controller;
	}
	
	private void initializeDefaultSettings(){
		myProperties = new PropertiesManager("resources.ViewSections.view");
		DEFAULT_HEIGHT = myProperties.getDoubleProperty("Height");
		DEFAULT_WIDTH = myProperties.getDoubleProperty("Width");
		DEFAULT_COLOR = myProperties.getProperty("Color");
		DEFAULT_SECTION_WIDTH = myProperties.getDoubleProperty("SectionWidth");
		DEFAULT_SECTION_HEIGHT = myProperties.getDoubleProperty("SectionHeight");
	}
	
	private void initializeStage(Stage stage, Scene root){	
		if (stage != null) {
			stage.setTitle(myProperties.getProperty("Title"));
			stage.setScene(root);
			stage.setResizable(false);
			stage.show();
		}
		myStage = stage;
	}
	
	private Scene initializeSceneWithTabs(){
		numberOfWorkTabs = 1;
		VBox myMainStructure = createMainStructure();
		Scene scene = new Scene(myMainStructure, DEFAULT_WIDTH, DEFAULT_HEIGHT,Color.valueOf(DEFAULT_COLOR));
		String css = this.getClass().getResource(myProperties.getProperty("CSSstyle")).toExternalForm(); 	
		scene.getStylesheets().add(css);
		return scene;
	}
	
	private VBox createMainStructure(){
		VBox myMainStructure = new VBox();
		TopMenu myMenu = new TopMenu(myController);
		myMenu.addObserver(this);
		myTabPane = new TabPane();
		myTabPane.getTabs().add(createNewTab());
		myMainStructure.getChildren().addAll(myMenu.getMenuBar(),myTabPane);
		return myMainStructure;
	}

	private Tab createNewTab() {
		BorderPane root = new BorderPane();
		initializeBorderPaneElements(root);
		
		Tab tab = new Tab();
		String tabName = myProperties.getProperty("Tab") + numberOfWorkTabs;
		tab.setText(tabName);
		tab.setContent(root);
		
		myController.addProject(tabName);
		tab.setOnSelectionChanged(e -> {myController.switchProject(tab.getText());});
		
		if(numberOfWorkTabs == 1){
			tab.setClosable(false);
		}
		
		return tab;
	}
	
	private void initializeBorderPaneElements(BorderPane root) {
		
		myTurtleScreen = new TurtleScreen();	
		myConsole = new Console(this, myProperties.getProperty("ConsoleTitle"), myProperties.getDoubleProperty("ConsoleHeight"));
		myUserCommandsSection = new UserCommandsSection(this,DEFAULT_SECTION_WIDTH, DEFAULT_SECTION_HEIGHT);
		myVariableList = new VariableList(this, DEFAULT_SECTION_WIDTH, DEFAULT_SECTION_HEIGHT);
		myVariableList.setConsole(myConsole);
		myUserCommandsSection.setConsole(myConsole);
		placeNodesOnBorderPane(root, myTurtleScreen, myVariableList, myUserCommandsSection, myConsole);
	}
	
	private void placeNodesOnBorderPane(BorderPane root, Node center, Node left, Node right, Node bottom){
		root.setCenter(center);
		root.setLeft(left);
		root.setRight(right);
		root.setBottom(bottom);
	}
	
	public UserCommandsSection getUserCommandSection(){
		return myUserCommandsSection;
	}
	public VariableList getMyVariableList() {
		return myVariableList;
	}
	public void setMyVariableList(VariableList myVariableList) {
		this.myVariableList = myVariableList;
	}
	public Console getMyConsole() {
		return myConsole;
	}
	public void setMyConsole(Console myConsole) {
		this.myConsole = myConsole;
	}
	
	public double getDefaultHeight(){
		return DEFAULT_HEIGHT;
	}
	
	public double getDefaultWidth(){
		return DEFAULT_WIDTH;
	}
	
	public Controller getController(){
		return myController;
	}
	
	public Tab getCurrentTab() {
		return myTabPane.getTabs().get(myTabPane.getSelectionModel().getSelectedIndex());
	}
	
	public Stage getStage () {
		return myStage;
	}
	
	public void displayErrorMessage(String error){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(myProperties.getProperty("ErrorTitle"));
		alert.setHeaderText(myProperties.getProperty("ErrorHeader"));
		alert.setContentText(error);
		alert.showAndWait();
	}


	public TurtleScreen getMyAlterTurtleScreen() {

		return myTurtleScreen;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		numberOfWorkTabs++;
		myTabPane.getTabs().add(createNewTab());
	}

}
