package org.cybergarage.upnp.std.av.server;

import com.letv.ads.ex.utils.PlayConstantUtils.SPConstant;
import com.letv.pp.utils.NetworkUtils;
import java.io.File;
import java.io.InputStream;
import java.util.StringTokenizer;
import org.cybergarage.http.HTTPRequest;
import org.cybergarage.http.HTTPResponse;
import org.cybergarage.http.Parameter;
import org.cybergarage.http.ParameterList;
import org.cybergarage.upnp.Action;
import org.cybergarage.upnp.StateVariable;
import org.cybergarage.upnp.control.ActionListener;
import org.cybergarage.upnp.control.QueryListener;
import org.cybergarage.upnp.std.av.server.action.BrowseAction;
import org.cybergarage.upnp.std.av.server.action.SearchAction;
import org.cybergarage.upnp.std.av.server.object.ContentNode;
import org.cybergarage.upnp.std.av.server.object.ContentNodeList;
import org.cybergarage.upnp.std.av.server.object.DIDLLite;
import org.cybergarage.upnp.std.av.server.object.Format;
import org.cybergarage.upnp.std.av.server.object.FormatList;
import org.cybergarage.upnp.std.av.server.object.SearchCap;
import org.cybergarage.upnp.std.av.server.object.SearchCapList;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;
import org.cybergarage.upnp.std.av.server.object.SearchCriteriaList;
import org.cybergarage.upnp.std.av.server.object.SortCap;
import org.cybergarage.upnp.std.av.server.object.SortCapList;
import org.cybergarage.upnp.std.av.server.object.SortCriterionList;
import org.cybergarage.upnp.std.av.server.object.container.ContainerNode;
import org.cybergarage.upnp.std.av.server.object.container.RootNode;
import org.cybergarage.upnp.std.av.server.object.item.ItemNode;
import org.cybergarage.upnp.std.av.server.object.search.IdSearchCap;
import org.cybergarage.upnp.std.av.server.object.search.TitleSearchCap;
import org.cybergarage.upnp.std.av.server.object.sort.DCDateSortCap;
import org.cybergarage.upnp.std.av.server.object.sort.DCTitleSortCap;
import org.cybergarage.upnp.std.av.server.object.sort.UPnPClassSortCap;
import org.cybergarage.util.Debug;
import org.cybergarage.util.Mutex;
import org.cybergarage.util.StringUtil;
import org.cybergarage.util.ThreadCore;

