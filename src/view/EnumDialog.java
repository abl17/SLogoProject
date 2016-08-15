package view;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;

public class EnumDialog {
	private	ArrayList<String> enumList;
	private String myPropertyName;

	public EnumDialog(Object o, String propertyName){
		myPropertyName = propertyName;
		enumList = addChoices(o);
		ChoiceDialog<String> EnumDialog = setUpDialog(enumList, o);
		setEnum(o, EnumDialog);
	}

	protected void setEnum(Object o, ChoiceDialog<String> EnumDialog) {
		//refactor so that penEnums and EnumChoices are always the same... maybe do away with penEnums?
		Optional<String> result = EnumDialog.showAndWait();
		if (result.isPresent()){
			setEnumParticular(o, result.get());
		}
	}

	protected void setEnumParticular(Object o, String Enum) {
		for(Method method: o.getClass().getDeclaredMethods()){
			String name = method.getName().toLowerCase();
			System.out.println(name + " " + method.getParameterCount());
			if(name.contains("set")&&name.contains(myPropertyName)&&method.getParameterCount()>0){
				try {
					
					method.invoke(o, Enum);
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

	

	protected ChoiceDialog<String> setUpDialog(List<String> EnumChoices, Object o) {
		ChoiceDialog<String> EnumDialog = new ChoiceDialog<>(EnumChoices.get(0), EnumChoices);
		EnumDialog.setTitle("Choose Enum");
		displayContentText(EnumDialog, o);
		return EnumDialog;
	}

	protected void displayContentText(ChoiceDialog<String> EnumDialog, Object o) {
		EnumDialog.setContentText("Choose the value of the " + o.getClass().getName());
	}

	protected ArrayList<String> addChoices(Object o) {
		ArrayList<String> EnumChoices = new ArrayList<String>();
		for(Field field: o.getClass().getDeclaredFields()){
			if(field.getName().toLowerCase().equals(myPropertyName.toLowerCase())){
				for(Object s: field.getType().getEnumConstants()){
					EnumChoices.add(s.toString());
				}
			}
		}
		
		return EnumChoices;
	}

}
