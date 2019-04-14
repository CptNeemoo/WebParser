package com.cptneemoo.service;

import com.cptneemoo.exception.XMLCreateException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class XMLCreatorService {

    private static final String PROJECT_PATH = System.getProperty("user.dir");

    private static final String FILE_SEPARATOR = System.getProperty("file.separator");

    private static final String XML_FILE_PATH = String.format("%s%sdata%sdata.xml",
            PROJECT_PATH, FILE_SEPARATOR, FILE_SEPARATOR);

    public void create(HashMap<String, String> information) throws XMLCreateException {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element root = document.createElement("item");
            document.appendChild(root);
            for (Map.Entry entry : information.entrySet()) {
                appendElement(document, entry.getKey(), entry.getValue(), root);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(XML_FILE_PATH));
            transformer.transform(domSource, streamResult);
        } catch (Exception e) {
            throw new XMLCreateException("Can't create xml file");
        }
    }

    private static void appendElement(Document document, Object key, Object value, Element parent) {
        Element element = document.createElement((String) key);
        element.appendChild(document.createTextNode((String) value));
        parent.appendChild(element);
    }
}