public class ContentDirectory extends ThreadCore implements ActionListener, QueryListener {
    public static final String BROWSE = "Browse";
    public static final String BROWSEDIRECTCHILDREN = "BrowseDirectChildren";
    public static final String BROWSEFLAG = "BrowseFlag";
    public static final String BROWSEMETADATA = "BrowseMetadata";
    public static final String COMPLETED = "COMPLETED";
    public static final String CONTAINERID = "ContainerID";
    public static final String CONTAINERUPDATEIDS = "ContainerUpdateIDs";
    public static final String CONTENT_EXPORT_URI = "/ExportContent";
    public static final String CONTENT_ID = "id";
    public static final String CONTENT_IMPORT_URI = "/ImportContent";
    public static final String CREATEOBJECT = "CreateObject";
    public static final String CREATEREFERENCE = "CreateReference";
    public static final String CURRENTTAGVALUE = "CurrentTagValue";
    private static final int DEFAULT_CONTENTUPDATE_INTERVAL = 60000;
    private static final int DEFAULT_SYSTEMUPDATEID_INTERVAL = 2000;
    public static final String DELETERESOURCE = "DeleteResource";
    public static final String DESTINATIONURI = "DestinationURI";
    public static final String DESTROYOBJECT = "DestroyObject";
    public static final String ELEMENTS = "Elements";
    public static final String ERROR = "ERROR";
    public static final String EXPORTRESOURCE = "ExportResource";
    public static final String FILTER = "Filter";
    public static final String GETSEARCHCAPABILITIES = "GetSearchCapabilities";
    public static final String GETSORTCAPABILITIES = "GetSortCapabilities";
    public static final String GETSYSTEMUPDATEID = "GetSystemUpdateID";
    public static final String GETTRANSFERPROGRESS = "GetTransferProgress";
    public static final String ID = "Id";
    public static final String IMPORTRESOURCE = "ImportResource";
    public static final String IN_PROGRESS = "IN_PROGRESS";
    public static final String NEWID = "NewID";
    public static final String NEWTAGVALUE = "NewTagValue";
    public static final String NUMBERRETURNED = "NumberReturned";
    public static final String OBJECTID = "ObjectID";
    public static final String REQUESTEDCOUNT = "RequestedCount";
    public static final String RESOURCEURI = "ResourceURI";
    public static final String RESULT = "Result";
    public static final String SCPD = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<scpd xmlns=\"urn:schemas-upnp-org:service-1-0\">\n   <specVersion>\n      <major>1</major>\n      <minor>0</minor>\n   </specVersion>\n   <actionList>\n      <action>\n         <name>ExportResource</name>\n         <argumentList>\n            <argument>\n               <name>SourceURI</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_URI</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>DestinationURI</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_URI</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>TransferID</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_TransferID</relatedStateVariable>\n            </argument>\n         </argumentList>\n      </action>\n      <action>\n         <name>StopTransferResource</name>\n         <argumentList>\n            <argument>\n               <name>TransferID</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_TransferID</relatedStateVariable>\n            </argument>\n         </argumentList>\n      </action>\n      <action>\n         <name>DestroyObject</name>\n         <argumentList>\n            <argument>\n               <name>ObjectID</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_ObjectID</relatedStateVariable>\n            </argument>\n         </argumentList>\n      </action>\n      <action>\n         <name>DeleteResource</name>\n         <argumentList>\n            <argument>\n               <name>ResourceURI</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_URI</relatedStateVariable>\n            </argument>\n         </argumentList>\n      </action>\n      <action>\n         <name>UpdateObject</name>\n         <argumentList>\n            <argument>\n               <name>ObjectID</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_ObjectID</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>CurrentTagValue</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_TagValueList</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>NewTagValue</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_TagValueList</relatedStateVariable>\n            </argument>\n         </argumentList>\n      </action>\n      <action>\n         <name>Browse</name>\n         <argumentList>\n            <argument>\n               <name>ObjectID</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_ObjectID</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>BrowseFlag</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_BrowseFlag</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>Filter</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_Filter</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>StartingIndex</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_Index</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>RequestedCount</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_Count</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>SortCriteria</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_SortCriteria</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>Result</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_Result</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>NumberReturned</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_Count</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>TotalMatches</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_Count</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>UpdateID</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_UpdateID</relatedStateVariable>\n            </argument>\n         </argumentList>\n      </action>\n      <action>\n         <name>GetTransferProgress</name>\n         <argumentList>\n            <argument>\n               <name>TransferID</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_TransferID</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>TransferStatus</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_TransferStatus</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>TransferLength</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_TransferLength</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>TransferTotal</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_TransferTotal</relatedStateVariable>\n            </argument>\n         </argumentList>\n      </action>\n      <action>\n         <name>GetSearchCapabilities</name>\n         <argumentList>\n            <argument>\n               <name>SearchCaps</name>\n               <direction>out</direction>\n               <relatedStateVariable>SearchCapabilities</relatedStateVariable>\n            </argument>\n         </argumentList>\n      </action>\n      <action>\n         <name>CreateObject</name>\n         <argumentList>\n            <argument>\n               <name>ContainerID</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_ObjectID</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>Elements</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_Result</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>ObjectID</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_ObjectID</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>Result</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_Result</relatedStateVariable>\n            </argument>\n         </argumentList>\n      </action>\n      <action>\n         <name>Search</name>\n         <argumentList>\n            <argument>\n               <name>ContainerID</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_ObjectID</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>SearchCriteria</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_SearchCriteria</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>Filter</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_Filter</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>StartingIndex</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_Index</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>RequestedCount</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_Count</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>SortCriteria</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_SortCriteria</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>Result</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_Result</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>NumberReturned</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_Count</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>TotalMatches</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_Count</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>UpdateID</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_UpdateID</relatedStateVariable>\n            </argument>\n         </argumentList>\n      </action>\n      <action>\n         <name>GetSortCapabilities</name>\n         <argumentList>\n            <argument>\n               <name>SortCaps</name>\n               <direction>out</direction>\n               <relatedStateVariable>SortCapabilities</relatedStateVariable>\n            </argument>\n         </argumentList>\n      </action>\n      <action>\n         <name>ImportResource</name>\n         <argumentList>\n            <argument>\n               <name>SourceURI</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_URI</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>DestinationURI</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_URI</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>TransferID</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_TransferID</relatedStateVariable>\n            </argument>\n         </argumentList>\n      </action>\n      <action>\n         <name>CreateReference</name>\n         <argumentList>\n            <argument>\n               <name>ContainerID</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_ObjectID</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>ObjectID</name>\n               <direction>in</direction>\n               <relatedStateVariable>A_ARG_TYPE_ObjectID</relatedStateVariable>\n            </argument>\n            <argument>\n               <name>NewID</name>\n               <direction>out</direction>\n               <relatedStateVariable>A_ARG_TYPE_ObjectID</relatedStateVariable>\n            </argument>\n         </argumentList>\n      </action>\n      <action>\n         <name>GetSystemUpdateID</name>\n         <argumentList>\n            <argument>\n              <name>Id</name>\n               <direction>out</direction>\n               <relatedStateVariable>SystemUpdateID</relatedStateVariable>\n            </argument>\n         </argumentList>\n      </action>\n   </actionList>\n   <serviceStateTable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_SortCriteria</name>\n         <dataType>string</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_TransferLength</name>\n         <dataType>string</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"yes\">\n         <name>TransferIDs</name>\n         <dataType>string</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_UpdateID</name>\n         <dataType>ui4</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_SearchCriteria</name>\n         <dataType>string</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_Filter</name>\n         <dataType>string</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"yes\">\n         <name>ContainerUpdateIDs</name>\n         <dataType>string</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_Result</name>\n         <dataType>string</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_Index</name>\n         <dataType>ui4</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_TransferID</name>\n         <dataType>ui4</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_TagValueList</name>\n         <dataType>string</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_URI</name>\n         <dataType>uri</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_ObjectID</name>\n         <dataType>string</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>SortCapabilities</name>\n         <dataType>string</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>SearchCapabilities</name>\n         <dataType>string</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_Count</name>\n         <dataType>ui4</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_BrowseFlag</name>\n         <dataType>string</dataType>\n         <allowedValueList>\n            <allowedValue>BrowseMetadata</allowedValue>\n            <allowedValue>BrowseDirectChildren</allowedValue>\n         </allowedValueList>\n      </stateVariable>\n      <stateVariable sendEvents=\"yes\">\n         <name>SystemUpdateID</name>\n         <dataType>ui4</dataType>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_TransferStatus</name>\n         <dataType>string</dataType>\n         <allowedValueList>\n            <allowedValue>COMPLETED</allowedValue>\n            <allowedValue>ERROR</allowedValue>\n            <allowedValue>IN_PROGRESS</allowedValue>\n            <allowedValue>STOPPED</allowedValue>\n         </allowedValueList>\n      </stateVariable>\n      <stateVariable sendEvents=\"no\">\n         <name>A_ARG_TYPE_TransferTotal</name>\n         <dataType>string</dataType>\n      </stateVariable>\n   </serviceStateTable>\n</scpd>";
    public static final String SEARCH = "Search";
    public static final String SEARCHCAPABILITIES = "SearchCapabilities";
    public static final String SEARCHCAPS = "SearchCaps";
    public static final String SEARCHCRITERIA = "SearchCriteria";
    public static final String SERVICE_TYPE = "urn:schemas-upnp-org:service:ContentDirectory:1";
    public static final String SORTCAPABILITIES = "SortCapabilities";
    public static final String SORTCAPS = "SortCaps";
    public static final String SORTCRITERIA = "SortCriteria";
    public static final String SOURCEURI = "SourceURI";
    public static final String STARTINGINDEX = "StartingIndex";
    public static final String STOPPED = "STOPPED";
    public static final String STOPTRANSFERRESOURCE = "StopTransferResource";
    public static final String SYSTEMUPDATEID = "SystemUpdateID";
    public static final String TOTALMATCHES = "TotalMatches";
    public static final String TRANSFERID = "TransferID";
    public static final String TRANSFERIDS = "TransferIDs";
    public static final String TRANSFERLENGTH = "TransferLength";
    public static final String TRANSFERSTATUS = "TransferStatus";
    public static final String TRANSFERTOTAL = "TransferTotal";
    public static final String UPDATEID = "UpdateID";
    public static final String UPDATEOBJECT = "UpdateObject";
    private long contentUpdateInterval;
    private DirectoryList dirList = new DirectoryList();
    private FormatList formatList = new FormatList();
    private int maxContentID;
    private MediaServer mediaServer;
    private Mutex mutex = new Mutex();
    private RootNode rootNode;
    private SearchCapList searchCapList = new SearchCapList();
    private SortCapList sortCapList = new SortCapList();
    private int systemUpdateID;
    private long systemUpdateIDInterval;

