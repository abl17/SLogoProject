package controller.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import util.ReflectionHelper;

public class SaveablePropertiesFactory {
	private static final String RESOURCE_LOCATION = "controller.resources.Saveable";
	private static final ResourceBundle RB = ResourceBundle.getBundle(RESOURCE_LOCATION);
	private static final String SAVEABLE_DELIMITER = ".";
	
	
	/**
	 * Saves a node in XML --> RECURSIVE, REFLECTION
	 * 
	 * @param document
	 * @param parent
	 * @param o
	 * @param field
	 * @throws DOMException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void saveProperty (Document document, Element parent, Object o, Field field) 
			throws DOMException, IllegalArgumentException, IllegalAccessException {
		
		String typeName = parent.getTagName() + SAVEABLE_DELIMITER + field.getName();
		String propertyType = extractPropertyFromResource(typeName);
		
		switch (propertyType) {
		case "element": { documentElement(document, parent, o, field); break; }
		case "attribute": { documentAttribute(document, parent, o, field); break; }
		case "map": { Collection<?> myCollection = ((Map<?,?>)field.get(o)).values(); 
						documentCollection(document, parent, o, field, myCollection); break; }
		case "enum": { documentEnum(document, parent, o, field); break; }
		case "list": { Collection<?> myList = (Collection<?>) field.get(o); 
						documentCollection(document, parent, o, field, myList); break; }
		default: { break; }
		}
		
	}

	/**
	 * @param document
	 * @param parent
	 * @param o
	 * @param field
	 * @throws IllegalAccessException
	 */
	private static void documentEnum(Document document, Element parent, Object o, Field field)
			throws IllegalAccessException {
		Object myEnum = field.get(o);
		Attr attr = document.createAttribute(field.getName());
		attr.setValue(myEnum.toString());
		parent.setAttributeNode(attr);
	}

	/**
	 * @param document
	 * @param parent
	 * @param o
	 * @param field
	 * @throws IllegalAccessException
	 */
	private static void documentCollection(Document document, Element parent, Object o, Field field, Collection<?> collection)
			throws IllegalAccessException {
		Element element = document.createElement(field.getName());
		parent.appendChild(element);
		
		int i = 0;
		for (Object value : collection) {
			documentElement(document, element, value);
		}
		documentAttribute(document, element, field.get(o), i);
	}

	/**
	 * @param typeName
	 * @return
	 */
	private static String extractPropertyFromResource (String typeName) {
		String propertyType;
		try {
			propertyType = RB.getString(typeName);
		} catch (MissingResourceException e) {
			propertyType = "none";
		}
		return propertyType;
	}

	/**
	 * @param document
	 * @param parent
	 * @param o
	 * @param field
	 * @throws IllegalAccessException
	 */
	private static void documentAttribute (Document document, Element parent, Object o, Field field)
			throws IllegalAccessException {
		Attr attr = document.createAttribute(field.getName());
		attr.setValue(field.get(o).toString());
		parent.setAttributeNode(attr);
	}
	
	private static void documentAttribute (Document document, Element parent, Object o, Object primitive) {
		if (primitive.getClass().isPrimitive() || primitive.getClass().isAssignableFrom(String.class)) {
			Attr attr = document.createAttribute(primitive.getClass().getSimpleName());
			attr.setValue(primitive.toString());
			parent.setAttributeNode(attr);
		}
	}

	/**
	 * @param document
	 * @param parent
	 * @param o
	 * @param field
	 * @throws IllegalAccessException
	 */
	private static void documentElement (Document document, Element parent, Object o, Field field)
			throws IllegalAccessException {
		Object newObject = field.get(o);
		Field[] newFields = ReflectionHelper.getFields(newObject);
		
		Element element = document.createElement(field.getName());
		parent.appendChild(element);
		
		for (Field newField : newFields) {
			saveProperty(document, element, newObject, newField);
		}
	}
	
	/**
	 * Document element when a direct parent is specified
	 * 
	 * @param document
	 * @param parent
	 * @param o
	 * @throws DOMException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private static void documentElement (Document document, Element parent, Object o) 
			throws DOMException, IllegalArgumentException, IllegalAccessException {
		Element element = document.createElement(o.getClass().getSimpleName());
		parent.appendChild(element);
		
		Field[] newFields = ReflectionHelper.getFields(o);
		
		for (Field newField : newFields) {
			saveProperty(document, element, o, newField);
		}
	}
}
