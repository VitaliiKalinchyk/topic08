package com.epam.rd.java.basic.topic08.validator;

import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;

import java.io.*;

public class XMLValidator {
    public static boolean validateXML(String xml, String xsd){
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsd));
            Validator validator = schema.newValidator();
            try {
                validator.validate(new StreamSource(new File(xml)));
            } catch (SAXException e) {
                return false;
            }
        } catch (IOException | SAXException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}