package com.letv.core.bean;

import android.util.Log;
import com.letv.core.bean.channel.ChannelNavigation;
import com.letv.core.bean.channel.RedField;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.datastatistics.util.DataConstant.StaticticsVersion2Constatnt.StaticticsName;
import java.util.ArrayList;

public class HomeBlock implements LetvBaseBean, Comparable<HomeBlock> {
    private static final int HORIZONTAL_COUNT = 2;
    private static final long serialVersionUID = 1;
    public ArrayList<AlbumInfo> albumList;
    public String area;
    public ArrayList<HomeChildItemData> arrayHomeChildItem;
    public String blockname;
    public String bucket;
    public String cid;
    public String cms_num;
    public String contentStyle;
    public int contentType;
    public String date;
    public String fragId;
    public int index;
    public String isLock;
    public String isPage;
    public ArrayList<HomeMetaData> list;
    public int mGroupCardId = -1;
    public boolean mHasStatistics = false;
    public int num;
    public ArrayList<RedField> redField;
    public String redirectCid;
    public RedirectData redirectData;
    public String redirectPageId;
    public String redirectType;
    public String redirectUrl;
    public String redirectVideoType;
    public String reid;
    public ArrayList<HomeBlock> sub_block;
    public ArrayList<ChannelNavigation> tabsNavigation;
    public String type;
    public int videoNum;

    public void toHomeChildItemData() {
        if (this.list != null && this.list.size() > 0) {
            int count;
            this.arrayHomeChildItem = new ArrayList();
            if (this.list.size() % 2 == 0) {
                count = this.list.size();
            } else {
                count = this.list.size() + 1;
            }
            int i = 0;
            while (i < count) {
                HomeChildItemData homeChildItemData = new HomeChildItemData(this);
                if (this.list.size() > i) {
                    homeChildItemData.childItemData[0] = (HomeMetaData) this.list.get(i);
                    homeChildItemData.childItemData[0].index = i;
                    Log.v("toHomeChildItemData", ">>childItemData1 : " + homeChildItemData.childItemData[0]);
                }
                if (this.list.size() > i + 1) {
                    homeChildItemData.childItemData[1] = (HomeMetaData) this.list.get(i + 1);
                    homeChildItemData.childItemData[1].index = i + 1;
                    Log.v("toHomeChildItemData", ">>childItemData222 : " + homeChildItemData.childItemData[1]);
                }
                i += 2;
                if (homeChildItemData.childItemData[1] != null || homeChildItemData.childItemData[0] != null) {
                    this.arrayHomeChildItem.add(homeChildItemData);
                }
            }
        }
    }

    public int compareTo(HomeBlock another) {
        if (BaseTypeUtils.stoi(this.isLock) == BaseTypeUtils.stoi(another.isLock)) {
            return this.index - another.index;
        }
        return BaseTypeUtils.stoi(another.isLock) - BaseTypeUtils.stoi(this.isLock);
    }

    public String toString() {
        return "blockname=" + this.blockname + StaticticsName.STATICTICS_NAM_CID + this.cid + "area" + this.area + "bucket=" + this.bucket + "cms_num=" + this.cms_num + "redirectData=" + this.redirectData + "contentStyle=" + this.contentStyle + "list=" + this.list;
    }
}
