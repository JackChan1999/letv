package org.cybergarage.upnp.std.av.server.object;

import org.cybergarage.xml.Attribute;
import org.cybergarage.xml.AttributeList;

public class ContentProperty {
    private AttributeList attrList = new AttributeList();
    private String name = new String();
    private String value = new String();

    public ContentProperty(String name, String value) {
        setName(name);
        setValue(value);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public int getNAttributes() {
        return this.attrList.size();
    }

    public Attribute getAttribute(int index) {
        return this.attrList.getAttribute(index);
    }

    public Attribute getAttribute(String name) {
        return this.attrList.getAttribute(name);
    }

    public void addAttribute(Attribute attr) {
        this.attrList.add(attr);
    }

    public void insertAttributeAt(Attribute attr, int index) {
        this.attrList.insertElementAt(attr, index);
    }

    public void addAttribute(String name, String value) {
        addAttribute(new Attribute(name, value));
    }

    public boolean removeAttribute(Attribute attr) {
        return this.attrList.remove(attr);
    }

    public boolean removeAttribute(String name) {
        return removeAttribute(getAttribute(name));
    }

    public boolean hasAttributes() {
        if (getNAttributes() > 0) {
            return true;
        }
        return false;
    }

    public void setAttribute(String name, String value) {
        Attribute attr = getAttribute(name);
        if (attr != null) {
            attr.setValue(value);
        } else {
            addAttribute(new Attribute(name, value));
        }
    }

    public void setAttribute(String name, int value) {
        setAttribute(name, Integer.toString(value));
    }

    public String getAttributeValue(String name) {
        Attribute attr = getAttribute(name);
        if (attr != null) {
            return attr.getValue();
        }
        return "";
    }

    public int getAttributeIntegerValue(String name) {
        try {
            return Integer.parseInt(getAttributeValue(name));
        } catch (Exception e) {
            return 0;
        }
    }
}
