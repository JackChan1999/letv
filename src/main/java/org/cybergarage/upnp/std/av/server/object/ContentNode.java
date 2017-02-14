package org.cybergarage.upnp.std.av.server.object;

import java.io.PrintWriter;
import org.cybergarage.upnp.std.av.server.ContentDirectory;
import org.cybergarage.upnp.std.av.server.MediaServer;
import org.cybergarage.upnp.std.av.server.UPnP;
import org.cybergarage.upnp.std.av.server.object.container.ContainerNode;
import org.cybergarage.upnp.std.av.server.object.item.ItemNode;
import org.cybergarage.xml.Attribute;
import org.cybergarage.xml.Node;

public abstract class ContentNode extends Node {
    public static final String ID = "id";
    public static final String PARENT_ID = "parentID";
    public static final String RESTRICTED = "restricted";
    public static final String UNKNOWN = "UNKNOWN";
    private ContentDirectory contentDir;
    private ContentPropertyList propList = new ContentPropertyList();

    public abstract void set(Node node);

    public ContentNode() {
        setID(0);
        setParentID(-1);
        setRestricted(1);
        setContentDirectory(null);
    }

    public void setContentDirectory(ContentDirectory cdir) {
        this.contentDir = cdir;
    }

    public ContentDirectory getContentDirectory() {
        return this.contentDir;
    }

    public MediaServer getMediaServer() {
        return getContentDirectory().getMediaServer();
    }

    public boolean isContainerNode() {
        if (this instanceof ContainerNode) {
            return true;
        }
        return false;
    }

    public boolean isItemNode() {
        if (this instanceof ItemNode) {
            return true;
        }
        return false;
    }

    public int getNProperties() {
        return this.propList.size();
    }

    public ContentProperty getProperty(int index) {
        return this.propList.getContentProperty(index);
    }

    public ContentProperty getProperty(String name) {
        return this.propList.getContentProperty(name);
    }

    public void addProperty(ContentProperty prop) {
        this.propList.add(prop);
    }

    public void insertPropertyAt(ContentProperty prop, int index) {
        this.propList.insertElementAt(prop, index);
    }

    public void addProperty(String name, String value) {
        addProperty(new ContentProperty(name, value));
    }

    public boolean removeProperty(ContentProperty prop) {
        return this.propList.remove(prop);
    }

    public boolean removeProperty(String name) {
        return removeProperty(getProperty(name));
    }

    public boolean hasProperties() {
        if (getNProperties() > 0) {
            return true;
        }
        return false;
    }

    public void setProperty(String name, String value) {
        ContentProperty prop = getProperty(name);
        if (prop != null) {
            prop.setValue(value);
        } else {
            addProperty(new ContentProperty(name, value));
        }
    }

    public void setProperty(String name, int value) {
        setProperty(name, Integer.toString(value));
    }

    public void setProperty(String name, long value) {
        setProperty(name, Long.toString(value));
    }

    public String getPropertyValue(String name) {
        ContentProperty prop = getProperty(name);
        if (prop != null) {
            return prop.getValue();
        }
        return "";
    }

    public int getPropertyIntegerValue(String name) {
        try {
            return Integer.parseInt(getPropertyValue(name));
        } catch (Exception e) {
            return 0;
        }
    }

    public long getPropertyLongValue(String name) {
        try {
            return Long.parseLong(getPropertyValue(name));
        } catch (Exception e) {
            return 0;
        }
    }

    public void setPropertyAttribure(String propName, String attrName, String value) {
        ContentProperty prop = getProperty(propName);
        if (prop == null) {
            prop = new ContentProperty(propName, "");
            addProperty(prop);
        }
        prop.setAttribute(attrName, value);
    }

    public String getPropertyAttribureValue(String propName, String attrName) {
        ContentProperty prop = getProperty(propName);
        if (prop != null) {
            return prop.getAttributeValue(attrName);
        }
        return "";
    }

    public void setID(int id) {
        setAttribute("id", id);
    }

    public void setID(String id) {
        setAttribute("id", id);
    }

    public String getID() {
        return getAttributeValue("id");
    }

    public void setParentID(int id) {
        setAttribute("parentID", id);
    }

    public void setParentID(String id) {
        setAttribute("parentID", id);
    }

    public String getParentID() {
        return getAttributeValue("parentID");
    }

    public void setRestricted(int id) {
        setAttribute("restricted", id);
    }

    public int getRestricted() {
        return getAttributeIntegerValue("restricted");
    }

    public void setTitle(String title) {
        setProperty("dc:title", title);
    }

    public String getTitle() {
        return getPropertyValue("dc:title");
    }

    public void setUPnPClass(String title) {
        setProperty("upnp:class", title);
    }

    public String getUPnPClass() {
        return getPropertyValue("upnp:class");
    }

    public boolean isUPnPClassStartWith(String targetClass) {
        if (targetClass == null) {
            return false;
        }
        String upnpClass = getUPnPClass();
        if (upnpClass != null) {
            return upnpClass.startsWith(targetClass);
        }
        return false;
    }

    public void setWriteStatus(String status) {
        setProperty(UPnP.WRITE_STATUS, status);
    }

    public String getWriteStatus() {
        return getPropertyValue(UPnP.WRITE_STATUS);
    }

    private void outputPropertyAttributes(PrintWriter ps, ContentProperty prop) {
        int nAttributes = prop.getNAttributes();
        for (int n = 0; n < nAttributes; n++) {
            Attribute attr = prop.getAttribute(n);
            ps.print(" " + attr.getName() + "=\"" + attr.getValue() + "\"");
        }
    }

    public void output(PrintWriter ps, int indentLevel, boolean hasChildNode) {
        String indentString = getIndentLevelString(indentLevel);
        String name = getName();
        String value = getValue();
        if (hasNodes() || hasProperties()) {
            int n;
            ps.print(new StringBuilder(String.valueOf(indentString)).append(SearchCriteria.LT).append(name).toString());
            outputAttributes(ps);
            ps.println(SearchCriteria.GT);
            int nProps = getNProperties();
            for (n = 0; n < nProps; n++) {
                String propIndentString = getIndentLevelString(indentLevel + 1);
                ContentProperty prop = getProperty(n);
                String propName = prop.getName();
                String propValue = prop.getValue();
                if (prop.hasAttributes()) {
                    ps.print(new StringBuilder(String.valueOf(propIndentString)).append(SearchCriteria.LT).append(propName).toString());
                    outputPropertyAttributes(ps, prop);
                    ps.println(new StringBuilder(SearchCriteria.GT).append(propValue).append("</").append(propName).append(SearchCriteria.GT).toString());
                } else {
                    ps.println(new StringBuilder(String.valueOf(propIndentString)).append(SearchCriteria.LT).append(propName).append(SearchCriteria.GT).append(propValue).append("</").append(propName).append(SearchCriteria.GT).toString());
                }
            }
            if (hasChildNode) {
                int nChildNodes = getNNodes();
                for (n = 0; n < nChildNodes; n++) {
                    getNode(n).output(ps, indentLevel + 1, true);
                }
            }
            ps.println(new StringBuilder(String.valueOf(indentString)).append("</").append(name).append(SearchCriteria.GT).toString());
            return;
        }
        ps.print(new StringBuilder(String.valueOf(indentString)).append(SearchCriteria.LT).append(name).toString());
        outputAttributes(ps);
        ps.println(new StringBuilder(SearchCriteria.GT).append(value).append("</").append(name).append(SearchCriteria.GT).toString());
    }
}
