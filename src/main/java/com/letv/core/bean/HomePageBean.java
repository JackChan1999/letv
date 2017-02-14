package com.letv.core.bean;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.ArrayList;
import java.util.List;

public class HomePageBean implements LetvBaseBean {
    public static boolean sHasLetvShop = false;
    private static final long serialVersionUID = 1;
    public HomeBlock adInfo;
    @JSONField(name = "block")
    public List<HomeBlock> block = new ArrayList();
    @JSONField(name = "bootings")
    public List<Booting> bootings = new ArrayList();
    @JSONField(name = "focus")
    public List<HomeMetaData> focus = new ArrayList();
    public int mADPosition = -1;
    public int mLockSize;
    public HomeBlock mServiceBlock;
    public HomeBlock recommend;
    public ArrayList<String> searchWords;

    public static class Booting {
        public String name;
        public int order;
        public String pic;
        public String pushpic_endtime;
        public String pushpic_starttime;
    }
}
