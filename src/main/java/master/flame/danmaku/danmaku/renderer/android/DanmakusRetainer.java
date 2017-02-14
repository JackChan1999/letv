package master.flame.danmaku.danmaku.renderer.android;

import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.IDanmakuIterator;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.util.DanmakuUtils;

public class DanmakusRetainer {
    private IDanmakusRetainer fbdrInstance = null;
    private IDanmakusRetainer ftdrInstance = null;
    private IDanmakusRetainer lrdrInstance = null;
    private IDanmakusRetainer rldrInstance = null;

    public interface Verifier {
        boolean skipLayout(BaseDanmaku baseDanmaku, float f, int i, boolean z);
    }

    public interface IDanmakusRetainer {
        void clear();

        void fix(BaseDanmaku baseDanmaku, IDisplayer iDisplayer, Verifier verifier);
    }

    private static class RLDanmakusRetainer implements IDanmakusRetainer {
        protected boolean mCancelFixingFlag;
        protected Danmakus mVisibleDanmakus;

        private RLDanmakusRetainer() {
            this.mVisibleDanmakus = new Danmakus(1);
            this.mCancelFixingFlag = false;
        }

        public void fix(BaseDanmaku drawItem, IDisplayer disp, Verifier verifier) {
            if (!drawItem.isOutside()) {
                float topPos = 0.0f;
                int lines = 0;
                boolean willHit = (drawItem.isShown() || this.mVisibleDanmakus.isEmpty()) ? false : true;
                boolean isOutOfVertialEdge = false;
                boolean shown = drawItem.isShown();
                if (!shown) {
                    boolean overwriteInsert;
                    this.mCancelFixingFlag = false;
                    IDanmakuIterator it = this.mVisibleDanmakus.iterator();
                    BaseDanmaku insertItem = null;
                    BaseDanmaku firstItem = null;
                    BaseDanmaku lastItem = null;
                    BaseDanmaku minRightRow = null;
                    while (!this.mCancelFixingFlag && it.hasNext()) {
                        lines++;
                        BaseDanmaku item = it.next();
                        if (item == drawItem) {
                            insertItem = item;
                            lastItem = null;
                            shown = true;
                            willHit = false;
                            overwriteInsert = false;
                            break;
                        }
                        if (firstItem == null) {
                            firstItem = item;
                        }
                        if (drawItem.paintHeight + item.getTop() > ((float) disp.getHeight())) {
                            overwriteInsert = true;
                            break;
                        }
                        if (minRightRow == null) {
                            minRightRow = item;
                        } else if (minRightRow.getRight() >= item.getRight()) {
                            minRightRow = item;
                        }
                        willHit = DanmakuUtils.willHitInDuration(disp, item, drawItem, drawItem.getDuration(), drawItem.getTimer().currMillisecond);
                        if (!willHit) {
                            insertItem = item;
                            overwriteInsert = false;
                            break;
                        }
                        lastItem = item;
                    }
                    overwriteInsert = false;
                    boolean checkEdge = true;
                    if (insertItem != null) {
                        if (lastItem != null) {
                            topPos = lastItem.getBottom();
                        } else {
                            topPos = insertItem.getTop();
                        }
                        if (insertItem != drawItem) {
                            this.mVisibleDanmakus.removeItem(insertItem);
                            shown = false;
                        }
                    } else if (overwriteInsert && minRightRow != null) {
                        topPos = minRightRow.getTop();
                        checkEdge = false;
                        shown = false;
                    } else if (lastItem != null) {
                        topPos = lastItem.getBottom();
                        willHit = false;
                    } else if (firstItem != null) {
                        topPos = firstItem.getTop();
                        this.mVisibleDanmakus.removeItem(firstItem);
                        shown = false;
                    } else {
                        topPos = 0.0f;
                    }
                    if (checkEdge) {
                        isOutOfVertialEdge = isOutVerticalEdge(overwriteInsert, drawItem, disp, topPos, firstItem, lastItem);
                    }
                    if (isOutOfVertialEdge) {
                        topPos = 0.0f;
                        willHit = true;
                    }
                    if (topPos == 0.0f) {
                        shown = false;
                    }
                }
                if (verifier == null || !verifier.skipLayout(drawItem, topPos, lines, willHit)) {
                    if (isOutOfVertialEdge) {
                        clear();
                    }
                    drawItem.layout(disp, drawItem.getLeft(), topPos);
                    if (!shown) {
                        this.mVisibleDanmakus.addItem(drawItem);
                    }
                }
            }
        }

        protected boolean isOutVerticalEdge(boolean overwriteInsert, BaseDanmaku drawItem, IDisplayer disp, float topPos, BaseDanmaku firstItem, BaseDanmaku lastItem) {
            if (topPos < 0.0f || ((firstItem != null && firstItem.getTop() > 0.0f) || drawItem.paintHeight + topPos > ((float) disp.getHeight()))) {
                return true;
            }
            return false;
        }

        public void clear() {
            this.mCancelFixingFlag = true;
            this.mVisibleDanmakus.clear();
        }
    }

    private static class FTDanmakusRetainer extends RLDanmakusRetainer {
        private FTDanmakusRetainer() {
            super();
        }

