package view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.paint.Color;
import model.Turtle;


public class ColorDialog {
	private HashMap<String, Color> colorMap;
	public ColorDialog(Object o, String propertyName){
		colorMap = addColors();
		List<String> colorChoices = new ArrayList<String>(colorMap.keySet());
		ChoiceDialog<String> colorDialog = setUpDialog(colorChoices, o);
		setColor(o, colorChoices, colorDialog);
	}

	protected void setColor(Object o, List<String> colorChoices, ChoiceDialog<String> colorDialog) {
		//refactor so that penColors and colorChoices are always the same... maybe do away with penColors?
		Optional<String> result = colorDialog.showAndWait();
		if (result.isPresent()){
			setColorParticular(o, result.get());
		}
	}

	protected void setColorParticular(Object o, String color) {
		for(Method method: o.getClass().getDeclaredMethods()){
			String name = method.getName().toLowerCase();
			System.out.println(name + " " + method.getParameterCount());
			if(name.contains("set")&&name.contains("color")&&method.getParameterCount()>0){
				try {
				
					method.invoke(o, stringToColor(color));
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

	private Color stringToColor(String color) {
		return colorMap.get(color);
	}

	protected ChoiceDialog<String> setUpDialog(List<String> colorChoices, Object o) {
		ChoiceDialog<String> colorDialog = new ChoiceDialog<>("RED", colorChoices);
		colorDialog.setTitle("Choose color");
		displayContentText(colorDialog, o);
		return colorDialog;
	}

	protected void displayContentText(ChoiceDialog<String> colorDialog, Object o) {
		colorDialog.setContentText("Choose the color of the " + o.getClass().getName());
	}

	protected HashMap<String,Color> addColors() {
		//refactor to use properties file
		HashMap<String,Color> colorChoices = new HashMap<String,Color>();
		colorChoices.put("RED", Color.RED);
		colorChoices.put("BLUE", Color.BLUE);
		colorChoices.put("BLACK", Color.BLACK);
		return colorChoices;
	}
}
