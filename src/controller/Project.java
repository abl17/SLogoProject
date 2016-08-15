package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import interpreter.IProjectManager;
import interpreter.ParseMaster;
import interpreter.blocks.RootBlock;
import interpreter.blocks.UserInstruction;
import interpreter.exceptions.blocks.BlockException;
import javafx.scene.paint.Color;
import model.ColorBundle;
import model.Turtle;
import model.TurtleBundle;
import model.Variables;
import model.exceptions.NumberException;
import model.util.SaveStateFactory;

public class Project extends Observable implements IProjectManager {
	
	private ParseMaster myParseMaster;
	
	private TurtleBundle myTurtleBundle;
	private Variables myVariables;
	private Map<String, UserInstruction> myUserInstructionMap;
	private ColorBundle myColorBundle;
	
	private String myLanguage;
	private String myName = "Workplace 1";
	
	public Project (String language) {
		myTurtleBundle = new TurtleBundle(new Turtle(0,0));
		myVariables = new Variables("MAIN");
		myUserInstructionMap = new HashMap<String, UserInstruction>();
		myColorBundle = new ColorBundle();
		
		setLanguage(language);
		
		myParseMaster = new ParseMaster(language);
	}
	
	public List<RootBlock> parse (String command) throws BlockException, NumberException {
		return myParseMaster.parse(command);
	}

	public void saveFuncsAndVars(String filename){
		try{
			SaveStateFactory.saveMath(this, filename);
		}
		catch (IOException e){
			System.out.println("CANNOT WRITE FILE");
		}
	}
	
	public ColorBundle getColorBundle() {
		return myColorBundle;
	}
	
	@Override
	public TurtleBundle getTurtleBundle() {
		return myTurtleBundle;
	}

	@Override
	public Variables getVariables() {
		return myVariables;
	}
	
	public void addUserInstruction (UserInstruction userInstruction) {
		myUserInstructionMap.put(userInstruction.getName(), userInstruction);
		
		update();
	}
	
	@Override
	public UserInstruction getUserInstruction(String token) {
		return myUserInstructionMap.get(token.toLowerCase());
	}

	public Map<String, UserInstruction> getUserInstructionMap () {
		return myUserInstructionMap;
	}
	
	public double setBackground (int index) {
		Color color = ColorBundle.getColor(index);
		
		if (color != null) {
			myColorBundle.setBackgroundColor(color);
		}
		
		return index;
	}
	
	public Color getBackgroundColor () {
		return myColorBundle.getBackgroundColor();
	}
	
	public double setCustomColor (int index, Color color) {
		myColorBundle.defineCustomColor(index, color);
		return index;
	}
	
	public void update () {
		setChanged();
		notifyObservers();
	}
	public void setName(String s){
		myName = s;
	}
	public String getName(){
		return myName;
	}

	public String getLanguage() {
		return myLanguage;
	}

	public void setLanguage(String myLanguage) {
		this.myLanguage = myLanguage;
	}
}
