package com.letv.business.flow.album.model;

import com.letv.core.bean.DDUrlsResultBean;
import com.letv.core.utils.BaseTypeUtils;
import java.util.List;

public class AlbumStreamSupporter {
    public boolean has1080p;
    public boolean has720p;
    public boolean hasHd;
    public boolean hasLow;
    public boolean hasStandard;
    public boolean hasSuperHd;

    public void reset() {
        this.has1080p = false;
        this.has720p = false;
        this.hasSuperHd = false;
        this.hasHd = false;
        this.hasStandard = false;
        this.hasLow = false;
    }

    public void resetHW() {
    }

    public void init(DDUrlsResultBean result) {
        this.has1080p = result.has1080p;
        this.has720p = result.has720p;
        this.hasSuperHd = result.hasSuperHigh;
        this.hasHd = result.hasHigh;
        this.hasStandard = result.hasStandard;
        this.hasLow = result.hasLow;
    }

    public int getStreamCount(List<Integer> levelList) {
        levelList.clear();
        if (this.has1080p) {
            levelList.add(Integer.valueOf(5));
        }
        if (this.has720p) {
            levelList.add(Integer.valueOf(4));
        }
        if (this.hasSuperHd) {
            levelList.add(Integer.valueOf(3));
        }
        if (this.hasHd) {
            levelList.add(Integer.valueOf(2));
        }
        if (this.hasStandard) {
            levelList.add(Integer.valueOf(1));
        }
        if (this.hasLow) {
            levelList.add(Integer.valueOf(0));
        }
        if (BaseTypeUtils.isListEmpty(levelList)) {
            return 0;
        }
        return levelList.size();
    }
}
