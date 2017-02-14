package org.cybergarage.upnp.std.av.renderer;

import java.io.File;
import org.cybergarage.http.HTTPRequest;
import org.cybergarage.net.HostInterface;
import org.cybergarage.upnp.Device;
import org.cybergarage.upnp.UPnP;
import org.cybergarage.upnp.control.ActionListener;
import org.cybergarage.upnp.device.InvalidDescriptionException;
import org.cybergarage.util.Debug;

public class MediaRenderer extends Device {
    public static final int DEFAULT_HTTP_PORT = 39520;
    public static final String DESCRIPTION = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<root xmlns=\"urn:schemas-upnp-org:device-1-0\">\n   <specVersion>\n      <major>1</major>\n      <minor>0</minor>\n   </specVersion>\n   <device>\n      <deviceType>urn:schemas-upnp-org:device:MediaRender:1</deviceType>\n      <friendlyName>Cyber Garage Media Render</friendlyName>\n      <manufacturer>Cyber Garage</manufacturer>\n      <manufacturerURL>http://www.cybergarage.org</manufacturerURL>\n      <modelDescription>Provides content through UPnP ContentDirectory service</modelDescription>\n      <modelName>Cyber Garage Media Render</modelName>\n      <modelNumber>1.0</modelNumber>\n      <modelURL>http://www.cybergarage.org</modelURL>\n      <UDN>uuid:362d9414-31a0-48b6-b684-2b4bd38391d0</UDN>\n      <serviceList>\n         <service>\n            <serviceType>urn:schemas-upnp-org:service:RenderingControl:1</serviceType>\n            <serviceId>RenderingControl</serviceId>\n         </service>\n         <service>\n            <serviceType>urn:schemas-upnp-org:service:ConnectionManager:1</serviceType>\n            <serviceId>ConnectionManager</serviceId>\n         </service>\n         <service>\n            <serviceType>urn:schemas-upnp-org:service:AVTransport:1</serviceType>\n            <serviceId>AVTransport</serviceId>\n         </service>\n      </serviceList>\n   </device>\n</root>";
    private static final String DESCRIPTION_FILE_NAME = "description/description.xml";
    public static final String DEVICE_TYPE = "urn:schemas-upnp-org:device:MediaRenderer:1";
    private ActionListener actionListener;
    private AVTransport avTrans;
    private ConnectionManager conMan;
    private RenderingControl renCon;

    public MediaRenderer(String descriptionFileName) throws InvalidDescriptionException {
        super(new File(descriptionFileName));
        initialize();
    }

    public MediaRenderer() {
        try {
            initialize(DESCRIPTION, RenderingControl.SCPD, "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<scpd xmlns=\"urn:schemas-upnp-org:service-1-0\">\n   <specVersion>\n      <major>1</major>\n      <minor>0</minor>\n\t</specVersion>\n\t<actionList>\n\t\t<action>\n         <name>GetCurrentConnectionInfo</name>\n         <argumentList>\n            <argument>\n               <name>ConnectionID</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_ConnectionID</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>RcsID</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_RcsID</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>AVTransportID</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_AVTransportID</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>ProtocolInfo</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_ProtocolInfo</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>PeerConnectionManager</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_ConnectionManager</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>PeerConnectionID</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_ConnectionID</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>Direction</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_Direction</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>Status</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_ConnectionStatus</relatedStateVariable>\n            </argument>\n         </argumentList>\n      </action>\n      <action>\n         <name>GetProtocolInfo</name>\n         <argumentList>\n            <argument>\n               <name>Source</name>\n               <direction>out</direction>\n               <relatedStateVariable>SourceProtocolInfo</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>Sink</name>\n               <direction>out</direction>\n               <relatedStateVariable>SinkProtocolInfo</relatedStateVariable>\n            </argument>\n         </argumentList>\n      </action>\n      <action>\n         <name>GetCurrentConnectionIDs</name>\n         <argumentList>\n            <argument>\n               <name>ConnectionIDs</name>\n               <direction>out</direction>\n               <relatedStateVariable>CurrentConnectionIDs</relatedStateVariable>\n            </argument>\n         </argumentList>\n      </action>\n   </actionList>\n   <serviceStateTable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_ProtocolInfo</name>\n         <dataType>string</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_ConnectionStatus</name>\n         <dataType>string</dataType>\n         <allowedValueList>\n            <allowedValue>OK</allowedValue>\n            <allowedValue>ContentFormatMismatch</allowedValue>\n            <allowedValue>InsufficientBandwidth</allowedValue>\n            <allowedValue>UnreliableChannel</allowedValue>\n            <allowedValue>Unknown</allowedValue>\n         </allowedValueList>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_AVTransportID</name>\n         <dataType>i4</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_RcsID</name>\n         <dataType>i4</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_ConnectionID</name>\n         <dataType>i4</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_ConnectionManager</name>\n         <dataType>string</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"yes\">\n         <name>SourceProtocolInfo</name>\n         <dataType>string</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"yes\">\n         <name>SinkProtocolInfo</name>\n         <dataType>string</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_Direction</name>\n         <dataType>string</dataType>\n         <allowedValueList>\n            <allowedValue>Input</allowedValue>\n            <allowedValue>Output</allowedValue>\n         </allowedValueList>\n      </stateVariable>\n      <stateVariable sendEvents=\"yes\">\n         <name>CurrentConnectionIDs</name>\n         <dataType>string</dataType>\n      </stateVariable>\n   </serviceStateTable>\n</scpd>", AVTransport.SCPD);
        } catch (InvalidDescriptionException e) {
        }
    }

    public MediaRenderer(String description, String renderCtrlSCPD, String conMgrSCPD, String avTransSCPD) throws InvalidDescriptionException {
        initialize(description, renderCtrlSCPD, conMgrSCPD, avTransSCPD);
    }

    private void initialize(String description, String renderCtrlSCPD, String conMgrSCPD, String avTransSCPD) throws InvalidDescriptionException {
        loadDescription(description);
        getService(RenderingControl.SERVICE_TYPE).loadSCPD(renderCtrlSCPD);
        getService("urn:schemas-upnp-org:service:ConnectionManager:1").loadSCPD(conMgrSCPD);
        getService(AVTransport.SERVICE_TYPE).loadSCPD(avTransSCPD);
        initialize();
    }

    private void initialize() {
        UPnP.setEnable(9);
        setInterfaceAddress(HostInterface.getHostAddress(0));
        setHTTPPort(DEFAULT_HTTP_PORT);
        this.renCon = new RenderingControl(this);
        this.conMan = new ConnectionManager(this);
        this.avTrans = new AVTransport(this);
        setActionListener(null);
    }

    protected void finalize() {
        stop();
    }

    public ConnectionManager getConnectionManager() {
        return this.conMan;
    }

    public RenderingControl getRenderingControl() {
        return this.renCon;
    }

    public AVTransport getAVTransport() {
        return this.avTrans;
    }

    public void setInterfaceAddress(String ifaddr) {
        HostInterface.setInterface(ifaddr);
    }

    public String getInterfaceAddress() {
        return HostInterface.getInterface();
    }

    public void httpRequestRecieved(HTTPRequest httpReq) {
        Debug.message("uri = " + httpReq.getURI());
        super.httpRequestRecieved(httpReq);
    }

    public void setActionListener(ActionListener listener) {
        this.actionListener = listener;
    }

    public ActionListener getActionListener() {
        return this.actionListener;
    }

    public boolean start() {
        super.start();
        return true;
    }

    public boolean stop() {
        super.stop();
        return true;
    }

    public void update() {
    }
}