    public ContentDirectory(MediaServer mserver) {
        setMediaServer(mserver);
        this.systemUpdateID = 0;
        this.maxContentID = 0;
        setSystemUpdateInterval(SPConstant.DELAY_BUFFER_DURATION);
        setContentUpdateInterval(60000);
        initRootNode();
        initSortCaps();
        initSearchCaps();
    }

    private void setMediaServer(MediaServer mserver) {
        this.mediaServer = mserver;
    }

    public MediaServer getMediaServer() {
        return this.mediaServer;
    }

    public void lock() {
        this.mutex.lock();
    }

    public void unlock() {
        this.mutex.unlock();
    }

    public synchronized void updateSystemUpdateID() {
        this.systemUpdateID++;
    }

    public synchronized int getSystemUpdateID() {
        return this.systemUpdateID;
    }

    private synchronized int getNextContentID() {
        this.maxContentID++;
        return this.maxContentID;
    }

    public int getNextItemID() {
        return getNextContentID();
    }

    public int getNextContainerID() {
        return getNextContentID();
    }

    private void initRootNode() {
        this.rootNode = new RootNode();
        this.rootNode.setContentDirectory(this);
    }

    public RootNode getRootNode() {
        return this.rootNode;
    }

    private ContainerNode createContainerNode() {
        return new ContainerNode();
    }

