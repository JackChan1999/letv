package master.flame.danmaku.danmaku.parser.android;

import com.letv.datastatistics.util.DataConstant.PAGE;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.util.DanmakuUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AcFunDanmakuParser extends BaseDanmakuParser {
    public Danmakus parse() {
        if (this.mDataSource == null || !(this.mDataSource instanceof JSONSource)) {
            return new Danmakus();
        }
        return doParse(this.mDataSource.data());
    }

    private Danmakus doParse(JSONArray danmakuListData) {
        Danmakus danmakus = new Danmakus();
        if (danmakuListData == null || danmakuListData.length() == 0) {
            return danmakus;
        }
        for (int i = 0; i < danmakuListData.length(); i++) {
            try {
                JSONObject danmakuArray = danmakuListData.getJSONObject(i);
                if (danmakuArray != null) {
                    danmakus = _parse(danmakuArray, danmakus);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return danmakus;
    }

    private Danmakus _parse(JSONObject jsonObject, Danmakus danmakus) {
        if (danmakus == null) {
            danmakus = new Danmakus();
        }
        if (!(jsonObject == null || jsonObject.length() == 0)) {
            for (int i = 0; i < jsonObject.length(); i++) {
                JSONObject obj = jsonObject;
                try {
                    String[] values = obj.getString("c").split(",");
                    if (values.length > 0) {
                        int type = Integer.parseInt(values[2]);
                        if (type != 7) {
                            long time = (long) (Float.parseFloat(values[0]) * 1000.0f);
                            int color = Integer.parseInt(values[1]) | -16777216;
                            float textSize = Float.parseFloat(values[3]);
                            BaseDanmaku item = this.mContext.mDanmakuFactory.createDanmaku(type, this.mContext);
                            if (item != null) {
                                int i2;
                                item.time = time;
                                item.textSize = (this.mDispDensity - 0.6f) * textSize;
                                item.textColor = color;
                                if (color <= -16777216) {
                                    i2 = -1;
                                } else {
                                    i2 = -16777216;
                                }
                                item.textShadowColor = i2;
                                DanmakuUtils.fillText(item, obj.optString(PAGE.MYLETV, "...."));
                                item.index = i;
                                item.setTimer(this.mTimer);
                                danmakus.addItem(item);
                            }
                        }
                    }
                } catch (JSONException e) {
                } catch (NumberFormatException e2) {
                }
            }
        }
        return danmakus;
    }
}
