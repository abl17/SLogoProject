package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

public class Variables extends Observable {
	private String name;
	private Map<String, Double> variables;
	
	public Variables (String name) {
		this.name = name;
		variables = new HashMap<String, Double>();
	}
	
	@Override
	public boolean equals (Object o) {
		if (o instanceof Variables) {
			return name.equals(((Variables) o).getName());
		} else if (o instanceof String) {
			return name.equals(o);
		} else {
			return false;
		}
	}
	
	public String getName () {
		return name;
	}
	
	public void setVariable (String assign, double assignedValue) {
		variables.put(assign, assignedValue);
		
		update();
	}
	
	public boolean containsVariable (String str) {
		return variables.containsKey(str);
	}
	
	public Double getVariable (String obtain) {
		return variables.get(obtain);
	}
	
	public Double removeVariable (String str) {
		Double myValue = variables.get(str);
		variables.remove(str);
		
		update();
		return myValue;
	}
	
	public Set<String> getVariableList () {
		return variables.keySet();
	}
	
	public void update () {
		setChanged();
		notifyObservers();
	}
}
