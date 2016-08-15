package util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import model.MarkType;

/**
 * This class allows for the creation of primitive classes over prim values
 * 
 * @author Austin
 *
 */
public class PrimitiveFactory {
	
	/**
	 * Modifies a class and returns a class type, changing Integer -> int and Boolean -> boolean
	 * @param classType
	 * @return Class
	 */
	public static Class<?> getPrimitive (Class<?> classType) {

		// Case: int
		if (classType == Integer.class) {
			return int.class;
		}
		
		// Case: bool
		if (classType == Boolean.class) {
			return boolean.class;
		}
		
		//Default
		return classType;
	}
	
	public static <T> Object getTypedValue (Object o, String stringValue) {
		Class<?> type = o.getClass();
		if (type.isAssignableFrom(String.class)) {
			return stringValue;
		} else if (type.isAssignableFrom(Boolean.class)) {
			return Boolean.parseBoolean(stringValue);
		} else if (type.isAssignableFrom(Double.class)) {
			return Double.parseDouble(stringValue);
		} else if (type.isAssignableFrom(Integer.class)) {
			return Integer.parseInt(stringValue);
		} else if (type.isAssignableFrom(Float.class)) {
			return Float.parseFloat(stringValue);
		} else if (type.isAssignableFrom(MarkType.class)) {
			return MarkType.valueOf(stringValue);
		} else {
			return null;
		}
	}
	
	public static <T> Object getKeyMapping (Object key, Map<?, ?> myMap, Class<?> clazz) {
		Object value;
		
		if (myMap.containsKey(key)) {
			value = myMap.get(key);
		} else {
			try {
				Constructor<?> constructor = clazz.getConstructor();
				value = constructor.newInstance();
			} catch (NoSuchMethodException | SecurityException | 
					InstantiationException | IllegalAccessException | 
					IllegalArgumentException | InvocationTargetException e) {
				value = null;
			}
		}
		
		return value;
	}
}
