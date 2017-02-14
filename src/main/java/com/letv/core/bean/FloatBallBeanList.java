package com.letv.core.bean;

import java.util.ArrayList;
import java.util.HashMap;

public class FloatBallBeanList implements LetvBaseBean {
    public static final String FLOAT_CHANNEL_CATETROY_SEVEN = "7";
    public static final String FLOAT_CHANNEL_FOUR = "4";
    public static final String FLOAT_HOME_ONE = "1";
    public static final String FLOAT_LIVE_CATETROY_EIGHT = "8";
    public static final String FLOAT_LIVE_FIVE = "5";
    public static final String FLOAT_MY_THREE = "3";
    public static final String FLOAT_SEACHER_TWO = "2";
    public static final String FLOAT_TOPIC_HALF_NINE = "9";
    public static final String FLOAT_TOPIC_SIX = "6";
    public static ArrayList<FloatBallBean> dataList = new ArrayList();
    public static HashMap<String, Boolean> mRecommendMap = new HashMap();
    private ArrayList<FloatBallBean> result = new ArrayList();

    public ArrayList<FloatBallBean> getResult() {
        return this.result;
    }

    public void setResult(ArrayList<FloatBallBean> result) {
        this.result = result;
        dataList = result;
    }
}
