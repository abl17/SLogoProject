// This entire file is part of my masterpiece.
// Austin Liu (abl17)

package model;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;

import interpreter.exceptions.blocks.BlockException;
import model.util.TurtleEvaluationFactory;
import util.PrimitiveFactory;

public class TurtleBundle extends Observable {

	private Map<Integer, Turtle> myTurtleReserves; //Inactive
	private Map<Integer, Turtle> myDeployedTurtles; //Active

	public TurtleBundle (Turtle primaryTurtle) {
		myTurtleReserves = new TreeMap<Integer, Turtle>();
		myDeployedTurtles = new TreeMap<Integer, Turtle>();

		recruit(0, primaryTurtle);
		deploy(0);
	}

	/**
	 * This method evaluates given a command and updates the turtle based on the command 
	 * @param command
	 * @param value
	 */
	public double evaluate(String command, Object ... values) {
		double result;

		try {
			result = TurtleEvaluationFactory.evaluate(command, values, myDeployedTurtles);
			update();
		} catch (BlockException e) { result = 0; }

		return result;
	}

	/**
	 * Add a turtle to the inactive turtle mapping
	 * @param index
	 * @param turtle
	 */
	public void recruit (int index, Turtle turtle) {
		myTurtleReserves.put(index, turtle);
		turtle.setID(index);
	}

	/**
	 * Permanently remove a turtle from the bundle
	 * @param index
	 */
	public void discharge (int index) {
		myTurtleReserves.remove(index);
		recall(index);
	}

	/**
	 * Permanently remove all turtles from the bundle
	 */
	public void discharge () {
		myTurtleReserves.clear();
		recall();
	}

	/**
	 * Makes inactive turtles specified active
	 * @param myIndexes
	 */
	public void deploy (Collection<Integer> myIndexes) {
		for (int index : myIndexes) {
			deploy(index);
		}
	}

	/**
	 * Activates turtles
	 * @param index
	 */
	public void deploy (int index) {
		Turtle turtle = (Turtle) PrimitiveFactory.getKeyMapping(index, myTurtleReserves, Turtle.class);
		recruit(index, turtle);
		
		myDeployedTurtles.put(index, myTurtleReserves.get(index));
		update();
	}

	/**
	 * Changes specified turtle from active to inactive
	 * @param index
	 */
	public void recall (int index) {
		myDeployedTurtles.remove(index);
		update();
	}

	/**
	 * Remove all turtles from active map
	 */
	public void recall () {
		myDeployedTurtles.clear();
		update();
	}

	/**
	 * @return unmodifiable collection of inactive turtles
	 */
	public Map<Integer,Turtle> getTurtleReserves () {
		return Collections.unmodifiableMap(myTurtleReserves);
	}

	/**
	 * @return unmodifiable collection of active turtles
	 */
	public Map<Integer,Turtle> getTurtleDeployed () {
		return Collections.unmodifiableMap(myDeployedTurtles);
	}

	/**
	 * Allows the TurtleBundle to notify its observers that its state has changed
	 */
	public void update () {
		setChanged();
		notifyObservers(myDeployedTurtles);
	}
}