    public boolean addPlugIn(Format format) {
        this.formatList.add(format);
        return true;
    }

    public Format getFormat(File file) {
        return this.formatList.getFormat(file);
    }

    public Format getFormat(int n) {
        return this.formatList.getFormat(n);
    }

    public int getNFormats() {
        return this.formatList.size();
    }

    public boolean addSortCap(SortCap sortCap) {
        this.sortCapList.add(sortCap);
        return true;
    }

    public int getNSortCaps() {
        return this.sortCapList.size();
    }

    public SortCap getSortCap(int n) {
        return this.sortCapList.getSortCap(n);
    }

    public SortCap getSortCap(String type) {
        return this.sortCapList.getSortCap(type);
    }

    private void initSortCaps() {
        addSortCap(new UPnPClassSortCap());
        addSortCap(new DCTitleSortCap());
        addSortCap(new DCDateSortCap());
    }

    private String getSortCapabilities() {
        String sortCapsStr = "";
        int nSortCaps = getNSortCaps();
        for (int n = 0; n < nSortCaps; n++) {
            String type = getSortCap(n).getType();
            if (n > 0) {
                sortCapsStr = new StringBuilder(String.valueOf(sortCapsStr)).append(",").toString();
            }
            sortCapsStr = new StringBuilder(String.valueOf(sortCapsStr)).append(type).toString();
        }
        return sortCapsStr;
    }

