package org.cybergarage.upnp.std.av.controller.server;

import org.cybergarage.upnp.Action;
import org.cybergarage.upnp.Argument;

public class BrowseAction {
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
    private Action action;

    public BrowseAction(Action action) {
        this.action = action;
    }

    public Argument getArgument(String name) {
        return this.action.getArgument(name);
    }

    public String getBrowseFlag() {
        return this.action.getArgumentValue("BrowseFlag");
    }

    public boolean isMetadata() {
        return "BrowseMetadata".equals(getBrowseFlag());
    }

    public boolean isDirectChildren() {
        return "BrowseDirectChildren".equals(getBrowseFlag());
    }

    public String getObjectID() {
        return this.action.getArgumentValue("ObjectID");
    }

    public int getStartingIndex() {
        return this.action.getArgumentIntegerValue("StartingIndex");
    }

    public int getRequestedCount() {
        return this.action.getArgumentIntegerValue("RequestedCount");
    }

    public String getSortCriteria() {
        return this.action.getArgumentValue("SortCriteria");
    }

    public String getFilter() {
        return this.action.getArgumentValue("Filter");
    }

    public void setBrowseFlag(String browseFlag) {
        this.action.setArgumentValue("BrowseFlag", browseFlag);
    }

    public void setObjectID(String objectID) {
        this.action.setArgumentValue("ObjectID", objectID);
    }

    public void setStartingIndex(int idx) {
        this.action.setArgumentValue("StartingIndex", idx);
    }

    public void setRequestedCount(int count) {
        this.action.setArgumentValue("RequestedCount", count);
    }

    public void setFilter(String filter) {
        this.action.setArgumentValue("Filter", filter);
    }

    public void setSortCriteria(String sortCaiteria) {
        this.action.setArgumentValue("SortCriteria", sortCaiteria);
    }

    public void setResult(String value) {
        this.action.setArgumentValue("Result", value);
    }

    public void setNumberReturned(int value) {
        this.action.setArgumentValue("NumberReturned", value);
    }

    public void setTotalMaches(int value) {
        this.action.setArgumentValue("TotalMatches", value);
    }

    public void setUpdateID(int value) {
        this.action.setArgumentValue("UpdateID", value);
    }

    public boolean postControlAction() {
        return this.action.postControlAction();
    }
}
