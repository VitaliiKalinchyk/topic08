package com.epam.rd.java.basic.topic08.controller.handler;

import com.epam.rd.java.basic.topic08.container.Currency;
import static com.epam.rd.java.basic.topic08.container.constants.Constants.*;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

public class SAXHandler extends DefaultHandler {

    private final List<Currency> CURRENCY_LIST = new ArrayList<>();

    private Currency currency;

    private String currentElement;

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

    public List<Currency> getCurrencyList() {
        return CURRENCY_LIST;
    }
}