    public boolean addSearchCap(SearchCap searchCap) {
        this.searchCapList.add(searchCap);
        return true;
    }

    public SearchCapList getSearchCapList() {
        return this.searchCapList;
    }

    public int getNSearchCaps() {
        return this.searchCapList.size();
    }

    public SearchCap getSearchCap(int n) {
        return this.searchCapList.getSearchCap(n);
    }

    public SearchCap getSearchCap(String type) {
        return this.searchCapList.getSearchCap(type);
    }

    private void initSearchCaps() {
        addSearchCap(new IdSearchCap());
        addSearchCap(new TitleSearchCap());
    }

    private String getSearchCapabilities() {
        String searchCapsStr = "";
        int nSearchCaps = getNSearchCaps();
        for (int n = 0; n < nSearchCaps; n++) {
            String type = getSearchCap(n).getPropertyName();
            if (n > 0) {
                searchCapsStr = new StringBuilder(String.valueOf(searchCapsStr)).append(",").toString();
            }
            searchCapsStr = new StringBuilder(String.valueOf(searchCapsStr)).append(type).toString();
        }
        return searchCapsStr;
    }

    private DirectoryList getDirectoryList() {
        return this.dirList;
    }

    public boolean addDirectory(Directory dir) {
        dir.setContentDirectory(this);
        dir.setID(getNextContainerID());
        dir.updateContentList();
        this.dirList.add(dir);
        this.rootNode.addContentNode(dir);
        updateSystemUpdateID();
        return true;
    }

    public boolean removeDirectory(String name) {
        Directory dirNode = this.dirList.getDirectory(name);
        if (dirNode == null) {
            return false;
        }
        this.dirList.remove(dirNode);
        this.rootNode.removeNode(dirNode);
        updateSystemUpdateID();
        return true;
    }

    public boolean removeAllDirectories() {
        this.dirList.removeAllElements();
        return true;
    }

    public int getNDirectories() {
        return this.dirList.size();
    }

    public Directory getDirectory(int n) {
        return this.dirList.getDirectory(n);
    }

    public ContentNode findContentNodeByID(String id) {
        return getRootNode().findContentNodeByID(id);
    }

    public boolean actionControlReceived(Action action) {
        String actionName = action.getName();
        if (actionName.equals(BROWSE)) {
            return browseActionReceived(new BrowseAction(action));
        }
        if (actionName.equals(SEARCH)) {
            return searchActionReceived(new SearchAction(action));
        }
        if (actionName.equals(GETSEARCHCAPABILITIES)) {
            action.getArgument(SEARCHCAPS).setValue(getSearchCapabilities());
            return true;
        } else if (actionName.equals(GETSORTCAPABILITIES)) {
            action.getArgument(SORTCAPS).setValue(getSortCapabilities());
            return true;
        } else if (!actionName.equals(GETSYSTEMUPDATEID)) {
            return false;
        } else {
            action.getArgument("Id").setValue(getSystemUpdateID());
            return true;
        }
    }

    private boolean browseActionReceived(BrowseAction action) {
        if (action.isMetadata()) {
            return browseMetadataActionReceived(action);
        }
        if (action.isDirectChildren()) {
            return browseDirectChildrenActionReceived(action);
        }
        return false;
    }

