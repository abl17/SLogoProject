package controller.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import controller.Project;
import util.PrimitiveFactory;
import util.ReflectionHelper;

public class XMLLoader {
	private static final DocumentBuilderFactory DB_FACTORY = DocumentBuilderFactory.newInstance();
	private static final String RESOURCE_LOCATION = "controller.resources.Loadable";
	private static final ResourceBundle RB = ResourceBundle.getBundle(RESOURCE_LOCATION);

	public static Project load (Project pm, File file) 
			throws ParserConfigurationException, SAXException, IOException, 
			NoSuchFieldException, SecurityException, IllegalArgumentException, 
			IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, InvocationTargetException {
		DocumentBuilder builder = DB_FACTORY.newDocumentBuilder();
		Document document = builder.parse(file);
		document.getDocumentElement().normalize();

		Element root = document.getDocumentElement();

		visitChildNodes(pm, root);

		return pm;
	}

	//This function is called recursively
	private static void visitChildNodes (Object parent, Node node) 
			throws NoSuchFieldException, SecurityException, 
			IllegalArgumentException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, InvocationTargetException {
	    if (node.hasAttributes()) {
	    	NamedNodeMap nodeMap = node.getAttributes();
	    	for (int i = 0; i < nodeMap.getLength(); i++) {
	    		Node tempNode = nodeMap.item(i);
	    		loadAttributeNode(parent, tempNode);
	    		
	    	}
	    }
	    
	    NodeList nodeList = node.getChildNodes();
	    
	    for (int i = 0; i < nodeList.getLength(); i++) {
	        Node currentNode = nodeList.item(i);
	       
	        switch (currentNode.getNodeType()) {
	        case Node.ELEMENT_NODE:
	        	boolean isCollection = checkCollection(parent, currentNode);
	        	if (isCollection) { loadCollectionNode(parent, currentNode); } else { loadElementNode(parent, currentNode); }
	        	break;
	        default:
	        	break;
	        }
	    }
	}
	
	/**
	 * 
	 * @param parent
	 * @param node
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws ClassNotFoundException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 */
	private static void loadCollectionNode (Object parent, Node node) 
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, 
			IllegalAccessException, ClassNotFoundException, NoSuchMethodException, 
			InstantiationException, InvocationTargetException {
		
		Field elementField = ReflectionHelper.getField(parent, node.getNodeName());
		Object collectionObject = elementField.get(parent);
		LoadablePropertiesFactory.clear(collectionObject);
		
		NodeList nodeList = node.getChildNodes();
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			addNodeToCollection(collectionObject, nodeList, i);
			
		}
		
		elementField.set(parent, collectionObject);
		
	}

	private static void addNodeToCollection(Object collectionObject, NodeList nodeList, int i)
			throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchFieldException {
		Node currentNode = nodeList.item(i);
		String newObjectClassName = RB.getString(currentNode.getNodeName());
		Class<?> newObjectClass = Class.forName(newObjectClassName);
		Object newObject = LoadablePropertiesFactory.getDefaultObject(newObjectClass);
		
		visitChildNodes(newObject, currentNode);

		LoadablePropertiesFactory.addToCollection(collectionObject, newObject, i);
	}

	private static void loadAttributeNode (Object parent, Node node) 
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field elementField = ReflectionHelper.getField(parent, node.getNodeName());
		String nodeStringValue = node.getNodeValue();
		Object value = PrimitiveFactory.getTypedValue(elementField.get(parent), nodeStringValue);
		
		elementField.set(parent, value);
		
	}
	
	/**
	 * @param parent
	 * @param currentNode
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 */
	private static void loadElementNode (Object parent, Node node)
			throws NoSuchFieldException, IllegalAccessException, SecurityException, 
			IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, InstantiationException, InvocationTargetException {
		Field elementField = ReflectionHelper.getField(parent, node.getNodeName());
		Object newObject = elementField.get(parent);
		visitChildNodes(newObject, node);
		elementField.set(parent, newObject);
		
	}
	
	/**
	 * 
	 * @param parent
	 * @param node
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	private static boolean checkCollection (Object parent, Node node) 
			throws NoSuchFieldException, SecurityException {
		Field elementField = ReflectionHelper.getField(parent, node.getNodeName());
		if (elementField.getType().isAssignableFrom(Map.class) || elementField.getType().isAssignableFrom(List.class)) {
			return true;
		} else {
			return false;
		}
	}
}
