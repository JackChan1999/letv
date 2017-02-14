package com.letv.core.bean;

import com.letv.core.utils.BaseTypeUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageCardListBean implements LetvBaseBean {
    public static final int DETAULT_CARD_ID = -1;
    private static final List<String> SAME_CARD_ID_LIST = new 1();
    private static final long serialVersionUID = 1;
    public int childTypeCount = 1;
    public int groupTypeCount = 1;
    public Map<String, PageCardBlock> map = new HashMap();
    public String pcversion;
    public String rootXML;
    public String supportVersion = "";

    public static boolean isLineSame(String cardId) {
        return SAME_CARD_ID_LIST.contains(cardId);
    }

    public PageCardRecyclerBean getDefaultRecyclerBean() {
        PageCardBlock cardBean = (PageCardBlock) this.map.get("card_-1");
        if (cardBean == null || BaseTypeUtils.isListEmpty(cardBean.childList)) {
            return null;
        }
        return (PageCardRecyclerBean) cardBean.childList.get(0);
    }
}
