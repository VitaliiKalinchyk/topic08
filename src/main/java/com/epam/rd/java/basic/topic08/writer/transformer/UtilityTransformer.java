package com.epam.rd.java.basic.topic08.writer.transformer;

import org.w3c.dom.Document;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.*;

import java.io.*;

public class UtilityTransformer {

    public static void write(Document document, OutputStream output) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(output);
        transformer.transform(source, result);
    }

    public static void write(OutputStream rawXMLStream, OutputStream output) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
        StreamSource source = new StreamSource(new StringReader(rawXMLStream.toString()));
        StreamResult result = new StreamResult(output);
        transformer.transform(source, result);
    }
}