    private boolean browseMetadataActionReceived(BrowseAction action) {
        ContentNode node = findContentNodeByID(action.getObjectID());
        if (node == null) {
            return false;
        }
        DIDLLite didlLite = new DIDLLite();
        didlLite.setContentNode(node);
        action.setArgumentValue("Result", didlLite.toString());
        action.setArgumentValue("NumberReturned", 1);
        action.setArgumentValue("TotalMatches", 1);
        action.setArgumentValue("UpdateID", getSystemUpdateID());
        if (!Debug.isOn()) {
            return true;
        }
        action.print();
        return true;
    }

    private void sortContentNodeList(ContentNode[] conNode, SortCap sortCap, boolean ascSeq) {
        int nConNode = conNode.length;
        for (int i = 0; i < nConNode - 1; i++) {
            int selIdx = i;
            for (int j = i + 1; j < nConNode; j++) {
                int cmpRet = sortCap.compare(conNode[selIdx], conNode[j]);
                if (ascSeq && cmpRet < 0) {
                    selIdx = j;
                }
                if (!ascSeq && cmpRet > 0) {
                    selIdx = j;
                }
            }
            ContentNode conTmp = conNode[i];
            conNode[i] = conNode[selIdx];
            conNode[selIdx] = conTmp;
        }
    }

    private SortCriterionList getSortCriteriaArray(String sortCriteria) {
        SortCriterionList sortCriList = new SortCriterionList();
        StringTokenizer st = new StringTokenizer(sortCriteria, ", ");
        while (st.hasMoreTokens()) {
            sortCriList.add(st.nextToken());
        }
        return sortCriList;
    }

    private ContentNodeList sortContentNodeList(ContentNodeList contentNodeList, String sortCriteria) {
        if (sortCriteria == null || sortCriteria.length() <= 0) {
            return contentNodeList;
        }
        int n;
        int nChildNodes = contentNodeList.size();
        ContentNode[] conNode = new ContentNode[nChildNodes];
        for (n = 0; n < nChildNodes; n++) {
            conNode[n] = contentNodeList.getContentNode(n);
        }
        SortCriterionList sortCritList = getSortCriteriaArray(sortCriteria);
        int nSortCrit = sortCritList.size();
        for (n = 0; n < nSortCrit; n++) {
            String sortStr = sortCritList.getSortCriterion(n);
            Debug.message("[" + n + "] = " + sortStr);
            boolean ascSeq = true;
            char firstSortChar = sortStr.charAt(0);
            if (firstSortChar == '-') {
                ascSeq = false;
            }
            if (firstSortChar == '+' || firstSortChar == '-') {
                sortStr = sortStr.substring(1);
            }
            SortCap sortCap = getSortCap(sortStr);
            if (sortCap != null) {
                Debug.message("  ascSeq = " + ascSeq);
                Debug.message("  sortCap = " + sortCap.getType());
                sortContentNodeList(conNode, sortCap, ascSeq);
            }
        }
        ContentNodeList sortedContentNodeList = new ContentNodeList();
        for (n = 0; n < nChildNodes; n++) {
            sortedContentNodeList.add(conNode[n]);
        }
        return sortedContentNodeList;
    }

    private boolean browseDirectChildrenActionReceived(BrowseAction action) {
        String objID = action.getObjectID();
        ContentNode node = findContentNodeByID(objID);
        if (node == null || !node.isContainerNode()) {
            return false;
        }
        int n;
        ContainerNode containerNode = (ContainerNode) node;
        ContentNodeList contentNodeList = new ContentNodeList();
        int nChildNodes = containerNode.getNContentNodes();
        for (n = 0; n < nChildNodes; n++) {
            contentNodeList.add(containerNode.getContentNode(n));
        }
        ContentNodeList sortedContentNodeList = sortContentNodeList(contentNodeList, action.getSortCriteria());
        int startingIndex = action.getStartingIndex();
        if (startingIndex <= 0) {
            startingIndex = 0;
        }
        int requestedCount = action.getRequestedCount();
        if (requestedCount == 0) {
            requestedCount = nChildNodes;
        }
        DIDLLite didlLite = new DIDLLite();
        int numberReturned = 0;
        for (n = startingIndex; n < nChildNodes && numberReturned < requestedCount; n++) {
            ContentNode cnode = sortedContentNodeList.getContentNode(n);
            didlLite.addContentNode(cnode);
            cnode.setParentID(objID);
            numberReturned++;
        }
        action.setResult(didlLite.toString());
        action.setNumberReturned(numberReturned);
        action.setTotalMaches(nChildNodes);
        action.setUpdateID(getSystemUpdateID());
        return true;
    }

