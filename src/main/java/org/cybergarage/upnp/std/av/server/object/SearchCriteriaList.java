package org.cybergarage.upnp.std.av.server.object;

import java.util.Vector;

public class SearchCriteriaList extends Vector {
    public SearchCriteria getSearchCriteria(int n) {
        return (SearchCriteria) get(n);
    }

    public SearchCriteria getSearchCriteria(String name) {
        if (name == null) {
            return null;
        }
        int nLists = size();
        for (int n = 0; n < nLists; n++) {
            SearchCriteria node = getSearchCriteria(n);
            if (name.compareTo(node.getProperty()) == 0) {
                return node;
            }
        }
        return null;
    }

    public boolean compare(ContentNode cnode, SearchCapList searchCapList) {
        int n;
        int searchCriCnt = size();
        for (n = 0; n < searchCriCnt; n++) {
            SearchCriteria searchCri = getSearchCriteria(n);
            String property = searchCri.getProperty();
            if (property == null) {
                searchCri.setResult(true);
            } else {
                SearchCap searchCap = searchCapList.getSearchCap(property);
                if (searchCap == null) {
                    searchCri.setResult(true);
                } else {
                    searchCri.setResult(searchCap.compare(searchCri, cnode));
                }
            }
        }
        SearchCriteriaList orSearchCriList = new SearchCriteriaList();
        for (n = 0; n < searchCriCnt; n++) {
            SearchCriteria currSearchCri = getSearchCriteria(n);
            if (n >= searchCriCnt - 1 || !currSearchCri.isLogicalAND()) {
                orSearchCriList.add(new SearchCriteria(currSearchCri));
            } else {
                SearchCriteria nextSearchCri = getSearchCriteria(n + 1);
                nextSearchCri.setResult((currSearchCri.getResult() & nextSearchCri.getResult()) != 0);
            }
        }
        int orSearchCriCnt = orSearchCriList.size();
        for (n = 0; n < orSearchCriCnt; n++) {
            if (getSearchCriteria(n).getResult()) {
                return true;
            }
        }
        return false;
    }
}
