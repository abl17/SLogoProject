// This entire file is part of my masterpiece.
// Austin Liu (abl17)

package model.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import interpreter.exceptions.blocks.BlockArgumentsException;
import interpreter.exceptions.blocks.BlockException;
import model.Turtle;

public class TurtleEvaluationFactory {
	
	/**
	 * Invokes a command on turtles specified
	 * @param command
	 * @param values
	 * @param turtleMap
	 * @return Numerical result of last turtle evaluation
	 * @throws BlockException
	 */
	public static double evaluate (String command, Object[] values, Map<Integer, Turtle> turtleMap) 
			throws BlockException {
		double result = 0;

		for (Turtle turtle : turtleMap.values()) {
				result = evaluate(command, values, turtle);
		}

		return result;
	}

	/**
	 * Invokes a command on the turtle using reflection
	 * 
	 * @param command
	 * @param values
	 * @param turtle
	 * @return Numerical result
	 * @throws BlockException
	 */
	public static double evaluate (String command, Object[] values, Turtle turtle) 
			throws BlockException {
		Object result;
		Class<Turtle> myClass = Turtle.class;
		Class<?>[] myValueClasses = setClassTypes(values.length, double.class);

		try {
			Method myMethod = myClass.getMethod(command, myValueClasses);
			result = myMethod.invoke(turtle, values);
		} catch (IllegalAccessException | IllegalArgumentException | 
				InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new BlockArgumentsException();
		} 

		return (double) result;
	}
	
	/**
	 * Helper method to set a collection to a particular class type
	 * 
	 * @param length
	 * @param clazz
	 * @return Class<?>[] collection of classes
	 */
	private static Class<?>[] setClassTypes (int length, Class<?> clazz) {
		Class<?>[] myClasses = new Class[length];
		
		for (int i = 0; i < length; i++) { 
			myClasses[i] = clazz; 
		}
		
		return myClasses;
	}
}
