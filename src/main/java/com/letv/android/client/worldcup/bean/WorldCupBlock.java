package com.letv.android.client.worldcup.bean;

import com.letv.core.bean.LetvBaseBean;
import java.util.ArrayList;

public class WorldCupBlock extends ArrayList<WorldCupMetaData> implements LetvBaseBean {
    private static final long serialVersionUID = 1;
    private int num;

    public int getNum() {
        return this.num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
