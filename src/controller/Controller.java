package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import controller.util.XMLLoader;
import controller.util.XMLSaver;
import interpreter.ParsingTable;
import interpreter.blocks.RootBlock;
import interpreter.exceptions.blocks.BlockException;
import javafx.stage.Stage;
import model.exceptions.NumberException;
import view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.io.IOException;

public class Controller implements IController {
	
	private static final String DEFAULT_LANGUAGE = "English";
	private static final String DEFAULT_NAME = "Workplace 1";
	
	private Project myCurrentProject;
	private Map<String, Project> projectMap;
	private View myView;
	
	public Controller(Stage primaryStage){

		myCurrentProject = new Project(DEFAULT_LANGUAGE);
		
		projectMap = new HashMap<String, Project>();
		projectMap.put(DEFAULT_NAME, myCurrentProject);

		try{
			initializeFileOfCommands();
		}
		catch (IOException e){
			System.out.println("CANNOT ERASE FILE");
		}


		myView = new View(primaryStage, this);
		ParsingTable.getThis().setUpLanguageBundle(myCurrentProject.getLanguage());
		ParsingTable.getThis().setProject(myCurrentProject);
		
		initializeObservers(myCurrentProject, myView);
	}

	/**
	 * @throws IOException
	 */
	private void initializeFileOfCommands() throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(myCurrentProject.getName()+".txt", false)));
		out.flush();
		out.close();
	}
	
	@Override
	public void executeCommand(String command) {
		
		List<RootBlock> n;
		try {

			n = myCurrentProject.parse(command);

			
			if (n != null) { 
				for (int i = 0; i < n.size(); i++) {
					System.out.println(n.get(i).evaluate(myCurrentProject));
				}
			}
			
			writeCommandToFile(command);
			
		} catch (BlockException | NumberException | IOException e) {
			System.out.println(e.getMessage());
			myView.displayErrorMessage(e.getMessage());
		}
		
	}

	/**
	 * @param command
	 * @throws IOException
	 */
	private void writeCommandToFile(String command) throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(myCurrentProject.getName()+ ".txt", true)));
		out.println(command);
		out.flush();
		out.close();
	}
	
	public View getView(){
		return myView;
	}
	
	public Project getCurrentProject () {
		return myCurrentProject;
	}
	
	private void initializeObservers (Project pm, View view) {
		pm.getTurtleBundle().addObserver(myView.getMyAlterTurtleScreen());
		pm.getVariables().addObserver(myView.getMyVariableList());
		pm.addObserver(myView.getUserCommandSection());
		pm.getColorBundle().addObserver(myView.getMyAlterTurtleScreen().getMyBackground());
		
		updateAllObservers(pm, view);
	}
	
	private void updateAllObservers (Project pm, View view) {
		pm.getTurtleBundle().update();
		pm.getVariables().update();
		pm.update();
	}
	
	public void setLanguage (String language) {
		myCurrentProject.setLanguage(language);
		ParsingTable.getThis().setUpLanguageBundle(language);
	}

	public void loadVars(String filename) throws IOException{
		String input = "";
		File file = new File(filename);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;
		while((line = br.readLine()) != null){
			input += line + "\n";
		
		}
		br.close();
		fr.close();
		System.out.println(input);
		executeCommand(input);
	}
	
	public void switchProject (String projectName) {
		myCurrentProject = projectMap.get(projectName);
		ParsingTable.getThis().setUpLanguageBundle(myCurrentProject.getLanguage());
		ParsingTable.getThis().setProject(myCurrentProject);
		
		if (myView != null) {
			initializeObservers(myCurrentProject, myView);
		}
	}
	
	public void addProject (String projectName, Project project) {
		projectMap.put(projectName, project);
	}
	
	public void addProject (String projectName) {
		Project newProject = new Project(DEFAULT_LANGUAGE);
		projectMap.put(projectName, newProject);
		newProject.setName(projectName);
	}
	
	public void removeProject (String name) {
		projectMap.remove(name);
	}
	
	public void saveProject (Project pm, String projectName) 
			throws DOMException, IllegalArgumentException, IllegalAccessException, 
			ParserConfigurationException, TransformerException {
		XMLSaver.save(pm, new File(projectName));
	}
	
	public void saveProject (Project pm, File projectFile) 
			throws DOMException, IllegalArgumentException, IllegalAccessException, 
			ParserConfigurationException, TransformerException {
		XMLSaver.save(pm, projectFile);
	}
	
	public void loadProject (File projectFile) 
			throws ParserConfigurationException, SAXException, IOException, 
			NoSuchFieldException, SecurityException, IllegalArgumentException, 
			IllegalAccessException, ClassNotFoundException, NoSuchMethodException, 
			InstantiationException, InvocationTargetException {
		
		Project uploadedProject = new Project(DEFAULT_LANGUAGE);
		XMLLoader.load(uploadedProject, projectFile);
		projectMap.put(uploadedProject.getName(), uploadedProject);
		switchProject(uploadedProject.getName());
	}
	
	public Project getProject(){
		return myCurrentProject;
	}	
	
}