        protected boolean isOutVerticalEdge(boolean overwriteInsert, BaseDanmaku drawItem, IDisplayer disp, float topPos, BaseDanmaku firstItem, BaseDanmaku lastItem) {
            if (drawItem.paintHeight + topPos > ((float) disp.getHeight())) {
                return true;
            }
            return false;
        }
    }

    private static class FBDanmakusRetainer extends FTDanmakusRetainer {
        protected Danmakus mVisibleDanmakus;

        private FBDanmakusRetainer() {
            super();
            this.mVisibleDanmakus = new Danmakus(2);
        }

        public void fix(BaseDanmaku drawItem, IDisplayer disp, Verifier verifier) {
            if (!drawItem.isOutside()) {
                boolean shown = drawItem.isShown();
                float topPos = drawItem.getTop();
                int lines = 0;
                boolean willHit = (drawItem.isShown() || this.mVisibleDanmakus.isEmpty()) ? false : true;
                boolean isOutOfVerticalEdge = false;
                if (topPos < 0.0f) {
                    topPos = ((float) disp.getHeight()) - drawItem.paintHeight;
                }
                BaseDanmaku removeItem = null;
                BaseDanmaku firstItem = null;
                if (!shown) {
                    this.mCancelFixingFlag = false;
                    IDanmakuIterator it = this.mVisibleDanmakus.iterator();
                    while (!this.mCancelFixingFlag && it.hasNext()) {
                        lines++;
                        BaseDanmaku item = it.next();
                        if (item == drawItem) {
                            removeItem = null;
                            willHit = false;
                            break;
                        }
                        if (firstItem == null) {
                            firstItem = item;
                            if (firstItem.getBottom() != ((float) disp.getHeight())) {
                                break;
                            }
                        }
                        if (topPos < 0.0f) {
                            removeItem = null;
                            break;
                        }
                        willHit = DanmakuUtils.willHitInDuration(disp, item, drawItem, drawItem.getDuration(), drawItem.getTimer().currMillisecond);
                        if (!willHit) {
                            removeItem = item;
                            break;
                        }
                        topPos = item.getTop() - drawItem.paintHeight;
                    }
                    isOutOfVerticalEdge = isOutVerticalEdge(false, drawItem, disp, topPos, firstItem, null);
                    if (isOutOfVerticalEdge) {
                        topPos = ((float) disp.getHeight()) - drawItem.paintHeight;
                        willHit = true;
                    } else if (topPos >= 0.0f) {
                        willHit = false;
                    }
                }
                if (verifier == null || !verifier.skipLayout(drawItem, topPos, lines, willHit)) {
                    if (isOutOfVerticalEdge) {
                        clear();
                    }
                    drawItem.layout(disp, drawItem.getLeft(), topPos);
                    if (!shown) {
                        this.mVisibleDanmakus.removeItem(removeItem);
                        this.mVisibleDanmakus.addItem(drawItem);
                    }
                }
            }
        }

        protected boolean isOutVerticalEdge(boolean overwriteInsert, BaseDanmaku drawItem, IDisplayer disp, float topPos, BaseDanmaku firstItem, BaseDanmaku lastItem) {
            if (topPos < 0.0f || (firstItem != null && firstItem.getBottom() != ((float) disp.getHeight()))) {
                return true;
            }
            return false;
        }

        public void clear() {
            this.mCancelFixingFlag = true;
            this.mVisibleDanmakus.clear();
        }
    }

    public void fix(BaseDanmaku danmaku, IDisplayer disp, Verifier verifier) {
        switch (danmaku.getType()) {
            case 1:
                if (this.rldrInstance == null) {
                    this.rldrInstance = new RLDanmakusRetainer();
                }
                this.rldrInstance.fix(danmaku, disp, verifier);
                return;
            case 4:
                if (this.fbdrInstance == null) {
                    this.fbdrInstance = new FBDanmakusRetainer();
                }
                this.fbdrInstance.fix(danmaku, disp, verifier);
                return;
            case 5:
                if (this.ftdrInstance == null) {
                    this.ftdrInstance = new FTDanmakusRetainer();
                }
                this.ftdrInstance.fix(danmaku, disp, verifier);
                return;
            case 6:
                if (this.lrdrInstance == null) {
                    this.lrdrInstance = new RLDanmakusRetainer();
                }
                this.lrdrInstance.fix(danmaku, disp, verifier);
                return;
            case 7:
                danmaku.layout(disp, 0.0f, 0.0f);
                return;
            default:
                return;
        }
    }

    public void clear() {
        if (this.rldrInstance != null) {
            this.rldrInstance.clear();
        }
        if (this.lrdrInstance != null) {
            this.lrdrInstance.clear();
        }
        if (this.ftdrInstance != null) {
            this.ftdrInstance.clear();
        }
        if (this.fbdrInstance != null) {
            this.fbdrInstance.clear();
        }
    }

    public void release() {
        clear();
        this.rldrInstance = null;
        this.lrdrInstance = null;
        this.ftdrInstance = null;
        this.fbdrInstance = null;
    }
}
