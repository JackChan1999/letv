package org.cybergarage.upnp.std.av.server.action;

import org.cybergarage.upnp.Action;

public class BrowseAction extends Action {
    public static final String BROWSE_DIRECT_CHILDREN = "BrowseDirectChildren";
    public static final String BROWSE_FLAG = "BrowseFlag";
    public static final String BROWSE_METADATA = "BrowseMetadata";
    public static final String FILTER = "Filter";
    public static final String NUMBER_RETURNED = "NumberReturned";
    public static final String OBJECT_ID = "ObjectID";
    public static final String REQUESTED_COUNT = "RequestedCount";
    public static final String RESULT = "Result";
    public static final String SORT_CRITERIA = "SortCriteria";
    public static final String STARTING_INDEX = "StartingIndex";
    public static final String TOTAL_MACHES = "TotalMatches";
    public static final String UPDATE_ID = "UpdateID";

    public BrowseAction(Action action) {
        super(action);
    }

    public String getBrowseFlag() {
        return getArgumentValue("BrowseFlag");
    }

    public boolean isMetadata() {
        return "BrowseMetadata".equals(getBrowseFlag());
    }

    public boolean isDirectChildren() {
        return "BrowseDirectChildren".equals(getBrowseFlag());
    }

    public String getObjectID() {
        return getArgumentValue("ObjectID");
    }

    public int getStartingIndex() {
        return getArgumentIntegerValue("StartingIndex");
    }

    public int getRequestedCount() {
        return getArgumentIntegerValue("RequestedCount");
    }

    public int getNumberReturned() {
        return getArgumentIntegerValue("NumberReturned");
    }

    public int getTotalMatches() {
        return getArgumentIntegerValue("TotalMatches");
    }

    public String getSortCriteria() {
        return getArgumentValue("SortCriteria");
    }

    public String getFilter() {
        return getArgumentValue("Filter");
    }

    public void setResult(String value) {
        setArgumentValue("Result", value);
    }

    public void setNumberReturned(int value) {
        setArgumentValue("NumberReturned", value);
    }

    public void setTotalMaches(int value) {
        setArgumentValue("TotalMatches", value);
    }

    public void setUpdateID(int value) {
        setArgumentValue("UpdateID", value);
    }
}
