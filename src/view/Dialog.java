package view;

//This entire file is part of my masterpiece.
//MICHAEL OGEZ

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import java.lang.reflect.Method;
import java.rmi.activation.Activator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.ChoiceDialog;

public class Dialog {
	public Dialog(Object o){
		HashMap<String,Class<?>> choices = addChoices(o);
		ChoiceDialog<String> dialog = setUpDialog(choices, o);
		setProperty(o,choices, dialog);
	}

	private void setProperty(Object o, HashMap<String, Class<?>> choices, ChoiceDialog<String> dialog) {
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			createNewDialogBox(result.get(), choices.get(result.get()), o);
		}		
	}

	private void createNewDialogBox(String string, Class<?> propertyType, Object o) {
		try {
			System.out.println(this.getClass().getClassLoader().toString());
			Class cls = Class.forName("view."+propertyType.getSimpleName()+"Dialog");
			
			try {
				try {
					
					Object obj = cls.getDeclaredConstructor(Object.class, String.class).newInstance(o,string);
					
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private ChoiceDialog<String> setUpDialog(HashMap<String, Class<?>> choices, Object o) {
		ChoiceDialog<String> dialog = new ChoiceDialog<>("No Property Chosen", choices.keySet());
		dialog.setTitle("Choose Property");
		displayContentText(dialog, o);
		return dialog;
	}

	private void displayContentText(ChoiceDialog<String> dialog, Object o) {
		dialog.setContentText("Choose Property of " + o.getClass().getName() + " to Modify:");
	}

	private HashMap<String,Class<?>> addChoices(Object o) {
		HashMap<String, Class<?>> mutableProperties = new HashMap<String, Class<?>>();
		Class cls = o.getClass();
		for(Method method: cls.getDeclaredMethods()){
			setMutableProperties(mutableProperties, cls, method);
		}
		return mutableProperties;
	}

	private void setMutableProperties(HashMap<String, Class<?>> mutableProperties, Class cls, Method method) {
		
		String methodName = method.getName().toLowerCase();
		if(methodName.startsWith("set")&&method.getParameterCount()>0){
			String propertyName = methodName.substring(3,methodName.length());
			Class<?> propertyType = setPropertyType(cls, method, propertyName);
			mutableProperties.put(propertyName, propertyType);
		}
	}

	private Class<?> setPropertyType(Class cls, Method method, String propertyName) {
		Class<?> propertyType = null;
		for(Field field: cls.getDeclaredFields()){
			if(field.getName().toLowerCase().equals(propertyName)){
				propertyType = field.getType();
				if(propertyType.isEnum()){
					propertyType=Enum.class;
				}
			}
		
		}
		if(propertyType==null){
			propertyType = method.getParameterTypes()[0];

		}
		return propertyType;
	}
}
