package com.letv.core.bean;

import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.utils.LetvUtils;
import java.util.ArrayList;
import java.util.Iterator;

public class FindDataBean extends FindBaseBean implements LetvBaseBean {
    public static final String FIND_SPREAD_AREA = "3";
    public static final String FIND_SPREAD_AREA_APP = "4";
    public String area;
    public ArrayList<FindDataAreaBean> data;
    private ArrayList<FindDataAreaBean> filterData;
    public String mtime;
    public String name;

    public ArrayList<FindDataAreaBean> getData() {
        filter();
        return this.filterData;
    }

    private void filter() {
        if (this.filterData == null) {
            this.filterData = new ArrayList();
            Iterator it = this.data.iterator();
            while (it.hasNext()) {
                FindDataAreaBean bean = (FindDataAreaBean) it.next();
                if (isNeedShow(bean)) {
                    this.filterData.add(bean);
                }
            }
        }
    }

    private boolean isNeedShow(FindDataAreaBean bean) {
        try {
            int type = Integer.parseInt(this.area);
            if (LetvUtils.isSpecialChannel() && DialogMsgConstantId.TWO_ZERO_TWO_CONSTANT.equals(bean.type)) {
                type = -1;
            }
            if (!(!LetvUtils.isGooglePlay() || LetvUtils.isInHongKong() || "5".equals(bean.type) || "101".equals(bean.type))) {
                type = -1;
            }
            boolean needShow;
            switch (type) {
                case 1:
                    if ("1".equals(bean.type) || ("5".equals(bean.type) && !LetvUtils.isInHongKong())) {
                        needShow = true;
                    } else {
                        needShow = false;
                    }
                    return needShow;
                case 2:
                    if (!"101".equals(bean.type) || LetvUtils.isInHongKong()) {
                        needShow = false;
                    } else {
                        needShow = true;
                    }
                    return needShow;
                case 3:
                    if (("201".equals(bean.type) || DialogMsgConstantId.TWO_ZERO_TWO_CONSTANT.equals(bean.type)) && !LetvUtils.isInHongKong()) {
                        needShow = true;
                    } else {
                        needShow = false;
                    }
                    return needShow;
                case 4:
                    return true;
                default:
                    return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void setData(ArrayList<FindDataAreaBean> findDataAreaOne) {
        this.data = findDataAreaOne;
    }

    public String getTimeStampKey() {
        return this.name;
    }

    public String getTimeStamp() {
        return this.mtime;
    }
}
