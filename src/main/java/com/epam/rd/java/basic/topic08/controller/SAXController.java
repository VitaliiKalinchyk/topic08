package com.epam.rd.java.basic.topic08.controller;

import com.epam.rd.java.basic.topic08.container.Currency;
import com.epam.rd.java.basic.topic08.container.Exchange;
import static com.epam.rd.java.basic.topic08.container.constants.Constants.*;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.*;

import java.io.IOException;
import java.util.*;

/**
 * Controller for SAX parser.
 */
public class SAXController extends DefaultHandler implements Controller {


	private final String XML_FILE_NAME;

	private final List<Currency> CURRENCY_LIST = new ArrayList<>();

	private Currency currency;

	private String currentElement;

	public SAXController(String xmlFileName) {
		XML_FILE_NAME = xmlFileName;
	}

	@Override
	public Exchange getExchange() {
		try {
			parseXML();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new RuntimeException(e);
		}
		return new Exchange(CURRENCY_LIST);
	}

	private void parseXML() throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser parser = saxParserFactory.newSAXParser();
		parser.parse(XML_FILE_NAME, this);
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		currentElement = qName;
		if (qName != null && qName.equals(CURRENCY)) {
			currency = new Currency();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) {
		if (qName != null && qName.equals(CURRENCY)) {
			CURRENCY_LIST.add(currency);
		}
		currentElement = null;
	}

	@Override
	public void characters(char[] ch, int start, int length) {
		if (currentElement != null) {
			String content = new String(ch, start, length);
			switch (currentElement) {
				case (ID):
					currency.setId(Integer.parseInt(content));
					break;
				case (NAME):
					currency.setName(content);
					break;
				case (RATE):
					currency.setRate(Double.parseDouble(content));
					break;
				case (ABR):
					currency.setAbr(content);
					break;
			}
		}
	}
}