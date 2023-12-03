package com.matrixpeckham.parse.examples.coffee;

import static javax.xml.parsers.SAXParserFactory.newInstance;

import java.util.*;
import java.util.logging.Logger;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Show the recognition of a list of types of coffee,
 * reading from an XML file.
 * <p>
 * This class keeps a hashtable whose keys are element
 * names, such as "roast", and whose values are subclasses
 * of <code>Helper</code>, such as <code>RoastHelper</code>.
 * <p>
 * When an object of this class receives an event indicating
 * that the parser has found a new element, it looks up the
 * element name in the hashtable, to find the right helper
 * for the element. For example, on seeing the "roast"
 * element, this class sets its <code>helper</code> object
 * to be an instance of <code>RoastHelper</code>. Then, when
 * this class receives characters from the parser, it passes
 * the characters to the current helper.
 * <p>
 * Helpers expect a target object, and this class
 * consistently uses a <code>Vector</code> of <code>Coffee
 * </code> objects as the target for helpers.
 */
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
     * @param ch    The characters
     *
     * @param start The start position in the character array
     *
     * @param len   The number of characters to use from the character array
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
     * @param uri     all arguments ignored
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
     *
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
     * <p>
     * <p>
     * If the <code>helpers</code> HashMap has a key of for the given element
     * name, then inform the helper that this element has appeared.</p>
     *
     * @param uri   the uniform resource identifier, ignored
     *
     * @param local the local name, ignored
     *
     * @param raw   the raw XML 1.0 name, which is the helper lookup key
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
