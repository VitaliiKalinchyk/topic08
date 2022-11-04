package com.epam.rd.java.basic.topic08.controller;

import com.epam.rd.java.basic.topic08.container.Currency;
import com.epam.rd.java.basic.topic08.container.Exchange;
import static com.epam.rd.java.basic.topic08.container.constants.Constants.*;

import javax.xml.stream.*;
import javax.xml.stream.events.XMLEvent;

import java.io.*;
import java.util.*;

/**
 * Controller for StAX parser.
 */
public class STAXController implements Controller {

	private final String XML_FILE_NAME;

	private XMLEventReader xmlEventReader;

	public STAXController(String xmlFileName) {
		this.XML_FILE_NAME = xmlFileName;
		createXmlEventReader();
	}

	public Exchange getExchange() {
		List<Currency> currencyList = new ArrayList<>();
		while (xmlEventReader.hasNext()) {
			XMLEvent xmlEvent = nextEvent();
			if (xmlEvent.isStartElement() && getStartElement(xmlEvent).equals(CURRENCY)) {
				currencyList.add(getCurrency(xmlEvent));
			}
        }
		return new Exchange(currencyList);
	}

	private void createXmlEventReader() {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		try {
			xmlEventReader = factory.createXMLEventReader(new FileInputStream(XML_FILE_NAME));
		} catch (XMLStreamException | FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private Currency getCurrency(XMLEvent xmlEvent) {
		Currency currency = new Currency();
		while (xmlEventReader.hasNext() && !endCurrencyElement(xmlEvent)) {
			xmlEvent = nextEvent();
			if (xmlEvent.isStartElement()) {
				String currentElement = getStartElement(xmlEvent);
				switch (currentElement) {
					case (ID):
						currency.setId(Integer.parseInt(nextEvent().asCharacters().getData()));
						break;
					case (NAME):
						currency.setName(nextEvent().asCharacters().getData());
						break;
					case (RATE):
						currency.setRate(Double.parseDouble(nextEvent().asCharacters().getData()));
						break;
					case (ABR):
						currency.setAbr(nextEvent().asCharacters().getData());
						break;
				}
			}
		}
		return currency;
	}

	private boolean endCurrencyElement(XMLEvent xmlEvent) {
		if (xmlEvent.isEndElement()) {
			return getEndElement(xmlEvent).equals(CURRENCY);
		}
		return false;
	}

	private String getEndElement(XMLEvent xmlEvent) {
		return xmlEvent.asEndElement().getName().getLocalPart();
	}

	private String getStartElement(XMLEvent xmlEvent) {
		return xmlEvent.asStartElement().getName().getLocalPart();
	}

	private XMLEvent nextEvent() {
		XMLEvent xmlEvent;
		try {
			xmlEvent = xmlEventReader.nextEvent();
		} catch (XMLStreamException e) {
			throw new RuntimeException(e);
		}
		return xmlEvent;
	}
}