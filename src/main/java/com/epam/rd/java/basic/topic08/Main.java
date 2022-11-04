package com.epam.rd.java.basic.topic08;

import com.epam.rd.java.basic.topic08.container.Exchange;
import com.epam.rd.java.basic.topic08.controller.*;
import com.epam.rd.java.basic.topic08.validator.XMLValidator;
import com.epam.rd.java.basic.topic08.writer.*;

public class Main {
	
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			return;
		}
		
		String xmlFileName = args[0];
		System.out.println("Input ==> " + xmlFileName);
		
		////////////////////////////////////////////////////////
		// DOM
		////////////////////////////////////////////////////////
		
		// get container
		Controller domController = new DOMController(xmlFileName);
		Exchange domExchange = domController.getExchange();

		domExchange.sortByName();
		
		// save
		String outputXmlFile = "output.dom.xml";
		XMLWriter domWriter = new DOMWriter(domExchange);
		domWriter.writeToXml(outputXmlFile);

		// validate
		XMLValidator.validateXML(outputXmlFile, xmlFileName);

		////////////////////////////////////////////////////////
		// SAX
		////////////////////////////////////////////////////////
		
		// get
		Controller saxController = new SAXController(xmlFileName);
		Exchange saxExchange = saxController.getExchange();

		saxExchange.sortByRate();

		
		// save
		outputXmlFile = "output.sax.xml";
		domWriter = new DOMWriter(saxExchange);
		domWriter.writeToXml(outputXmlFile);

		// validate
		XMLValidator.validateXML(outputXmlFile, xmlFileName);
		
		////////////////////////////////////////////////////////
		// StAX
		////////////////////////////////////////////////////////
		
		// get
		Controller staxController = new STAXController(xmlFileName);
		Exchange staxExchange = staxController.getExchange();
		staxExchange.sortByAbr();

		// save
		outputXmlFile = "output.stax.xml";
		XMLWriter staxWriter = new STAXWriter(staxExchange);
		staxWriter.writeToXml(outputXmlFile);

		// validate
		XMLValidator.validateXML(outputXmlFile, xmlFileName);
	}
}