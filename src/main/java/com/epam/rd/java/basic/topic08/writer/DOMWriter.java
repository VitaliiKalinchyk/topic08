package com.epam.rd.java.basic.topic08.writer;

import com.epam.rd.java.basic.topic08.container.Currency;
import com.epam.rd.java.basic.topic08.container.Exchange;
import static com.epam.rd.java.basic.topic08.container.constants.Constants.*;

import org.w3c.dom.*;
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;
import java.util.function.Supplier;

public class DOMWriter implements XMLWriter {

    private final Exchange elementToWrite;

    public DOMWriter(Exchange exchange) {
        this.elementToWrite = exchange;
    }

    public void writeToXml(String file) {
        Document document = getDocument();

        Element exchangeElement = getRootElement(document);
        document.appendChild(exchangeElement);
        for (Currency currency : elementToWrite.getCurrencies()) {
            Element currencyElement = getCurrencyElement(document, currency);
            exchangeElement.appendChild(currencyElement);
        }
        try (FileOutputStream output = new FileOutputStream(file)) {
            write(document, output);
        } catch (IOException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    private Element getRootElement(Document document) {
        Element exchangeElement = document.createElement("tn:" + EXCHANGE);
        exchangeElement.setAttribute("xmlns:xsi", W3C_XML_SCHEMA_INSTANCE_NS_URI);
        exchangeElement.setAttribute("xsi:schemaLocation", NAMESPACE + " input.xsd");
        exchangeElement.setAttribute("xmlns:tn", NAMESPACE);
        return exchangeElement;
    }

    private Element getCurrencyElement(Document document, Currency currency) {
        Element currencyElement = document.createElement(CURRENCY);
        currencyField(document, currencyElement, ID, currency::getId);
        currencyField(document, currencyElement, NAME, currency::getName);
        currencyField(document, currencyElement, RATE, currency::getRate);
        currencyField(document, currencyElement, ABR, currency::getAbr);
        return currencyElement;
    }

    private void currencyField (Document document, Element currencyElement, String field, Supplier<Object> supplier) {
        Element idElement = document.createElement(field);
        idElement.setTextContent(String.valueOf(supplier.get()));
        currencyElement.appendChild(idElement);
    }

    private void write(Document document, OutputStream output) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(output);
        transformer.transform(source, result);
    }

    private Document getDocument() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        return documentBuilder.newDocument();
    }
}