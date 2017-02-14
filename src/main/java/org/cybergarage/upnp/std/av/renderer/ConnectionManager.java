package org.cybergarage.upnp.std.av.renderer;

import org.cybergarage.upnp.Action;
import org.cybergarage.upnp.StateVariable;
import org.cybergarage.upnp.control.ActionListener;
import org.cybergarage.upnp.control.QueryListener;
import org.cybergarage.util.Mutex;

public class ConnectionManager implements ActionListener, QueryListener {
    public static final String AVTRANSPORTID = "AVTransportID";
    public static final String CONNECTIONCOMPLETE = "ConnectionComplete";
    public static final String CONNECTIONID = "ConnectionID";
    public static final String CONNECTIONIDS = "ConnectionIDs";
    public static final String CONTENTFORMATMISMATCH = "ContentFormatMismatch";
    public static final String CURRENTCONNECTIONIDS = "CurrentConnectionIDs";
    public static final String DIRECTION = "Direction";
    public static final String GETCURRENTCONNECTIONIDS = "GetCurrentConnectionIDs";
    public static final String GETCURRENTCONNECTIONINFO = "GetCurrentConnectionInfo";
    public static final String GETPROTOCOLINFO = "GetProtocolInfo";
    public static final String HTTP_GET = "http-get";
    public static final String INPUT = "Input";
    public static final String INSUFFICIENTBANDWIDTH = "InsufficientBandwidth";
    public static final String OK = "OK";
    public static final String OUTPUT = "Output";
    public static final String PEERCONNECTIONID = "PeerConnectionID";
    public static final String PEERCONNECTIONMANAGER = "PeerConnectionManager";
    public static final String PREPAREFORCONNECTION = "PrepareForConnection";
    public static final String PROTOCOLINFO = "ProtocolInfo";
    public static final String RCSID = "RcsID";
    public static final String REMOTEPROTOCOLINFO = "RemoteProtocolInfo";
    public static final String SCPD = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<scpd xmlns=\"urn:schemas-upnp-org:service-1-0\">\n   <specVersion>\n      <major>1</major>\n      <minor>0</minor>\n\t</specVersion>\n\t<actionList>\n\t\t<action>\n         <name>GetCurrentConnectionInfo</name>\n         <argumentList>\n            <argument>\n               <name>ConnectionID</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_ConnectionID</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>RcsID</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_RcsID</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>AVTransportID</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_AVTransportID</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>ProtocolInfo</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_ProtocolInfo</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>PeerConnectionManager</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_ConnectionManager</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>PeerConnectionID</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_ConnectionID</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>Direction</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_Direction</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>Status</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_ConnectionStatus</relatedStateVariable>\n            </argument>\n         </argumentList>\n      </action>\n      <action>\n         <name>GetProtocolInfo</name>\n         <argumentList>\n            <argument>\n               <name>Source</name>\n               <direction>out</direction>\n               <relatedStateVariable>SourceProtocolInfo</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>Sink</name>\n               <direction>out</direction>\n               <relatedStateVariable>SinkProtocolInfo</relatedStateVariable>\n            </argument>\n         </argumentList>\n      </action>\n      <action>\n         <name>GetCurrentConnectionIDs</name>\n         <argumentList>\n            <argument>\n               <name>ConnectionIDs</name>\n               <direction>out</direction>\n               <relatedStateVariable>CurrentConnectionIDs</relatedStateVariable>\n            </argument>\n         </argumentList>\n      </action>\n   </actionList>\n   <serviceStateTable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_ProtocolInfo</name>\n         <dataType>string</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_ConnectionStatus</name>\n         <dataType>string</dataType>\n         <allowedValueList>\n            <allowedValue>OK</allowedValue>\n            <allowedValue>ContentFormatMismatch</allowedValue>\n            <allowedValue>InsufficientBandwidth</allowedValue>\n            <allowedValue>UnreliableChannel</allowedValue>\n            <allowedValue>Unknown</allowedValue>\n         </allowedValueList>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_AVTransportID</name>\n         <dataType>i4</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_RcsID</name>\n         <dataType>i4</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_ConnectionID</name>\n         <dataType>i4</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_ConnectionManager</name>\n         <dataType>string</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"yes\">\n         <name>SourceProtocolInfo</name>\n         <dataType>string</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"yes\">\n         <name>SinkProtocolInfo</name>\n         <dataType>string</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_Direction</name>\n         <dataType>string</dataType>\n         <allowedValueList>\n            <allowedValue>Input</allowedValue>\n            <allowedValue>Output</allowedValue>\n         </allowedValueList>\n      </stateVariable>\n      <stateVariable sendEvents=\"yes\">\n         <name>CurrentConnectionIDs</name>\n         <dataType>string</dataType>\n      </stateVariable>\n   </serviceStateTable>\n</scpd>";
    public static final String SERVICE_TYPE = "urn:schemas-upnp-org:service:ConnectionManager:1";
    public static final String SINK = "Sink";
    public static final String SINKPROTOCOLINFO = "SinkProtocolInfo";
    public static final String SOURCE = "Source";
    public static final String SOURCEPROTOCOLINFO = "SourceProtocolInfo";
    public static final String STATUS = "Status";
    public static final String UNKNOWN = "Unknown";
    public static final String UNRELIABLECHANNEL = "UnreliableChannel";
    private ConnectionInfoList conInfoList = new ConnectionInfoList();
    private int maxConnectionID = 0;
    private MediaRenderer mediaRenderer;
    private Mutex mutex = new Mutex();