    private SearchCriteriaList getSearchCriteriaList(String searchStr) {
        SearchCriteriaList searchList = new SearchCriteriaList();
        if (searchStr != null && searchStr.compareTo("*") != 0) {
            StringTokenizer searchCriTokenizer = new StringTokenizer(searchStr, SearchCriteria.WCHARS);
            while (searchCriTokenizer.hasMoreTokens()) {
                String prop = searchCriTokenizer.nextToken();
                if (!searchCriTokenizer.hasMoreTokens()) {
                    break;
                }
                String binOp = searchCriTokenizer.nextToken();
                if (!searchCriTokenizer.hasMoreTokens()) {
                    break;
                }
                String value = StringUtil.trim(searchCriTokenizer.nextToken(), "\"");
                String logOp = "";
                if (searchCriTokenizer.hasMoreTokens()) {
                    logOp = searchCriTokenizer.nextToken();
                }
                SearchCriteria searchCri = new SearchCriteria();
                searchCri.setProperty(prop);
                searchCri.setOperation(binOp);
                searchCri.setValue(value);
                searchCri.setLogic(logOp);
                searchList.add(searchCri);
            }
        }
        return searchList;
    }

    private int getSearchContentList(ContainerNode node, SearchCriteriaList searchCriList, SearchCapList searchCapList, ContentNodeList contentNodeList) {
        if (searchCriList.compare(node, searchCapList)) {
            contentNodeList.add(node);
        }
        int nChildNodes = node.getNContentNodes();
        for (int n = 0; n < nChildNodes; n++) {
            ContentNode cnode = node.getContentNode(n);
            if (cnode.isContainerNode()) {
                getSearchContentList((ContainerNode) cnode, searchCriList, searchCapList, contentNodeList);
            }
        }
        return contentNodeList.size();
    }

    private boolean searchActionReceived(SearchAction action) {
        ContentNode node = findContentNodeByID(action.getContainerID());
        if (node == null || !node.isContainerNode()) {
            return false;
        }
        int n;
        ContainerNode containerNode = (ContainerNode) node;
        SearchCriteriaList searchCriList = getSearchCriteriaList(action.getSearchCriteria());
        SearchCapList searchCapList = getSearchCapList();
        ContentNodeList contentNodeList = new ContentNodeList();
        int nChildNodes = containerNode.getNContentNodes();
        for (n = 0; n < nChildNodes; n++) {
            ContentNode cnode = containerNode.getContentNode(n);
            if (cnode.isContainerNode()) {
                getSearchContentList((ContainerNode) cnode, searchCriList, searchCapList, contentNodeList);
            }
        }
        nChildNodes = contentNodeList.size();
        ContentNodeList sortedContentNodeList = sortContentNodeList(contentNodeList, action.getSortCriteria());
        int startingIndex = action.getStartingIndex();
        if (startingIndex <= 0) {
            startingIndex = 0;
        }
        int requestedCount = action.getRequestedCount();
        if (requestedCount == 0) {
            requestedCount = nChildNodes;
        }
        DIDLLite didlLite = new DIDLLite();
        int numberReturned = 0;
        for (n = startingIndex; n < nChildNodes && numberReturned < requestedCount; n++) {
            didlLite.addContentNode(sortedContentNodeList.getContentNode(n));
            numberReturned++;
        }
        action.setResult(didlLite.toString());
        action.setNumberReturned(numberReturned);
        action.setTotalMaches(nChildNodes);
        action.setUpdateID(getSystemUpdateID());
        return true;
    }

