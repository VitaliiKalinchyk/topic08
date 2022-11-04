package com.epam.rd.java.basic.topic08.writer;

import com.epam.rd.java.basic.topic08.container.Currency;
import com.epam.rd.java.basic.topic08.container.Exchange;
import static com.epam.rd.java.basic.topic08.container.constants.Constants.*;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI;
import javax.xml.stream.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;

import java.io.*;
import java.util.function.Supplier;

public class STAXWriter implements XMLWriter {

    private final Exchange EXCHANGE_TO_WRITE;
    private final XMLEventFactory EVENT_FACTORY;

    public STAXWriter(Exchange exchange) {
        EXCHANGE_TO_WRITE = exchange;
        EVENT_FACTORY = XMLEventFactory.newInstance();
    }

    public void writeToXml(String file) {
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        OutputStream outputStream = new ByteArrayOutputStream();
        try {
            XMLEventWriter writer =  outputFactory.createXMLEventWriter(outputStream);
            toByteArray(writer);
            writer.flush();
            writer.close();
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        writeToFile(file, formattedXML(outputStream.toString()));
    }

    private void toByteArray(XMLEventWriter writer) throws XMLStreamException {
        writer.add(EVENT_FACTORY.createStartDocument("utf-8", "1.0"));
        writer.add(EVENT_FACTORY.createStartElement("tn", NAMESPACE, EXCHANGE));
        writer.add(EVENT_FACTORY.createAttribute("xmlns:xsi", W3C_XML_SCHEMA_INSTANCE_NS_URI));
        writer.add(EVENT_FACTORY.createAttribute("xsi:schemaLocation", NAMESPACE + " input.xsd"));
        writer.add(EVENT_FACTORY.createAttribute("xmlns:tn", NAMESPACE));
        for (Currency currency : EXCHANGE_TO_WRITE.getCurrencies()) {
            writeCurrency(writer, currency);
        }
        writer.add(EVENT_FACTORY.createEndElement("", NAMESPACE, EXCHANGE));
        writer.add(EVENT_FACTORY.createEndDocument());
    }

    private void writeCurrency(XMLEventWriter writer, Currency currency) throws XMLStreamException {
        writer.add(EVENT_FACTORY.createStartElement("", "", CURRENCY));
        writeField(writer, ID, currency::getId);
        writeField(writer, NAME, currency::getName);
        writeField(writer, RATE, currency::getRate);
        writeField(writer, ABR, currency::getAbr);
        writer.add(EVENT_FACTORY.createEndElement("", "", CURRENCY));
    }

    private void writeField (XMLEventWriter writer, String field, Supplier<Object> supplier) throws XMLStreamException {
        writer.add(EVENT_FACTORY.createStartElement("", "", field));
        writer.add(EVENT_FACTORY.createCharacters(String.valueOf(supplier.get())));
        writer.add(EVENT_FACTORY.createEndElement("", "", field));
    }

    private String formattedXML(String outputStream) {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        StringWriter output;
        try {
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            StreamSource source = new StreamSource(new StringReader(outputStream));
            output = new StringWriter();
            transformer.transform(source, new StreamResult(output));
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
        return output.toString();
    }

    private void writeToFile(String file,String content) {
        try (Writer outputStream = new FileWriter(file)) {
            outputStream.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}