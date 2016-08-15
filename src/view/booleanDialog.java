package view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.paint.Color;

public class booleanDialog {
	private String myPropertyName;
	private HashMap<String, Boolean> boolMap;
	public booleanDialog(Object o, String propertyName){
		myPropertyName = propertyName;
		boolMap = addChoices();
		List<String> choices = new ArrayList<String>(boolMap.keySet());
		ChoiceDialog<String> booleanDialog = setUpDialog(choices, o);
		setBoolean(o, choices, booleanDialog);
	}
	protected ChoiceDialog<String> setUpDialog(List<String> choices, Object o) {
		ChoiceDialog<String> booleanDialog = new ChoiceDialog<>("TRUE", choices);
		booleanDialog.setTitle("Choose true or false");
		displayContentText(booleanDialog, o);
		return booleanDialog;
	}
	private void displayContentText(ChoiceDialog<String> booleanDialog, Object o) {
		booleanDialog.setContentText("Choose a boolean for the " + myPropertyName + "property");
		
	}
	protected HashMap<String,Boolean> addChoices() {
		//refactor to use properties file
		HashMap<String, Boolean> colorChoices = new HashMap<String,Boolean>();
		colorChoices.put("TRUE", true);
		colorChoices.put("FALSE", false);
		return colorChoices;
	}
	protected void setBoolean(Object o, List<String> colorChoices, ChoiceDialog<String> colorDialog) {
		//refactor so that penColors and colorChoices are always the same... maybe do away with penColors?
		Optional<String> result = colorDialog.showAndWait();
		if (result.isPresent()){
			setBooleanParticular(o, result.get());
		}
	}
	private void setBooleanParticular(Object o, String string) {
		for(Method method: o.getClass().getDeclaredMethods()){
			String name = method.getName().toLowerCase();
			if(name.contains("set")&&name.contains(myPropertyName)){
				try {
					method.invoke(o, stringToBoolean(string));
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}		
	}
	private Boolean stringToBoolean(String string) {
		return boolMap.get(string);
	}
}
