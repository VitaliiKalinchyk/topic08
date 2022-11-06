package com.epam.rd.java.basic.topic08.controller;

import com.epam.rd.java.basic.topic08.container.Currency;
import com.epam.rd.java.basic.topic08.container.Exchange;
import com.epam.rd.java.basic.topic08.controller.handler.SAXHandler;

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

	public SAXController(String xmlFileName) {
		XML_FILE_NAME = xmlFileName;
	}

	@Override
	public Exchange getExchange() {
		return new Exchange(parseXML());
	}

	private List<Currency> parseXML() {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXHandler handler = new SAXHandler();
		SAXParser parser;
		try {
			parser = saxParserFactory.newSAXParser();
			parser.parse(XML_FILE_NAME, handler);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new RuntimeException(e);
		}
		return handler.getCurrencyList();
	}
}