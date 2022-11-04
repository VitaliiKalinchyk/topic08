package com.epam.rd.java.basic.topic08.writer;

import com.epam.rd.java.basic.topic08.container.Currency;
import com.epam.rd.java.basic.topic08.container.Exchange;
import static com.epam.rd.java.basic.topic08.container.constants.Constants.*;
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI;

import javax.xml.stream.*;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class STAXWriter implements XMLWriter {

    private final Exchange elementToWrite;

    public STAXWriter(Exchange exchange) {
        this.elementToWrite = exchange;
    }

    public void writeToXml(String file) {
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        try (OutputStream outputStream = new FileOutputStream(file)) {
            XMLEventWriter writer =  outputFactory.createXMLEventWriter(outputStream);
            writer.add(eventFactory.createStartDocument("utf-8", "1.0"));
            writer.add(eventFactory.createCharacters(System.getProperty("line.separator")));
            writer.add(eventFactory.createStartElement("tn", NAMESPACE, EXCHANGE));
            writer.add(eventFactory.createAttribute("xmlns:xsi", W3C_XML_SCHEMA_INSTANCE_NS_URI));
            writer.add(eventFactory.createAttribute("xsi:schemaLocation", NAMESPACE + " input.xsd"));
            writer.add(eventFactory.createAttribute("xmlns:tn", NAMESPACE));
            writer.add(eventFactory.createCharacters(System.getProperty("line.separator")));
            for (Currency currency : elementToWrite.getCurrencies()) {
                writer.add(eventFactory.createStartElement("", "", CURRENCY));
                writer.add(eventFactory.createCharacters(System.getProperty("line.separator")));
                writer.add(eventFactory.createStartElement("", "", ID));
                writer.add(eventFactory.createCharacters(String.valueOf(currency.getId())));
                writer.add(eventFactory.createEndElement("", "", ID));
                writer.add(eventFactory.createCharacters(System.getProperty("line.separator")));
                writer.add(eventFactory.createStartElement("", "", NAME));
                writer.add(eventFactory.createCharacters(currency.getName()));
                writer.add(eventFactory.createEndElement("", "", NAME));
                writer.add(eventFactory.createCharacters(System.getProperty("line.separator")));
                writer.add(eventFactory.createStartElement("", "", RATE));
                writer.add(eventFactory.createCharacters(String.valueOf(currency.getRate())));
                writer.add(eventFactory.createEndElement("", "", RATE));
                writer.add(eventFactory.createCharacters(System.getProperty("line.separator")));
                writer.add(eventFactory.createStartElement("", "", ABR));
                writer.add(eventFactory.createCharacters(currency.getAbr()));
                writer.add(eventFactory.createEndElement("", "", ABR));
                writer.add(eventFactory.createCharacters(System.getProperty("line.separator")));
                writer.add(eventFactory.createEndElement("", "", CURRENCY));
                writer.add(eventFactory.createCharacters(System.getProperty("line.separator")));
            }
            writer.add(eventFactory.createEndElement("", NAMESPACE, EXCHANGE));
            writer.add(eventFactory.createEndDocument());
            writer.flush();
            writer.close();
        } catch (IOException | XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }
}