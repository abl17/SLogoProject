package util;

import java.lang.reflect.Field;

public class ReflectionHelper {
	public static Field[] getFields (Object o) {
		Class<?> clazz = o.getClass();
		Field[] myFields = clazz.getDeclaredFields();
		for (Field field : myFields) {
			field.setAccessible(true);
		}
		return myFields;
	}
	
	public static Field getField (Object o, String fieldString) 
			throws NoSuchFieldException, SecurityException {
		Class<? extends Object> parentClass = o.getClass();
		Field elementField = parentClass.getDeclaredField(fieldString);
		elementField.setAccessible(true);
		
		return elementField;
	}
}
