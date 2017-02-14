package com.letv.core.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.MediaAssetApi;
import com.letv.core.bean.AlbumInfo;
import com.letv.core.bean.CodeBean;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.FavouriteBean;
import com.letv.core.bean.FavouriteBeanList;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyDiskCache;
import com.letv.core.parser.FavouriteListParser;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.LogInfo;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.HashSet;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FavoriteTraceHandler {
    private Context context;

    public FavoriteTraceHandler(Context context) {
        this.context = context;
    }

    public void migrationOldData() {
        new Handler(Looper.getMainLooper()).post(new 1(this));
    }

    public boolean saveFavoriteTrace(FavouriteBean mFavBean) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("timestamp", Long.valueOf(mFavBean.timeStamp / 1000));
            contentValues.put("id", Long.valueOf(mFavBean.vid));
            contentValues.put("aid", Long.valueOf(mFavBean.pid));
            contentValues.put("cid", Integer.valueOf(mFavBean.channelId));
            contentValues.put("title", mFavBean.nameCn);
            contentValues.put("subTitle", mFavBean.subTitle);
            contentValues.put(SettingsJsonConstants.APP_ICON_KEY, mFavBean.pic);
            contentValues.put("count", Float.valueOf(mFavBean.nowEpisodes));
            contentValues.put("episode", Float.valueOf(mFavBean.episode));
            contentValues.put("type", Integer.valueOf(mFavBean.type));
            contentValues.put("isEnd", Integer.valueOf(mFavBean.isEnd));
            this.context.getContentResolver().insert(LetvContentProvider.URI_FAVORITETRACE, contentValues);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveFavoriteTrace(AlbumInfo albumInfo, long timeStamp) {
        if (albumInfo == null) {
            LogInfo.log("songhang", "saveFavoriteTrace albumInfo == null");
            return false;
        }
        try {
            if (hasFavoriteTrace(albumInfo)) {
                LogInfo.log("Emerson", "------- 里面有 id 更新数据 id = " + albumInfo.pid);
                return updateById(albumInfo, timeStamp);
            }
            LogInfo.log("Emerson", "------- 添加　数据 id = " + albumInfo.pid);
            ContentValues contentValues = new ContentValues();
            contentValues.put("timestamp", Long.valueOf(timeStamp));
            contentValues.put("id", Long.valueOf(albumInfo.vid));
            contentValues.put("aid", Long.valueOf(albumInfo.pid));
            contentValues.put("title", albumInfo.nameCn);
            contentValues.put("subTitle", albumInfo.subTitle);
            contentValues.put(SettingsJsonConstants.APP_ICON_KEY, albumInfo.pic320_200);
            contentValues.put("count", albumInfo.nowEpisodes);
            contentValues.put("episode", albumInfo.episode);
            contentValues.put("type", Integer.valueOf(albumInfo.type));
            contentValues.put("isEnd", Integer.valueOf(albumInfo.isEnd));
            contentValues.put("cid", Integer.valueOf(albumInfo.cid));
            contentValues.put("actor", albumInfo.starring);
            this.context.getContentResolver().insert(LetvContentProvider.URI_FAVORITETRACE, contentValues);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean updateById(AlbumInfo albumInfo, long timeStamp) {
        if (albumInfo == null) {
            return false;
        }
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("timestamp", Long.valueOf(timeStamp));
            contentValues.put("id", Long.valueOf(albumInfo.vid));
            contentValues.put("aid", Long.valueOf(albumInfo.pid));
            contentValues.put("title", albumInfo.nameCn);
            contentValues.put("subTitle", albumInfo.subTitle);
            contentValues.put("count", albumInfo.nowEpisodes);
            contentValues.put("episode", albumInfo.episode);
            contentValues.put("type", Integer.valueOf(albumInfo.type));
            contentValues.put("isEnd", Integer.valueOf(albumInfo.isEnd));
            contentValues.put("actor", albumInfo.starring);
            remove(albumInfo);
            this.context.getContentResolver().insert(LetvContentProvider.URI_FAVORITETRACE, contentValues);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public FavouriteBeanList getAllFavoriteTrace() {
        Exception e;
        Throwable th;
        Cursor cursor = null;
        FavouriteBeanList list = null;
        try {
            cursor = this.context.getContentResolver().query(LetvContentProvider.URI_FAVORITETRACE, null, null, null, "timestamp desc");
            FavouriteBeanList list2 = new FavouriteBeanList();
            int count = 0;
            while (cursor.moveToNext()) {
                try {
                    FavouriteBean mFavouriteBean = new FavouriteBean();
                    mFavouriteBean.timeStamp = cursor.getLong(cursor.getColumnIndexOrThrow("timestamp"));
                    mFavouriteBean.id = (long) cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    mFavouriteBean.vid = (long) cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    mFavouriteBean.pid = (long) cursor.getInt(cursor.getColumnIndexOrThrow("aid"));
                    mFavouriteBean.channelId = cursor.getInt(cursor.getColumnIndexOrThrow("cid"));
                    mFavouriteBean.nowEpisodes = (float) cursor.getInt(cursor.getColumnIndexOrThrow("count"));
                    mFavouriteBean.episode = (float) cursor.getInt(cursor.getColumnIndexOrThrow("episode"));
                    mFavouriteBean.isEnd = cursor.getInt(cursor.getColumnIndexOrThrow("isEnd"));
                    mFavouriteBean.nameCn = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                    mFavouriteBean.pic = cursor.getString(cursor.getColumnIndexOrThrow(SettingsJsonConstants.APP_ICON_KEY));
                    mFavouriteBean.subTitle = cursor.getString(cursor.getColumnIndexOrThrow("subTitle"));
                    mFavouriteBean.type = cursor.getInt(cursor.getColumnIndexOrThrow("type"));
                    String stars = cursor.getString(cursor.getColumnIndexOrThrow("actor"));
                    if (!TextUtils.isEmpty(stars)) {
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.put(stars);
                        mFavouriteBean.stars = jsonArray;
                    }
                    list2.add(mFavouriteBean);
                    count++;
                } catch (Exception e2) {
                    e = e2;
                    list = list2;
                } catch (Throwable th2) {
                    th = th2;
                    list = list2;
                }
            }
            list2.max = count;
            LetvTools.closeCursor(cursor);
            return list2;
        } catch (Exception e3) {
            e = e3;
            try {
                e.toString();
                LetvTools.closeCursor(cursor);
                return list;
            } catch (Throwable th3) {
                th = th3;
                LetvTools.closeCursor(cursor);
                throw th;
            }
        }
    }

    public String getAllFavoriteJsonString() {
        FavouriteBeanList dbFavList = getAllFavoriteTrace();
        if (dbFavList != null && dbFavList.size() >= 0) {
            JSONArray jsonArray = new JSONArray();
            Iterator it = dbFavList.iterator();
            while (it.hasNext()) {
                FavouriteBean favouriteBean = (FavouriteBean) it.next();
                long pid = favouriteBean.pid;
                long vid = favouriteBean.vid;
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("play_id", pid);
                    jsonObject.put("video_id", vid);
                    jsonObject.put("favorite_type", "1");
                    jsonObject.put("create_time", favouriteBean.timeStamp);
                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (jsonArray.length() > 0) {
                return jsonArray.toString();
            }
        }
        return null;
    }

    public void clearAll() {
        this.context.getContentResolver().delete(LetvContentProvider.URI_FAVORITETRACE, null, null);
    }

    public boolean remove(long pid, long vid) {
        try {
            this.context.getContentResolver().delete(LetvContentProvider.URI_FAVORITETRACE, "id= ? AND aid= ?", new String[]{vid + "", pid + ""});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean remove(long pid) {
        try {
            this.context.getContentResolver().delete(LetvContentProvider.URI_FAVORITETRACE, "aid= ?", new String[]{pid + ""});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void remove(HashSet<FavouriteBean> set) {
        Iterator<FavouriteBean> i = set.iterator();
        while (i.hasNext()) {
            FavouriteBean favouriteBean = (FavouriteBean) i.next();
            remove(favouriteBean.pid, favouriteBean.vid);
        }
    }

    public void remove(AlbumInfo albumInfo) {
        if (albumInfo != null) {
            if (albumInfo.pid <= 0 || !albumInfo.isMovieOrTvOrCartoon()) {
                remove(albumInfo.pid, albumInfo.vid);
            } else {
                remove(albumInfo.pid);
            }
        }
    }

    public boolean hasFavoriteTrace(AlbumInfo albumInfo) {
        Cursor cursor = null;
        try {
            boolean z;
            long vid = albumInfo.vid;
            long pid = albumInfo.pid;
            boolean isPidFlag = albumInfo.isMovieOrTvOrCartoon();
            if (pid == 0 || !isPidFlag) {
                cursor = this.context.getContentResolver().query(LetvContentProvider.URI_FAVORITETRACE, null, "id=?", new String[]{vid + ""}, null);
                z = cursor.getCount() > 0;
                LetvTools.closeCursor(cursor);
            } else {
                cursor = this.context.getContentResolver().query(LetvContentProvider.URI_FAVORITETRACE, null, "aid=?", new String[]{pid + ""}, null);
                z = cursor.getCount() > 0;
                LetvTools.closeCursor(cursor);
            }
            return z;
        } catch (Throwable th) {
            LetvTools.closeCursor(cursor);
        }
    }

    public VolleyRequest requestPostFavourite(SimpleResponse<CodeBean> simpleResponse) {
        String jsonData = getAllFavoriteJsonString();
        if (!TextUtils.isEmpty(jsonData)) {
            return new LetvRequest(CodeBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(MediaAssetApi.getInstance().getFavouriteDumpUrl()).addPostParams(MediaAssetApi.getInstance().getFavouriteDumpParams(jsonData)).setCallback(simpleResponse).add();
        }
        if (simpleResponse == null) {
            return null;
        }
        simpleResponse.onNetworkResponse(null, null, new DataHull(), NetworkResponseState.UNKONW);
        return null;
    }

    public void requestPostFavouriteThenDeleteDbBean() {
        requestPostFavourite(new 2(this));
    }

    public VolleyRequest requestGetFavouriteList(int page, int pageSize, SimpleResponse<FavouriteBeanList> simpleResponse) {
        VolleyRequest request = new LetvRequest(FavouriteBeanList.class).setUrl(MediaAssetApi.getInstance().getFavouriteListUrl(page, pageSize)).setCache(new VolleyDiskCache("favourite_bean_list_cache")).setParser(new FavouriteListParser()).setCallback(simpleResponse);
        if (page <= 1) {
            request.setRequestType(RequestManner.CACHE_THEN_NETROWK);
            request.setAlwaysCallbackNetworkResponse(true);
        } else {
            request.setRequestType(RequestManner.NETWORK_ONLY);
        }
        request.add();
        return request;
    }

    public void requestGetMultideleteFavourite(String fIds, SimpleResponse<CodeBean> simpleResponse) {
        new LetvRequest(CodeBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(MediaAssetApi.getInstance().getMultideleteFavouritetUrl(fIds)).setCallback(simpleResponse).add();
    }

    public void requestGetDeleteFavourite(long pid, long vid, long favoriteType, SimpleResponse<CodeBean> simpleResponse) {
        new LetvRequest(CodeBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(MediaAssetApi.getInstance().getDeleteFavouriteUrl(String.valueOf(pid), String.valueOf(vid), String.valueOf(favoriteType))).setCallback(simpleResponse).add();
    }

    public void requestGetAddFavourite(long pid, long vid, int favoriteType, int fromType, SimpleResponse<CodeBean> simpleResponse) {
        new LetvRequest(CodeBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(MediaAssetApi.getInstance().getAddFavouriteUrl(String.valueOf(pid), String.valueOf(vid), String.valueOf(favoriteType), String.valueOf(fromType))).setCallback(simpleResponse).add();
    }

    public void requestGetIsFavourite(long pid, long vid, int favoriteType, SimpleResponse<CodeBean> simpleResponse) {
        new LetvRequest(CodeBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(MediaAssetApi.getInstance().getIsFavouriteUrl(String.valueOf(pid), String.valueOf(vid), String.valueOf(favoriteType))).setCallback(simpleResponse).add();
    }
}
