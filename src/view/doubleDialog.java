package view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;

public class doubleDialog {
	private String myPropertyName;
	private HashMap<String, Boolean> boolMap;
	public doubleDialog(Object o, String propertyName){
		myPropertyName = propertyName;
		TextInputDialog doubleDialog = setUpDialog(o);
		setDouble(o, doubleDialog);
	}
	protected TextInputDialog setUpDialog(Object o) {
		TextInputDialog doubleDialog = new TextInputDialog();
		doubleDialog.setTitle("Choose double");
		displayContentText(doubleDialog, o);
		return doubleDialog;
	}
	private void displayContentText(TextInputDialog doubleDialog, Object o) {
		doubleDialog.setContentText("Choose a double for the " + myPropertyName + "property");
	}
	
	protected void setDouble(Object o, TextInputDialog colorDialog) {
		//refactor so that penColors and colorChoices are always the same... maybe do away with penColors?
		Optional<String> result = colorDialog.showAndWait();
		if (result.isPresent()){
			setDoubleParticular(o, result.get());
		}
	}
	private void setDoubleParticular(Object o, String string) {
		for(Method method: o.getClass().getDeclaredMethods()){
			String name = method.getName().toLowerCase();
			if(name.contains("set")&&name.contains(myPropertyName)){
				try {
					method.invoke(o, Double.parseDouble(string));
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
	
}
