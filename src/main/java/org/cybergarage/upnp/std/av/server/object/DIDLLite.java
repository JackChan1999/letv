package org.cybergarage.upnp.std.av.server.object;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import org.cybergarage.util.Debug;

public class DIDLLite {
    public static final String CONTAINER = "container";
    public static final String ID = "id";
    public static final String NAME = "DIDL-Lite";
    public static final String OBJECT_CONTAINER = "object.container";
    public static final String PARENTID = "parentID";
    public static final String RES = "res";
    public static final String RESTICTED = "restricted";
    public static final String RES_PROTOCOLINFO = "protocolInfo";
    public static final String SEARCHABLE = "searchable";
    public static final String XMLNS = "xmlns";
    public static final String XMLNS_DC = "xmlns:dc";
    public static final String XMLNS_DC_URL = "http://purl.org/dc/elements/1.1/";
    public static final String XMLNS_UPNP = "xmlns:upnp";
    public static final String XMLNS_UPNP_URL = "urn:schemas-upnp-org:metadata-1-0/upnp/";
    public static final String XMLNS_URL = "urn:schemas-upnp-org:metadata-1-0/DIDL-Lite/";
    private ContentNodeList nodeList = new ContentNodeList();

    public void setContentNode(ContentNode node) {
        this.nodeList.clear();
        this.nodeList.add(node);
    }

    public void addContentNode(ContentNode node) {
        this.nodeList.add(node);
    }

    public int getNContentNodes() {
        return this.nodeList.size();
    }

    public ContentNode getContentNode(int n) {
        return this.nodeList.getContentNode(n);
    }

    public void output(PrintWriter ps) {
        ps.print("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        DIDLLiteNode didlNode = new DIDLLiteNode();
        String name = didlNode.getName();
        ps.print(new StringBuilder(SearchCriteria.LT).append(name).toString());
        didlNode.outputAttributes(ps);
        ps.println(SearchCriteria.GT);
        int nNodes = getNContentNodes();
        for (int n = 0; n < nNodes; n++) {
            getContentNode(n).output(ps, 1, false);
        }
        ps.println("</" + name + SearchCriteria.GT);
    }

    public String toString() {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            PrintWriter pr = new PrintWriter(new OutputStreamWriter(byteOut, "UTF-8"));
            output(pr);
            pr.flush();
            return byteOut.toString();
        } catch (UnsupportedEncodingException e) {
            Debug.warning(e);
            return "";
        }
    }
}
