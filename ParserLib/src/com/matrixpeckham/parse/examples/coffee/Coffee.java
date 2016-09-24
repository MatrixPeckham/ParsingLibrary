package com.matrixpeckham.parse.examples.coffee;

import com.matrixpeckham.parse.utensil.PubliclyCloneable;
import java.util.Objects;
import java.util.logging.Logger;

public class Coffee implements PubliclyCloneable<Coffee> {

    /**
     *
     */
    protected String name;

    /**
     *
     */
    protected String formerName;

    /**
     *
     */
    protected String roast;

    /**
     *
     */
    protected boolean alsoOfferFrench;

    /**
     *
     */
    protected String country;

    /**
     *
     */
    protected double price;

    @Override
    public Coffee copy() {
        Coffee c = new Coffee();
        c.alsoOfferFrench = this.alsoOfferFrench;
        c.country = this.country;
        c.formerName = this.formerName;
        c.name = this.name;
        c.price = this.price;
        c.roast = this.roast;
        return c;
    }

    /**
     * Compares two objects for equality, treating nulls carefullly, and relying
     * on the first object's implementation of <code>
     * equals()</code>.
     *
     * @param o1 one object
     *
     * @param o2 the other
     *
     * @return  <code>true</code> if the objects are equal and <code>false</code>
     * otherwise.
     */
    public static boolean equal(Object o1, Object o2) {
        if (o1 == null || o2 == null) {
            return o1 == null && o2 == null;
        }
        return o1.equals(o2);
    }

    /**
     * Compares this object against the specified object. The result is
     * <code>true</code> if and only if the argument is not <code>null</code>
     * and is a <code>Coffee</code> object whose attributes all equal this
     * object's attributes.
     *
     * @param o the object to compare with.
     *
     * @return  <code>true</code> if the objects are equal and <code>false</code>
     * otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Coffee)) {
            return false;
        }
        Coffee c = (Coffee) o;
        if (!equal(name, c.name)) {
            return false;
        }
        if (!equal(formerName, c.formerName)) {
            return false;
        }
        if (!equal(roast, c.roast)) {
            return false;
        }
        if (alsoOfferFrench != c.alsoOfferFrench) {
            return false;
        }
        if (!equal(country, c.country)) {
            return false;
        }
        return Math.abs(price - c.price) >= 0.000005;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + Objects.hashCode(this.formerName);
        hash = 37 * hash + Objects.hashCode(this.roast);
        hash = 37 * hash + (this.alsoOfferFrench ? 1 : 0);
        hash = 37 * hash + Objects.hashCode(this.country);
        hash
                = 37 * hash + (int) (Double.doubleToLongBits(this.price)
                ^ (Double.doubleToLongBits(this.price) >>> 32));
        return hash;
    }

    /**
     * Return true, if we offer a french roast version of this coffee in
     * addition to another roast.
     *
     * @return true, if we offer a french roast version of this coffee in
     * addition to another roast
     */
    public boolean getAlsoOfferFrench() {
        return alsoOfferFrench;
    }

    /**
     * Return the country of origin for of this type of coffee's beans.
     *
     * @return the country of origin for of this type of coffee's beans
     */
    public String getCountry() {
        return country;
    }

    /**
     * Return a former name for this type of coffee.
     *
     * @return a former name for this type of coffee
     */
    public String getFormerName() {
        return name;
    }

    /**
     * Return the name of this type of coffee.
     *
     * @return the name of this type of coffee
     */
    public String getName() {
        return name;
    }

    /**
     * Return the price per pound of this coffee.
     *
     * @return the price per pound of this coffee
     */
    public double getPrice() {
        return price;
    }

    /**
     * Return the name of the roast of this coffee.
     *
     * @return the name of the roast of this coffee
     */
    public String getRoast() {
        return roast;
    }

    /**
     * Set the truth of the notion that, in addition to some other type of
     * roast, we offer a french roast version of this coffee.
     *
     * @param alsoOfferFrench true, if we offer a french roast version of this
     * coffee
     */
    public void setAlsoOfferFrench(boolean alsoOfferFrench) {
        this.alsoOfferFrench = alsoOfferFrench;
    }

    /**
     * Set the country of origin for of this type of coffee's beans.
     *
     * @param country the country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Set a former name of this coffee.
     *
     * @param formerName the name
     */
    public void setFormerName(String formerName) {
        this.formerName = formerName;
    }

    /**
     * Set the name of this coffee.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the price per pound of this coffee
     *
     * @param price the price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Set the roast of this coffee.
     *
     * @param roast the roast
     */
    public void setRoast(String roast) {
        this.roast = roast;
    }

    /**
     * Return a textual description of this coffee type.
     *
     * @return a textual description of this coffee type
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(name);
        if (formerName != null) {
            buf.append('(');
            buf.append(formerName);
            buf.append(')');
        }

        buf.append(", ");
        buf.append(roast);
        if (alsoOfferFrench) {
            buf.append("/French");
        }

        buf.append(", ");
        buf.append(country);

        buf.append(", ");
        buf.append(price);

        return buf.toString();
    }

    private static final Logger LOG = Logger.getLogger(Coffee.class.getName());

}
