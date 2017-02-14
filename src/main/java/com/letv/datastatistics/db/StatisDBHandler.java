package com.letv.datastatistics.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.letv.datastatistics.bean.StatisCacheBean;
import java.util.ArrayList;

public class StatisDBHandler {
    public static synchronized boolean saveLocalCache(Context context, StatisCacheBean mStatisCacheBean) {
        boolean z = false;
        synchronized (StatisDBHandler.class) {
            if (mStatisCacheBean != null) {
                try {
                    if (hasByCacheId(context, mStatisCacheBean.getCacheId())) {
                        updateByCacheId(context, mStatisCacheBean);
                    } else {
                        ContentValues cv = new ContentValues();
                        cv.put("cacheId", mStatisCacheBean.getCacheId());
                        cv.put("cachedata", mStatisCacheBean.getCacheData());
                        cv.put("cachetime", Long.valueOf(mStatisCacheBean.getCacheTime()));
                        context.getContentResolver().insert(StatisContentProvider.URI_STATIS, cv);
                    }
                    z = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return z;
    }

    public static void updateByCacheId(Context context, StatisCacheBean mStatisCacheBean) {
        if (mStatisCacheBean != null) {
            try {
                ContentValues cv = new ContentValues();
                cv.put("cacheId", mStatisCacheBean.getCacheId());
                cv.put("cachedata", mStatisCacheBean.getCacheData());
                cv.put("cachetime", Long.valueOf(mStatisCacheBean.getCacheTime()));
                context.getContentResolver().update(StatisContentProvider.URI_STATIS, cv, "cacheId=?", new String[]{mStatisCacheBean.getCacheId()});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized StatisCacheBean getCacheByCacheId(Context context, String cacheId) {
        StatisCacheBean mLocalCacheBean;
        synchronized (StatisDBHandler.class) {
            mLocalCacheBean = null;
            try {
                Cursor cursor = context.getContentResolver().query(StatisContentProvider.URI_STATIS, null, "cacheId= ?", new String[]{cacheId}, null);
                if (cursor != null && cursor.moveToNext()) {
                    mLocalCacheBean = new StatisCacheBean(cursor.getString(cursor.getColumnIndex("cacheId")), cursor.getString(cursor.getColumnIndex("cachedata")), cursor.getLong(cursor.getColumnIndex("cachetime")));
                }
                closeCursor(cursor);
            } catch (Exception e) {
                e.printStackTrace();
                closeCursor(null);
            } catch (Throwable th) {
                closeCursor(null);
            }
        }
        return mLocalCacheBean;
    }

    public static ArrayList<StatisCacheBean> getAllCacheTrace(Context context) {
        Exception e;
        Throwable th;
        Cursor cursor = null;
        ArrayList<StatisCacheBean> list = null;
        try {
            cursor = context.getContentResolver().query(StatisContentProvider.URI_STATIS, null, null, null, "cachetime desc");
            ArrayList<StatisCacheBean> list2 = new ArrayList();
            while (cursor != null) {
                try {
                    if (!cursor.moveToNext()) {
                        break;
                    }
                    list2.add(new StatisCacheBean(cursor.getString(cursor.getColumnIndex("cacheId")), cursor.getString(cursor.getColumnIndex("cachedata")), cursor.getLong(cursor.getColumnIndex("cachetime"))));
                } catch (Exception e2) {
                    e = e2;
                    list = list2;
                } catch (Throwable th2) {
                    th = th2;
                    list = list2;
                }
            }
            closeCursor(cursor);
            return list2;
        } catch (Exception e3) {
            e = e3;
            try {
                e.printStackTrace();
                closeCursor(cursor);
                return list;
            } catch (Throwable th3) {
                th = th3;
                closeCursor(cursor);
                throw th;
            }
        }
    }

    public static synchronized void clearAll(Context context) {
        synchronized (StatisDBHandler.class) {
            context.getContentResolver().delete(StatisContentProvider.URI_STATIS, null, null);
        }
    }

    public static synchronized void deleteByCacheId(Context context, String cacheId) {
        synchronized (StatisDBHandler.class) {
            context.getContentResolver().delete(StatisContentProvider.URI_STATIS, "cacheId= ?", new String[]{cacheId});
        }
    }

    public static synchronized boolean hasByCacheId(Context context, String cacheId) {
        boolean result;
        synchronized (StatisDBHandler.class) {
            result = false;
            try {
                Cursor cursor = context.getContentResolver().query(StatisContentProvider.URI_STATIS, new String[]{"cacheId"}, "cacheId= ?", new String[]{cacheId}, null);
                if (cursor != null) {
                    result = cursor.getCount() > 0;
                }
                closeCursor(cursor);
            } catch (Exception e) {
                e.printStackTrace();
                closeCursor(null);
            } catch (Throwable th) {
                closeCursor(null);
            }
        }
        return result;
    }

    public static void closeCursor(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }
}
