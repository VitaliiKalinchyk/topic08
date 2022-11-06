package com.epam.rd.java.basic.topic08.writer.transformer;

import org.w3c.dom.Document;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.*;

import java.io.*;
import java.util.Properties;

public class UtilityTransformer {

    private static Properties properties() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("transformer.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    public static void write(Document document, OutputStream output) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperties(properties());
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(output);
        transformer.transform(source, result);
    }

    public static void write(OutputStream rawXMLStream, OutputStream output) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperties(properties());
        StreamSource source = new StreamSource(new StringReader(rawXMLStream.toString()));
        StreamResult result = new StreamResult(output);
        transformer.transform(source, result);
    }
}