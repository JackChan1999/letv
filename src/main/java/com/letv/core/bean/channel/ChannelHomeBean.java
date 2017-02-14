package com.letv.core.bean.channel;

import com.alibaba.fastjson.annotation.JSONField;
import com.letv.core.bean.HomeBlock;
import com.letv.core.bean.HomeMetaData;
import com.letv.core.bean.LetvBaseBean;
import com.letv.core.bean.LiveRemenListBean.LiveRemenBaseBean;
import com.letv.core.utils.BaseTypeUtils;
import java.util.ArrayList;
import java.util.List;

public class ChannelHomeBean implements LetvBaseBean {
    public static boolean sHasLetvShop = false;
    private static final long serialVersionUID = 1;
    public ArrayList<HomeBlock> block;
    @JSONField(name = "bootings")
    public List<Booting> bootings = new ArrayList();
    public ArrayList<HomeMetaData> focus;
    public boolean isShowLiveBlock;
    public boolean isShowTextMark;
    public ChannelWorldCupInfoList mChannelWorldCupInfoList;
    public List<LiveRemenBaseBean> mLiveSportsList;
    public ArrayList<ChannelNavigation> navigation;
    public ArrayList<String> searchWords;
    public int tabIndex = -1;

    public void clear() {
        if (!BaseTypeUtils.isListEmpty(this.block)) {
            this.block.clear();
        }
        if (!BaseTypeUtils.isListEmpty(this.focus)) {
            this.focus.clear();
        }
        if (!BaseTypeUtils.isListEmpty(this.navigation)) {
            this.navigation.clear();
        }
    }
}
