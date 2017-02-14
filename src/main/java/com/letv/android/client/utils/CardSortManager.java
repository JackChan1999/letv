package com.letv.android.client.utils;

import android.text.TextUtils;
import com.letv.core.bean.HomeBlock;
import com.letv.core.bean.HomePageBean;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CardSortManager {
    private static List<HomeBlock> sList;

    public CardSortManager() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public static String[] getFragList() {
        String fragId = "";
        fragId = PreferencesManager.getInstance().getCardSort();
        if (TextUtils.isEmpty(fragId)) {
            return null;
        }
        return fragId.split(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
    }

    private static void saveCradList(List<HomeBlock> list) {
        if (!BaseTypeUtils.isListEmpty(list)) {
            if (sList == null) {
                sList = new ArrayList();
            }
            sList.clear();
            for (HomeBlock block : list) {
                if (block == null) {
                    return;
                }
                if (!"113".equals(block.contentStyle)) {
                    sList.add(block);
                }
            }
        }
    }

    public static HomePageBean getHomePageBean(HomePageBean pageBean) {
        if (!(pageBean == null || BaseTypeUtils.isListEmpty(pageBean.block))) {
            List<HomeBlock> list = pageBean.block;
            if (sList == null) {
                sList = new ArrayList();
            }
            sList.clear();
            String[] fragIds = getFragList();
            if (BaseTypeUtils.isArrayEmpty(fragIds)) {
                saveCradList(list);
            } else {
                List<String> listFraId = Arrays.asList(fragIds);
                int addIndex = 1000;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) != null) {
                        if (TextUtils.equals(((HomeBlock) list.get(i)).isLock, "1")) {
                            ((HomeBlock) list.get(i)).index = i;
                        } else if (listFraId.indexOf(((HomeBlock) list.get(i)).fragId) != -1) {
                            ((HomeBlock) list.get(i)).index = listFraId.indexOf(((HomeBlock) list.get(i)).fragId);
                        } else {
                            ((HomeBlock) list.get(i)).index = addIndex;
                            addIndex++;
                        }
                    }
                }
                Collections.sort(list);
                saveCradList(list);
                pageBean.block = list;
            }
        }
        return pageBean;
    }

    public static List<HomeBlock> getHomeBlock() {
        return sList;
    }

    public static void updateList(List<HomeBlock> list) {
        sList = list;
    }

    public static void reset() {
        if (sList != null) {
            sList.clear();
        }
        sList = null;
    }
}
