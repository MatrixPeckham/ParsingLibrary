package com.matrixpeckham.parse.examples.coffee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import static javax.xml.parsers.SAXParserFactory.newInstance;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class ShowCoffeeXML extends DefaultHandler {

    /**
     *
     */
    protected HashMap<String, Helper<ArrayList<Coffee>>> helpers;

    /**
     *
     */
    protected Helper<ArrayList<Coffee>> helper;

    /**
     *
     */
    protected ArrayList<Coffee> coffees = new ArrayList<>();

    /**
     * Receive notification of character data inside an element. If there is a
     * helper ready to go, ask the helper to process these characters.
     *
     * @param ch The characters
     *
     * @param start The start position in the character array
     *
     * @param len The number of characters to use from the character array
     *
     */
    @Override
    public void characters(char ch[], int start, int len) {
        if (helper != null) {
            helper.characters(
                    new String(ch, start, len), coffees);
        }
    }

    /**
     * Receive notification of the end of an element, which means that no helper
     * should be active.
     *
     * @param uri all arguments ignored
     * @param rawName
     */
    @Override
    public void endElement(
            String uri, String localName, String rawName) {

        helper = null;
    }
    /*
     * Returns the lookup table that tells which helper to
     * use for which element.
     */

    /**
     *
     * @return
     */
    protected HashMap<String, Helper<ArrayList<Coffee>>> helpers() {
        if (helpers == null) {
            helpers = new HashMap<>();
            helpers.put("coffee", new NewCoffeeHelper());
            helpers.put("name", new NameHelper());
            helpers.put("formerName", new FormerNameHelper());
            helpers.put("roast", new RoastHelper());
            helpers.put("orFrench", new OrFrenchHelper());
            helpers.put("country", new CountryHelper());
            helpers.put("price", new PriceHelper());
        }
        return helpers;
    }

    /**
     * Show how to recognize coffees in an XML file.
     *
     * @param argv
     * @throws java.lang.Exception
     */
    public static void main(String argv[]) throws Exception {
        SAXParserFactory factory = newInstance();
        SAXParser parser = factory.newSAXParser();

        ShowCoffeeXML x = new ShowCoffeeXML();
        parser.parse(ClassLoader.getSystemResourceAsStream(
                "XML Files/coffee.xml"), x);

        Iterator<Coffee> e = x.coffees.iterator();
        while (e.hasNext()) {
            Coffee c = e.next();
            System.out.println(c);
        }
    }

    /**
     * Receive notification of the start of an element.
     *
     * <p>
     * If the <code>helpers</code> HashMap has a key of for the given element
     * name, then inform the helper that this element has appeared.</p>
     *
     * @param uri the uniform resource identifier, ignored
     *
     * @param local the local name, ignored
     *
     * @param raw the raw XML 1.0 name, which is the helper lookup key
     * @param atts
     */
    @Override
    public void startElement(
            String uri, String local, String raw, Attributes atts) {

        helper = helpers().get(raw);
        if (helper != null) {
            helper.startElement(coffees);
        }
    }

    private static final Logger LOG
            = Logger.getLogger(ShowCoffeeXML.class.getName());

}
