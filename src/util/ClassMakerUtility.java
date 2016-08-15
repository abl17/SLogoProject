package util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import interpreter.blocks.RootBlock;
import interpreter.exceptions.blocks.BlockArgumentsException;

/**
 * This class is a helper class primarily used to extract certain important elements from classes
 * dynamically at runtime using reflection. Includes the following public static methods:
 * 
 * getConstructor -- returns the constructor of a class given the class and its parameters
 * getClassArray -- returns an array of class types given a list of objects
 * checkEquals -- checks if one class equals another or vice versa
 * 
 * createBlockInstance -- returns a root block primarily for the abstract syntax tree
 * 
 * @author Austin
 *
 */

public class ClassMakerUtility {
	
	/**
	 * Class that returns the constructor of a class given the class and its parameters
	 * 
	 * @param myClass
	 * @param parameters
	 * @return constructor
	 * @throws BlockArgumentsException
	 */
	public static Constructor<?> getConstructor (Class<?> myClass, Object ... parameters) 
			throws BlockArgumentsException {
		for (Constructor<?> constructor: myClass.getConstructors()) {
			if (checkEquals(getClassArray(parameters), constructor.getParameterTypes())) {
				return constructor;
			}
		}
		
		return null;
	}
	
	/**
	 * returns a root block primarily for the abstract syntax tree
	 * 
	 * @param myClass
	 * @param parameters
	 * @return RootBlock
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws BlockArgumentsException
	 */
	public static RootBlock createBlockInstance (Class<?> myClass, Object ... parameters) 
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
			InvocationTargetException, BlockArgumentsException {
		Constructor<?> constructor = getConstructor(myClass, parameters);
		
		if (constructor == null) {
			// TODO: Change this
			throw new BlockArgumentsException("Wrong call", null);
		}
		
		return (RootBlock) constructor.newInstance(parameters);
	}
	
	/**
	 * Class that returns an array of class types given a list of objects
	 * 
	 * @param parameters
	 * @return array of class types given a list of objects
	 */
	public static Class<?>[] getClassArray (Object ... parameters) {
		Class<?>[] myTypes = new Class<?>[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
            myTypes[i] = PrimitiveFactory.getPrimitive(parameters[i].getClass());
		}
		return myTypes;
	}
	
	/**
	 * Class that checks if one class equals another or vice versa
	 * 
	 * @param myClass1
	 * @param myClass2
	 * @return check if they are equal
	 */
	public static boolean checkEquals (Class<?>[] myClass1, Class<?>[] myClass2) {
		if (myClass1.length != myClass2.length) {
			return false;
		}
		
		for (int i = 0; i < myClass1.length; i++) {
			if (!(myClass1[i].isAssignableFrom(myClass2[i]) || 
					myClass2[i].isAssignableFrom(myClass1[i]))) {
				return false;
			}
		}
		
		return true;
	}
}