    public boolean queryControlReceived(StateVariable stateVar) {
        return false;
    }

    public void contentExportRequestRecieved(HTTPRequest httpReq) {
        if (httpReq.getURI().startsWith(CONTENT_EXPORT_URI)) {
            ParameterList paramList = httpReq.getParameterList();
            for (int n = 0; n < paramList.size(); n++) {
                Parameter param = paramList.getParameter(n);
                Debug.message("[" + param.getName() + "] = " + param.getValue());
            }
            ContentNode node = findContentNodeByID(paramList.getValue("id"));
            if (node == null) {
                httpReq.returnBadRequest();
                return;
            } else if (node instanceof ItemNode) {
                ItemNode itemNode = (ItemNode) node;
                long contentLen = itemNode.getContentLength();
                String contentType = itemNode.getMimeType();
                InputStream contentIn = itemNode.getContentInputStream();
                if (contentLen <= 0 || contentType.length() <= 0 || contentIn == null) {
                    httpReq.returnBadRequest();
                    return;
                }
                ConnectionManager conMan = getMediaServer().getConnectionManager();
                int conID = conMan.getNextConnectionID();
                ConnectionInfo conInfo = new ConnectionInfo(conID);
                conInfo.setProtocolInfo(contentType);
                conInfo.setDirection("Output");
                conInfo.setStatus("OK");
                conMan.addConnectionInfo(conInfo);
                HTTPResponse httpRes = new HTTPResponse();
                httpRes.setContentType(contentType);
                httpRes.setStatusCode(200);
                httpRes.setContentLength(contentLen);
                httpRes.setContentInputStream(contentIn);
                httpReq.post(httpRes);
                try {
                    contentIn.close();
                } catch (Exception e) {
                }
                conMan.removeConnectionInfo(conID);
                return;
            } else {
                httpReq.returnBadRequest();
                return;
            }
        }
        httpReq.returnBadRequest();
    }

    public String getInterfaceAddress() {
        return getMediaServer().getInterfaceAddress();
    }

    public int getHTTPPort() {
        return getMediaServer().getHTTPPort();
    }

    public String getContentExportURL(String id) {
        return "http://" + getInterfaceAddress() + NetworkUtils.DELIMITER_COLON + getHTTPPort() + CONTENT_EXPORT_URI + "?" + "id" + SearchCriteria.EQ + id;
    }

    public String getContentImportURL(String id) {
        return "http://" + getInterfaceAddress() + NetworkUtils.DELIMITER_COLON + getHTTPPort() + CONTENT_IMPORT_URI + "?" + "id" + SearchCriteria.EQ + id;
    }

    public void setSystemUpdateInterval(long itime) {
        this.systemUpdateIDInterval = itime;
    }

    public long getSystemUpdateIDInterval() {
        return this.systemUpdateIDInterval;
    }

    public void setContentUpdateInterval(long itime) {
        this.contentUpdateInterval = itime;
    }

    public long getContentUpdateInterval() {
        return this.contentUpdateInterval;
    }

    public void run() {
        StateVariable varSystemUpdateID = getMediaServer().getStateVariable(SYSTEMUPDATEID);
        int lastSystemUpdateID = 0;
        long lastContentUpdateTime = System.currentTimeMillis();
        while (isRunnable()) {
            try {
                Thread.sleep(getSystemUpdateIDInterval());
            } catch (InterruptedException e) {
            }
            int currSystemUpdateID = getSystemUpdateID();
            if (lastSystemUpdateID != currSystemUpdateID) {
                varSystemUpdateID.setValue(currSystemUpdateID);
                lastSystemUpdateID = currSystemUpdateID;
            }
            long currTime = System.currentTimeMillis();
            if (getContentUpdateInterval() < currTime - lastContentUpdateTime) {
                getDirectoryList().update();
                lastContentUpdateTime = currTime;
            }
        }
    }
}
