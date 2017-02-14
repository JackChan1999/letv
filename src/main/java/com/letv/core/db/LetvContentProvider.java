package com.letv.core.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.letv.core.constant.DatabaseConstant.ChannelHisListTrace;
import com.letv.core.constant.DatabaseConstant.ChannelListTrace;
import com.letv.core.constant.DatabaseConstant.DialogMsgTrace;
import com.letv.core.constant.DatabaseConstant.DownloadTrace;
import com.letv.core.constant.DatabaseConstant.Emoji;
import com.letv.core.constant.DatabaseConstant.FavoriteRecord;
import com.letv.core.constant.DatabaseConstant.FestivalImageTrace;
import com.letv.core.constant.DatabaseConstant.HotTopTrace;
import com.letv.core.constant.DatabaseConstant.LanguageSettingsTrace;
import com.letv.core.constant.DatabaseConstant.LiveBookTrace;
import com.letv.core.constant.DatabaseConstant.LocalCacheTrace;
import com.letv.core.constant.DatabaseConstant.LocalVideoTrace;
import com.letv.core.constant.DatabaseConstant.PlayRecord;
import com.letv.core.constant.DatabaseConstant.PlayRecordWatched;
import com.letv.core.constant.DatabaseConstant.SearchTrace;
import com.letv.core.constant.DatabaseConstant.TopChannelsTrace;
import com.letv.core.constant.DatabaseConstant.WorldCupTrace;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LetvContentProvider extends ContentProvider {
    public static String AUTHORITY = null;
    private static final int CHANNELHISLIST_TRACE = 112;
    private static final int CHANNELLIST_TRACE = 111;
    private static final int DIALOGMSG_TRACE = 107;
    private static final int DOWNLOAD_TRACE = 103;
    private static final int EMOJI = 115;
    private static final int FAVORITE_TRACE = 101;
    private static final int FESTIVALIMAGE_TRACE = 104;
    private static final int HOT_TOP = 114;
    private static final int LANGUAGE_SETTINGS = 116;
    private static final int LIVEBOOK_TRACE = 106;
    private static final int LOCALCACHE_TRACE = 108;
    private static final int LOCALVIDEO_TRACE = 105;
    private static final int PLAY_TRACE = 102;
    private static final int PLAY_TRACE_WATCHED = 113;
    private static final int SEARCH_TRACE = 100;
    private static final int TOPCHANNELS_TRACE = 109;
    public static final Uri URI_CHANNELHISLISTTRACE = Uri.parse("content://" + AUTHORITY + "/" + ChannelHisListTrace.TABLE_NAME);
    public static final Uri URI_CHANNELLISTTRACE = Uri.parse("content://" + AUTHORITY + "/" + ChannelListTrace.TABLE_NAME);
    public static final Uri URI_DIALOGMSGTRACE = Uri.parse("content://" + AUTHORITY + "/" + DialogMsgTrace.TABLE_NAME);
    public static final Uri URI_DOWNLOADTRACE = Uri.parse("content://" + AUTHORITY + "/" + DownloadTrace.TABLE_NAME);
    public static final Uri URI_EMOJITRACE = Uri.parse("content://" + AUTHORITY + "/" + Emoji.TABLE_NAME);
    public static final Uri URI_FAVORITETRACE = Uri.parse("content://" + AUTHORITY + "/" + FavoriteRecord.TABLE_NAME);
    public static final Uri URI_FESTIVALIMAGE = Uri.parse("content://" + AUTHORITY + "/" + FestivalImageTrace.TABLE_NAME);
    public static final Uri URI_HOTTOPTRACE = Uri.parse("content://" + AUTHORITY + "/" + HotTopTrace.TABLE_NAME);
    public static final Uri URI_LANGUAGE_SETTINGS_TRACE = Uri.parse("content://" + AUTHORITY + "/" + LanguageSettingsTrace.TABLE_NAME);
    public static final Uri URI_LIVEBOOKTRACE = Uri.parse("content://" + AUTHORITY + "/" + LiveBookTrace.TABLE_NAME);
    public static final Uri URI_LOCALCACHETRACE = Uri.parse("content://" + AUTHORITY + "/" + LocalCacheTrace.TABLE_NAME);
    public static final Uri URI_LOCALVIDEOTRACE = Uri.parse("content://" + AUTHORITY + "/" + LocalVideoTrace.TABLE_NAME);
    private static final UriMatcher URI_MATCHER = new UriMatcher(-1);
    public static final Uri URI_PLAYTRACE = Uri.parse("content://" + AUTHORITY + "/" + PlayRecord.TABLE_NAME);
    public static final Uri URI_PLAYTRACE_WATCHED = Uri.parse("content://" + AUTHORITY + "/" + PlayRecordWatched.TABLE_NAME);
    public static final Uri URI_SEARCHTRACE = Uri.parse("content://" + AUTHORITY + "/" + SearchTrace.TABLE_NAME);
    public static final Uri URI_TOPCHANNELSTRACE = Uri.parse("content://" + AUTHORITY + "/" + TopChannelsTrace.TABLE_NAME);
    public static final Uri URI_WORLDCUPTRACE = Uri.parse("content://" + AUTHORITY + "/" + WorldCupTrace.TABLE_NAME);
    private static final int WORLDCUP_TRACE = 110;
    private SQLiteDataBase sqliteDataBase;

    static {
        InputStream in = null;
        try {
            Properties properties = new Properties();
            in = LetvContentProvider.class.getClassLoader().getResourceAsStream("letv.properties");
            if (in != null) {
                properties.load(in);
                AUTHORITY = properties.getProperty("LetvContentProvider.authorities");
            }
            if (AUTHORITY == null) {
                AUTHORITY = "com.letv.android.client.dao.LetvContentProvider";
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e22) {
                    e22.printStackTrace();
                }
            }
        } catch (Throwable th) {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e222) {
                    e222.printStackTrace();
                }
            }
        }
        URI_MATCHER.addURI(AUTHORITY, SearchTrace.TABLE_NAME, 100);
        URI_MATCHER.addURI(AUTHORITY, FavoriteRecord.TABLE_NAME, 101);
        URI_MATCHER.addURI(AUTHORITY, PlayRecord.TABLE_NAME, 102);
        URI_MATCHER.addURI(AUTHORITY, DownloadTrace.TABLE_NAME, 103);
        URI_MATCHER.addURI(AUTHORITY, FestivalImageTrace.TABLE_NAME, 104);
        URI_MATCHER.addURI(AUTHORITY, LocalVideoTrace.TABLE_NAME, 105);
        URI_MATCHER.addURI(AUTHORITY, LiveBookTrace.TABLE_NAME, 106);
        URI_MATCHER.addURI(AUTHORITY, DialogMsgTrace.TABLE_NAME, 107);
        URI_MATCHER.addURI(AUTHORITY, LocalCacheTrace.TABLE_NAME, 108);
        URI_MATCHER.addURI(AUTHORITY, TopChannelsTrace.TABLE_NAME, 109);
        URI_MATCHER.addURI(AUTHORITY, WorldCupTrace.TABLE_NAME, 110);
        URI_MATCHER.addURI(AUTHORITY, ChannelListTrace.TABLE_NAME, 111);
        URI_MATCHER.addURI(AUTHORITY, ChannelHisListTrace.TABLE_NAME, 112);
        URI_MATCHER.addURI(AUTHORITY, PlayRecordWatched.TABLE_NAME, 113);
        URI_MATCHER.addURI(AUTHORITY, HotTopTrace.TABLE_NAME, 114);
        URI_MATCHER.addURI(AUTHORITY, Emoji.TABLE_NAME, 115);
        URI_MATCHER.addURI(AUTHORITY, LanguageSettingsTrace.TABLE_NAME, 116);
    }

    public boolean onCreate() {
        this.sqliteDataBase = new SQLiteDataBase(getContext());
        return true;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues values) {
        Uri newUri = null;
        int match = URI_MATCHER.match(uri);
        SQLiteDatabase db = this.sqliteDataBase.getWritableDatabase();
        long rowId;
        switch (match) {
            case 100:
                rowId = db.insert(SearchTrace.TABLE_NAME, "name", values);
                if (rowId > 0) {
                    newUri = ContentUris.withAppendedId(URI_SEARCHTRACE, rowId);
                    break;
                }
                break;
            case 101:
                rowId = db.insert(FavoriteRecord.TABLE_NAME, null, values);
                if (rowId > 0) {
                    newUri = ContentUris.withAppendedId(URI_FAVORITETRACE, rowId);
                    break;
                }
                break;
            case 102:
                rowId = db.insert(PlayRecord.TABLE_NAME, null, values);
                if (rowId > 0) {
                    newUri = ContentUris.withAppendedId(URI_PLAYTRACE, rowId);
                    break;
                }
                break;
            case 103:
                rowId = db.insert(DownloadTrace.TABLE_NAME, null, values);
                if (rowId > 0) {
                    newUri = ContentUris.withAppendedId(URI_DOWNLOADTRACE, rowId);
                    break;
                }
                break;
            case 104:
                rowId = db.insert(FestivalImageTrace.TABLE_NAME, null, values);
                if (rowId > 0) {
                    newUri = ContentUris.withAppendedId(URI_FAVORITETRACE, rowId);
                    break;
                }
                break;
            case 105:
                rowId = db.insert(LocalVideoTrace.TABLE_NAME, null, values);
                if (rowId > 0) {
                    newUri = ContentUris.withAppendedId(URI_LOCALVIDEOTRACE, rowId);
                    break;
                }
                break;
            case 106:
                rowId = db.insert(LiveBookTrace.TABLE_NAME, null, values);
                if (rowId > 0) {
                    newUri = ContentUris.withAppendedId(URI_LIVEBOOKTRACE, rowId);
                    break;
                }
                break;
            case 107:
                rowId = db.insert(DialogMsgTrace.TABLE_NAME, null, values);
                if (rowId > 0) {
                    newUri = ContentUris.withAppendedId(URI_DIALOGMSGTRACE, rowId);
                    break;
                }
                break;
            case 108:
                rowId = db.insert(LocalCacheTrace.TABLE_NAME, null, values);
                if (rowId > 0) {
                    newUri = ContentUris.withAppendedId(URI_LOCALCACHETRACE, rowId);
                    break;
                }
                break;
            case 109:
                rowId = db.insert(TopChannelsTrace.TABLE_NAME, null, values);
                if (rowId > 0) {
                    newUri = ContentUris.withAppendedId(URI_LOCALCACHETRACE, rowId);
                    break;
                }
                break;
            case 110:
                rowId = db.insert(WorldCupTrace.TABLE_NAME, null, values);
                if (rowId > 0) {
                    newUri = ContentUris.withAppendedId(URI_WORLDCUPTRACE, rowId);
                    break;
                }
                break;
            case 111:
                rowId = db.insert(ChannelListTrace.TABLE_NAME, null, values);
                if (rowId > 0) {
                    newUri = ContentUris.withAppendedId(URI_CHANNELLISTTRACE, rowId);
                    break;
                }
                break;
            case 112:
                rowId = db.insert(ChannelHisListTrace.TABLE_NAME, null, values);
                if (rowId > 0) {
                    newUri = ContentUris.withAppendedId(URI_CHANNELHISLISTTRACE, rowId);
                    break;
                }
                break;
            case 113:
                rowId = db.insert(PlayRecordWatched.TABLE_NAME, null, values);
                if (rowId > 0) {
                    newUri = ContentUris.withAppendedId(URI_PLAYTRACE_WATCHED, rowId);
                    break;
                }
                break;
            case 114:
                rowId = db.insert(HotTopTrace.TABLE_NAME, null, values);
                if (rowId > 0) {
                    newUri = ContentUris.withAppendedId(URI_HOTTOPTRACE, rowId);
                    break;
                }
                break;
            case 115:
                rowId = db.insert(Emoji.TABLE_NAME, null, values);
                if (rowId > 0) {
                    newUri = ContentUris.withAppendedId(URI_EMOJITRACE, rowId);
                    break;
                }
                break;
            case 116:
                rowId = db.insert(LanguageSettingsTrace.TABLE_NAME, null, values);
                if (rowId > 0) {
                    newUri = ContentUris.withAppendedId(URI_LANGUAGE_SETTINGS_TRACE, rowId);
                    break;
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown or unsupported URL: " + uri.toString());
        }
        if (newUri != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return newUri;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count;
        int match = URI_MATCHER.match(uri);
        SQLiteDatabase db = this.sqliteDataBase.getWritableDatabase();
        switch (match) {
            case 100:
                count = db.delete(SearchTrace.TABLE_NAME, selection, selectionArgs);
                break;
            case 101:
                count = db.delete(FavoriteRecord.TABLE_NAME, selection, selectionArgs);
                break;
            case 102:
                count = db.delete(PlayRecord.TABLE_NAME, selection, selectionArgs);
                break;
            case 103:
                count = db.delete(DownloadTrace.TABLE_NAME, selection, selectionArgs);
                break;
            case 104:
                count = db.delete(FestivalImageTrace.TABLE_NAME, selection, selectionArgs);
                break;
            case 105:
                count = db.delete(LocalVideoTrace.TABLE_NAME, selection, selectionArgs);
                break;
            case 106:
                count = db.delete(LiveBookTrace.TABLE_NAME, selection, selectionArgs);
                break;
            case 107:
                count = db.delete(DialogMsgTrace.TABLE_NAME, selection, selectionArgs);
                break;
            case 108:
                count = db.delete(LocalCacheTrace.TABLE_NAME, selection, selectionArgs);
                break;
            case 109:
                count = db.delete(TopChannelsTrace.TABLE_NAME, selection, selectionArgs);
                break;
            case 110:
                count = db.delete(WorldCupTrace.TABLE_NAME, selection, selectionArgs);
                break;
            case 111:
                count = db.delete(ChannelListTrace.TABLE_NAME, selection, selectionArgs);
                break;
            case 112:
                count = db.delete(ChannelHisListTrace.TABLE_NAME, selection, selectionArgs);
                break;
            case 113:
                count = db.delete(PlayRecordWatched.TABLE_NAME, selection, selectionArgs);
                break;
            case 115:
                count = db.delete(Emoji.TABLE_NAME, selection, selectionArgs);
                break;
            case 116:
                count = db.delete(LanguageSettingsTrace.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown or unsupported URL: " + uri.toString());
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count;
        int match = URI_MATCHER.match(uri);
        SQLiteDatabase db = this.sqliteDataBase.getWritableDatabase();
        switch (match) {
            case 100:
                count = db.update(SearchTrace.TABLE_NAME, values, selection, selectionArgs);
                break;
            case 102:
                count = db.update(PlayRecord.TABLE_NAME, values, selection, selectionArgs);
                break;
            case 103:
                count = db.update(DownloadTrace.TABLE_NAME, values, selection, selectionArgs);
                break;
            case 104:
                count = db.update(FestivalImageTrace.TABLE_NAME, values, selection, selectionArgs);
                break;
            case 105:
                count = db.update(LocalVideoTrace.TABLE_NAME, values, selection, selectionArgs);
                break;
            case 106:
                count = db.update(LiveBookTrace.TABLE_NAME, values, selection, selectionArgs);
                break;
            case 107:
                count = db.update(DialogMsgTrace.TABLE_NAME, values, selection, selectionArgs);
                break;
            case 108:
                count = db.update(LocalCacheTrace.TABLE_NAME, values, selection, selectionArgs);
                break;
            case 109:
                db.update(TopChannelsTrace.TABLE_NAME, values, selection, selectionArgs);
                break;
            case 110:
                break;
            case 111:
                count = db.update(ChannelListTrace.TABLE_NAME, values, selection, selectionArgs);
                break;
            case 112:
                count = db.update(ChannelHisListTrace.TABLE_NAME, values, selection, selectionArgs);
                break;
            case 113:
                count = db.update(PlayRecordWatched.TABLE_NAME, values, selection, selectionArgs);
                break;
            case 115:
                count = db.update(Emoji.TABLE_NAME, values, selection, selectionArgs);
                break;
            case 116:
                count = db.update(LanguageSettingsTrace.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown or unsupported URL: " + uri.toString());
        }
        count = db.update(WorldCupTrace.TABLE_NAME, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match = URI_MATCHER.match(uri);
        SQLiteDatabase db = this.sqliteDataBase.getWritableDatabase();
        String targetTable = "";
        switch (match) {
            case 100:
                targetTable = SearchTrace.TABLE_NAME;
                break;
            case 101:
                targetTable = FavoriteRecord.TABLE_NAME;
                break;
            case 102:
                targetTable = PlayRecord.TABLE_NAME;
                break;
            case 103:
                targetTable = DownloadTrace.TABLE_NAME;
                break;
            case 104:
                targetTable = FestivalImageTrace.TABLE_NAME;
                break;
            case 105:
                targetTable = LocalVideoTrace.TABLE_NAME;
                break;
            case 106:
                targetTable = LiveBookTrace.TABLE_NAME;
                break;
            case 107:
                targetTable = DialogMsgTrace.TABLE_NAME;
                break;
            case 108:
                targetTable = LocalCacheTrace.TABLE_NAME;
                break;
            case 109:
                targetTable = TopChannelsTrace.TABLE_NAME;
                break;
            case 110:
                targetTable = WorldCupTrace.TABLE_NAME;
                break;
            case 111:
                targetTable = ChannelListTrace.TABLE_NAME;
                break;
            case 112:
                targetTable = ChannelHisListTrace.TABLE_NAME;
                break;
            case 113:
                targetTable = PlayRecordWatched.TABLE_NAME;
                break;
            case 114:
                targetTable = HotTopTrace.TABLE_NAME;
                break;
            case 115:
                targetTable = Emoji.TABLE_NAME;
                break;
            case 116:
                targetTable = LanguageSettingsTrace.TABLE_NAME;
                break;
            default:
                throw new UnsupportedOperationException("缺少URL matcher，请添加..." + uri.toString());
        }
        Cursor cursor = db.query(targetTable, null, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }
}
