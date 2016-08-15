package controller.util;

import java.io.File;
import java.lang.reflect.Field;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import util.ReflectionHelper;

/**
 * Class that uses reflection utilities to save persistent data. In this case, saves relevant information.
 * 
 * @author Austin
 *
 */
public class XMLSaver {
	private static final DocumentBuilderFactory DB_FACTORY = DocumentBuilderFactory.newInstance();
	private static final TransformerFactory T_FACTORY = TransformerFactory.newInstance();

	/**
	 * Saves a project to an XML file specified in the projectPath.
	 * 
	 * @param project
	 * @param projectPath
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws DOMException 
	 */
	public static void save (Object o, File projectFile) 
			throws ParserConfigurationException, TransformerException, 
			DOMException, IllegalArgumentException, IllegalAccessException {
		Document document = createDocument(o);
		transform(document, projectFile);
	}
	
	/**
	 * Creates a document with DocumentBuilder class; defines all the XML content, e.g. nodes and attributes
	 * 
	 * @param project
	 * @return XML-defined content (Document)
	 * @throws ParserConfigurationException
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws DOMException 
	 */
	private static Document createDocument (Object o) 
			throws ParserConfigurationException, DOMException, IllegalArgumentException, IllegalAccessException {
		DocumentBuilder db = DB_FACTORY.newDocumentBuilder();
		Document document = db.newDocument();
		Element rootElement = documentParentElement(document, o);
		
		Field[] fields = ReflectionHelper.getFields(o);
		documentChildElements(document, rootElement, o, fields);
		
		return document;
	}
	
	/**
	 * Writes document contents to an XML file specified by file parameter.
	 * 
	 * @param document
	 * @param file
	 * @throws TransformerException
	 */
	private static void transform (Document document, File file) 
			throws TransformerException {
		// Write content to XML
		Transformer transformer = T_FACTORY.newTransformer();
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(file);
		
		transformer.transform(source, result);
	}
	
	/**
	 * Adds children nodes and attributes to the document
	 * 
	 * @param document
	 * @param parent
	 * @param o
	 * @param fields
	 * @throws DOMException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void documentChildElements (Document document, Element parent, Object o, Field ... fields) 
			throws DOMException, IllegalArgumentException, IllegalAccessException {
		for (Field field : fields) {
			SaveablePropertiesFactory.saveProperty(document, parent, o, field);
		}
	}
	
	/**
	 * Adds parent node to the document
	 * 
	 * @param document
	 * @param o
	 * @return
	 */
	public static Element documentParentElement (Document document, Object o) {
		String parentName = o.getClass().getSimpleName();
		Element rootElement = document.createElement(parentName);
		document.appendChild(rootElement);
		
		return rootElement;
	}
}
