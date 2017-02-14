package com.letv.core.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChannelListBean implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    private Map<String, Channel> channelMap = new HashMap();
    public List<Channel> listChannel = new ArrayList();
    public int lockSize;

    public static class Channel implements LetvBaseBean, Comparable {
        public static final int CHANNEL_TYPE_ALBUMS = 1;
        public static final int CHANNEL_TYPE_RECOMM = 3;
        public static final int CHANNEL_TYPE_VIDEOS = 2;
        public static final int FROM_CHANNEL_PAGE_ITEM = 2;
        public static final int FROM_HOME_PAGE_MORE = 1;
        public static final int FROM_MY_PAGE_MORE = 3;
        private static final long serialVersionUID = 1;
        private int fromWhere = 2;
        public String htmlUrl;
        public String icon;
        public String iconSelected;
        public int id;
        public int index;
        public int lock;
        public ArrayList<SiftKVP> mSiftKvps;
        public String name;
        public String pageid;
        public String subtitle;
        public int top;
        public int type;

        public Channel(int id, String name, String icon, String iconSelected, int type, String subtitle, String htmlUrl, String pageid) {
            this.id = id;
            this.name = name;
            this.icon = icon;
            this.iconSelected = iconSelected;
            this.type = type;
            this.subtitle = subtitle;
            this.htmlUrl = htmlUrl;
            this.pageid = pageid;
        }

        public String toString() {
            return "Channel{fromWhere=" + this.fromWhere + ", id=" + this.id + ", name='" + this.name + '\'' + ", icon='" + this.icon + '\'' + ", type=" + this.type + ", subtitle='" + this.subtitle + '\'' + ", htmlUrl='" + this.htmlUrl + '\'' + ", mSiftKvps=" + this.mSiftKvps + ", pageid='" + this.pageid + '\'' + '}';
        }

        public int compareTo(Object another) {
            if (this.lock - ((Channel) another).lock != 0) {
                return ((Channel) another).lock - this.lock;
            }
            if (this.top - ((Channel) another).top != 0) {
                return this.top - ((Channel) another).top;
            }
            return this.index - ((Channel) another).index;
        }
    }

    public Map<String, Channel> getChannelMap() {
        return this.channelMap;
    }
}