    public ConnectionManager(MediaRenderer render) {
        setMediaRenderer(render);
    }

    private void setMediaRenderer(MediaRenderer render) {
        this.mediaRenderer = render;
    }

    public MediaRenderer getMediaRenderer() {
        return this.mediaRenderer;
    }

    public void lock() {
        this.mutex.lock();
    }

    public void unlock() {
        this.mutex.unlock();
    }

    public int getNextConnectionID() {
        lock();
        this.maxConnectionID++;
        unlock();
        return this.maxConnectionID;
    }

    public ConnectionInfoList getConnectionInfoList() {
        return this.conInfoList;
    }

    public ConnectionInfo getConnectionInfo(int id) {
        int size = this.conInfoList.size();
        for (int n = 0; n < size; n++) {
            ConnectionInfo info = this.conInfoList.getConnectionInfo(n);
            if (info.getID() == id) {
                return info;
            }
        }
        return null;
    }

    public void addConnectionInfo(ConnectionInfo info) {
        lock();
        this.conInfoList.add(info);
        unlock();
    }

    public void removeConnectionInfo(int id) {
        lock();
        int size = this.conInfoList.size();
        for (int n = 0; n < size; n++) {
            ConnectionInfo info = this.conInfoList.getConnectionInfo(n);
            if (info.getID() == id) {
                this.conInfoList.remove(info);
                break;
            }
        }
        unlock();
    }

    public void removeConnectionInfo(ConnectionInfo info) {
        lock();
        this.conInfoList.remove(info);
        unlock();
    }

    public boolean actionControlReceived(Action action) {
        if (action.getName() == null) {
            return false;
        }
        MediaRenderer dmr = getMediaRenderer();
        if (dmr == null) {
            return false;
        }
        ActionListener listener = dmr.getActionListener();
        if (listener == null) {
            return false;
        }
        listener.actionControlReceived(action);
        return false;
    }

    private boolean getCurrentConnectionIDs(Action action) {
        String conIDs = "";
        lock();
        int size = this.conInfoList.size();
        for (int n = 0; n < size; n++) {
            ConnectionInfo info = this.conInfoList.getConnectionInfo(n);
            if (n > 0) {
                conIDs = new StringBuilder(String.valueOf(conIDs)).append(",").toString();
            }
            conIDs = new StringBuilder(String.valueOf(conIDs)).append(Integer.toString(info.getID())).toString();
        }
        action.getArgument("ConnectionIDs").setValue(conIDs);
        unlock();
        return true;
    }

    private boolean getCurrentConnectionInfo(Action action) {
        int id = action.getArgument("RcsID").getIntegerValue();
        lock();
        ConnectionInfo info = getConnectionInfo(id);
        if (info != null) {
            action.getArgument("RcsID").setValue(info.getRcsID());
            action.getArgument("AVTransportID").setValue(info.getAVTransportID());
            action.getArgument("PeerConnectionManager").setValue(info.getPeerConnectionManager());
            action.getArgument("PeerConnectionID").setValue(info.getPeerConnectionID());
            action.getArgument("Direction").setValue(info.getDirection());
            action.getArgument("Status").setValue(info.getStatus());
        } else {
            action.getArgument("RcsID").setValue(-1);
            action.getArgument("AVTransportID").setValue(-1);
            action.getArgument("PeerConnectionManager").setValue("");
            action.getArgument("PeerConnectionID").setValue(-1);
            action.getArgument("Direction").setValue("Output");
            action.getArgument("Status").setValue("Unknown");
        }
        unlock();
        return true;
    }

    public boolean queryControlReceived(StateVariable stateVar) {
        return false;
    }
}
