package org.cybergarage.upnp.std.av.server.action;

import org.cybergarage.upnp.Action;

public class SearchAction extends Action {
    public static final String CONTAINER_ID = "ContainerID";
    public static final String FILTER = "Filter";
    public static final String NUMBER_RETURNED = "NumberReturned";
    public static final String REQUESTED_COUNT = "RequestedCount";
    public static final String RESULT = "Result";
    public static final String SEARCH_CRITERIA = "SearchCriteria";
    public static final String SORT_CRITERIA = "SortCriteria";
    public static final String STARTING_INDEX = "StartingIndex";
    public static final String TOTAL_MACHES = "TotalMatches";
    public static final String UPDATE_ID = "UpdateID";

    public SearchAction(Action action) {
        super(action);
    }

    public String getContainerID() {
        return getArgumentValue("ContainerID");
    }

    public String getSearchCriteria() {
        return getArgumentValue("SearchCriteria");
    }

    public int getStartingIndex() {
        return getArgumentIntegerValue("StartingIndex");
    }

    public int getRequestedCount() {
        return getArgumentIntegerValue("RequestedCount");
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
