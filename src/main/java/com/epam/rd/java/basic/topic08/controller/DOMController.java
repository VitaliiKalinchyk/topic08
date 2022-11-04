package com.epam.rd.java.basic.topic08.controller;


import com.epam.rd.java.basic.topic08.container.Currency;
import com.epam.rd.java.basic.topic08.container.Exchange;
import static com.epam.rd.java.basic.topic08.container.constants.Constants.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;

import java.io.IOException;
import java.util.*;

/**
 * Controller for DOM parser.
 */
public class DOMController implements Controller {

	private final String XML_FILE_NAME;

	public DOMController(String xmlFileName) {
		this.XML_FILE_NAME = xmlFileName;
	}

	@Override
	public Exchange getExchange() {
		Document document;
		try {
			document = getDocument();
		} catch (IOException | SAXException | ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
		NodeList currencies = document.getElementsByTagName(CURRENCY);
		return new Exchange(getCurrencyList(currencies));
	}

	private List<Currency> getCurrencyList(NodeList currencies) {
		List<Currency> list = new ArrayList<>();
		for (int i = 0; i < currencies.getLength(); i++) {
			Node node = currencies.item(i);
			NodeList currencyNodes = node.getChildNodes();
			list.add(getCurrency(currencyNodes));
		}
		return list;
	}

	private Currency getCurrency(NodeList currencyNodes) {
		Currency currency = new Currency();
		for (int i = 0; i < currencyNodes.getLength(); i++) {
			Node node = currencyNodes.item(i);
			switch (node.getNodeName()) {
				case (ID): currency.setId(Integer.parseInt(node.getTextContent())); break;
				case (NAME): currency.setName(node.getTextContent()); break;
				case (RATE): currency.setRate(Double.parseDouble(node.getTextContent())); break;
				case (ABR): currency.setAbr(node.getTextContent()); break;
			}
		}
		return currency;
	}

	private Document getDocument() throws IOException, SAXException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder documentBuilder = factory.newDocumentBuilder();
		return documentBuilder.parse(XML_FILE_NAME);
	}
}