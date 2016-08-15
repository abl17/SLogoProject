package model;


import java.util.ArrayList;
import java.util.Observable;

public class VariableBundle extends Observable {

	private ArrayList<Variables> myLists;
	private Variables myMain;
	
	public VariableBundle () {
		myMain = new Variables("MAIN");
		myLists = new ArrayList<Variables>();
		myLists.add(myMain);
	}
}
