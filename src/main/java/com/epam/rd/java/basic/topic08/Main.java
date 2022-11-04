package com.epam.rd.java.basic.topic08;

import com.epam.rd.java.basic.topic08.container.Exchange;
import com.epam.rd.java.basic.topic08.controller.*;

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
		Exchange DomExchange = domController.getExchange();

		DomExchange.sortByName();
		
		// save
		String outputXmlFile = "output.dom.xml";
		// PLACE YOUR CODE HERE

		////////////////////////////////////////////////////////
		// SAX
		////////////////////////////////////////////////////////
		
		// get
		Controller saxController = new SAXController(xmlFileName);
		Exchange SAXExchange = saxController.getExchange();

		SAXExchange.sortByRate();

		
		// save
		outputXmlFile = "output.sax.xml";
		// PLACE YOUR CODE HERE
		
		////////////////////////////////////////////////////////
		// StAX
		////////////////////////////////////////////////////////
		
		// get
		Controller staxController = new STAXController(xmlFileName);
		Exchange STAXExchange = staxController.getExchange();
		STAXExchange.sortByAbr();
		
		// save
		outputXmlFile = "output.stax.xml";
		// PLACE YOUR CODE HERE
	}
}