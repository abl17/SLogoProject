package controller.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import model.Turtle;

public class LoadablePropertiesFactory {
	public static Object getDefaultObject (Class<?> newObjectClass) 
			throws NoSuchMethodException, SecurityException, ClassNotFoundException, 
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Constructor<?> constructor = newObjectClass.getConstructor();
		
		Object myNewObject = constructor.newInstance();
		return myNewObject;
	}

	@SuppressWarnings("unchecked")
	public static void addToCollection(Object collectionObject, Object newObject, int index) {
		int id = 0;
		
		if (collectionObject instanceof Map) {
			if (newObject.getClass().isAssignableFrom(Turtle.class)) {
				id = (int) ((Turtle) newObject).getID();
				((Map<Integer, Turtle>) collectionObject).put(id, (Turtle) newObject);
			} else {
				((Map<Integer, Object>) collectionObject).put(index, newObject);
			}
		} else if (collectionObject instanceof List) {
			((List<Object>) collectionObject).add(newObject);
		}
	}

	public static void clear(Object collectionObject) {
		if (collectionObject instanceof Map) {
			((Map<?, ?>) collectionObject).clear();
		} else if (collectionObject instanceof Collection) {
			((Collection<?>) collectionObject).clear();
		}
	}
	
}
