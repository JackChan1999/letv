package com.letv.core.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;
import com.letv.core.constant.DatabaseConstant.ChannelHisListTrace;
import com.letv.core.constant.DatabaseConstant.ChannelListTrace;

public class SQLiteDataBase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "dbletv.db";
    public static final int DATABASE_VERSION = 68;

    public SQLiteDataBase(Context context) {
        super(context, DATABASE_NAME, null, 68);
    }

    public void onCreate(SQLiteDatabase db) {
        Log.d("HYX", "COME  INTO onCreate!!");
        createTable_searchTrace(db);
        createTable_favoriteTrace(db);
        createTable_playTrace(db);
        createTable_playTraceWatched(db);
        createTable_downloadTrace(db);
        createTable_festivalImageTrace(db);
        createTable_localVideoTrace(db);
        createTable_liveBookTrace(db);
        createTable_DialogMsgTrace(db);
        createTable_LocalCacheTrace(db);
        createTable_TopChannelsTrace(db);
        createTable_channelListSaveTrace(db);
        createTable_channelHisListTrace(db);
        createTable_worldCupTrace(db);
        createTable_HotTopTrace(db);
        createEmojiTable(db);
        createLanguageConfigTable(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("upgrade", "-----onUpgrade-----oldVersion:" + oldVersion + "; newVersion:" + newVersion);
        if (oldVersion == 58) {
            db.execSQL("ALTER TABLE playtable ADD COLUMN videotypekey TEXT ;");
            createTable_playTraceWatched(db);
        } else if (oldVersion == 57) {
            db.execSQL("ALTER TABLE playtable ADD COLUMN videotypekey TEXT ;");
            createTable_playTraceWatched(db);
        } else if (oldVersion == 56) {
            db.execSQL("ALTER TABLE playtable ADD COLUMN videotypekey TEXT ;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isWatch INTEGER DEFAULT 1;");
            createTable_channelListSaveTrace(db);
            createTable_channelHisListTrace(db);
            createTable_playTraceWatched(db);
        } else if (oldVersion == 55) {
            db.execSQL("ALTER TABLE playtable ADD COLUMN videotypekey TEXT ;");
            createTable_worldCupTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isWatch INTEGER DEFAULT 1;");
            createTable_channelListSaveTrace(db);
            createTable_channelHisListTrace(db);
            createTable_playTraceWatched(db);
        } else if (oldVersion == 54) {
            db.execSQL("ALTER TABLE playtable ADD COLUMN videotypekey TEXT ;");
            try {
                db.execSQL("drop table if exists TopChannelsTrace");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            createTable_TopChannelsTrace(db);
            createTable_worldCupTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isWatch INTEGER DEFAULT 1;");
            createTable_channelListSaveTrace(db);
            createTable_channelHisListTrace(db);
            createTable_playTraceWatched(db);
        } else if (oldVersion == 53) {
            db.execSQL("ALTER TABLE playtable ADD COLUMN videotypekey TEXT ;");
            try {
                db.execSQL("drop table if exists TopChannelsTrace");
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            createTable_TopChannelsTrace(db);
            createTable_worldCupTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isWatch INTEGER DEFAULT 1;");
            createTable_channelListSaveTrace(db);
            createTable_channelHisListTrace(db);
            createTable_playTraceWatched(db);
        } else if (oldVersion == 52) {
            db.execSQL("ALTER TABLE playtable ADD COLUMN videotypekey TEXT ;");
            try {
                db.execSQL("drop table if exists TopChannelsTrace");
            } catch (SQLException e22) {
                e22.printStackTrace();
            }
            createTable_TopChannelsTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN episodeicon TEXT;");
            createTable_worldCupTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isWatch INTEGER DEFAULT 1;");
            createTable_channelListSaveTrace(db);
            createTable_channelHisListTrace(db);
            createTable_playTraceWatched(db);
        } else if (oldVersion == 51) {
            db.execSQL("ALTER TABLE playtable ADD COLUMN videotypekey TEXT ;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN episodeicon TEXT;");
            db.execSQL("ALTER TABLE playtable ADD COLUMN img300 TEXT;");
            try {
                db.execSQL("drop table if exists TopChannelsTrace");
            } catch (SQLException e222) {
                e222.printStackTrace();
            }
            createTable_TopChannelsTrace(db);
            db.execSQL("ALTER TABLE LiveBookTrace ADD COLUMN launch_mode INTEGER DEFAULT 102;");
            createTable_worldCupTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isWatch INTEGER DEFAULT 1;");
            createTable_channelListSaveTrace(db);
            createTable_channelHisListTrace(db);
            createTable_playTraceWatched(db);
        } else if (oldVersion == 50) {
            db.execSQL("ALTER TABLE playtable ADD COLUMN videotypekey TEXT ;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN episodeicon TEXT;");
            db.execSQL("ALTER TABLE playtable ADD COLUMN img300 TEXT;");
            try {
                db.execSQL("drop table if exists TopChannelsTrace");
            } catch (SQLException e2222) {
                e2222.printStackTrace();
            }
            createTable_TopChannelsTrace(db);
            db.execSQL("ALTER TABLE LocalCacherace ADD COLUMN assistkey TEXT;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isNew INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN btime INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN etime INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE Favoritetable ADD COLUMN subTitle TEXT;");
            db.execSQL("ALTER TABLE Favoritetable ADD COLUMN isEnd INTEGER;");
            db.execSQL("ALTER TABLE Favoritetable ADD COLUMN episode INTEGER;");
            db.execSQL("drop table if exists festivalimagetrace");
            createTable_festivalImageTrace(db);
            db.execSQL("ALTER TABLE LiveBookTrace ADD COLUMN launch_mode INTEGER DEFAULT 102;");
            createTable_worldCupTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isWatch INTEGER DEFAULT 1;");
        } else if (oldVersion == 49) {
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN episodeicon TEXT;");
            try {
                db.execSQL("drop table if exists TopChannelsTrace");
            } catch (SQLException e22222) {
                e22222.printStackTrace();
            }
            createTable_TopChannelsTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isNew INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN btime INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN etime INTEGER DEFAULT 0;");
            db.execSQL("drop table if exists festivalimagetrace");
            createTable_festivalImageTrace(db);
            try {
                db.execSQL("drop table if exists playtable");
            } catch (SQLException e222222) {
                e222222.printStackTrace();
            }
            createTable_playTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isHd INTEGER;");
            db.execSQL("drop table if exists Favoritetable");
            createTable_favoriteTrace(db);
            db.execSQL("drop table if exists LocalCacherace");
            createTable_LocalCacheTrace(db);
            db.execSQL("ALTER TABLE LiveBookTrace ADD COLUMN launch_mode INTEGER DEFAULT 102;");
            createTable_worldCupTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isWatch INTEGER DEFAULT 1;");
        } else if (oldVersion == 48) {
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN episodeicon TEXT;");
            try {
                db.execSQL("drop table if exists TopChannelsTrace");
            } catch (SQLException e2222222) {
                e2222222.printStackTrace();
            }
            createTable_TopChannelsTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isNew INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN btime INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN etime INTEGER DEFAULT 0;");
            db.execSQL("drop table if exists festivalimagetrace");
            createTable_festivalImageTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isHd INTEGER;");
            db.execSQL("ALTER TABLE Favoritetable ADD COLUMN subTitle TEXT;");
            db.execSQL("ALTER TABLE Favoritetable ADD COLUMN isEnd INTEGER;");
            db.execSQL("ALTER TABLE Favoritetable ADD COLUMN episode INTEGER;");
            db.execSQL("drop table if exists playtable");
            createTable_playTrace(db);
            db.execSQL("drop table if exists DialogMsgTrace");
            createTable_DialogMsgTrace(db);
            db.execSQL("drop table if exists LocalCacherace");
            createTable_LocalCacheTrace(db);
            db.execSQL("ALTER TABLE LiveBookTrace ADD COLUMN launch_mode INTEGER DEFAULT 102;");
            createTable_worldCupTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isWatch INTEGER DEFAULT 1;");
        } else if (oldVersion == 47) {
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN episodeicon TEXT;");
            try {
                db.execSQL("drop table if exists TopChannelsTrace");
            } catch (SQLException e22222222) {
                e22222222.printStackTrace();
            }
            createTable_TopChannelsTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isNew INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN btime INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN etime INTEGER DEFAULT 0;");
            db.execSQL("drop table if exists festivalimagetrace");
            createTable_festivalImageTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isHd INTEGER;");
            db.execSQL("ALTER TABLE Favoritetable ADD COLUMN subTitle TEXT;");
            db.execSQL("ALTER TABLE Favoritetable ADD COLUMN isEnd INTEGER;");
            db.execSQL("ALTER TABLE Favoritetable ADD COLUMN episode INTEGER;");
            db.execSQL("drop table if exists LocalCacherace");
            createTable_LocalCacheTrace(db);
            db.execSQL("drop table if exists DialogMsgTrace");
            createTable_DialogMsgTrace(db);
            db.execSQL("drop table if exists playtable");
            createTable_playTrace(db);
            db.execSQL("ALTER TABLE LiveBookTrace ADD COLUMN launch_mode INTEGER DEFAULT 102;");
            createTable_worldCupTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isWatch INTEGER DEFAULT 1;");
        } else if (oldVersion == 38) {
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN episodeicon TEXT;");
            try {
                db.execSQL("drop table if exists TopChannelsTrace");
            } catch (SQLException e222222222) {
                e222222222.printStackTrace();
            }
            createTable_TopChannelsTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isNew INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN btime INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN etime INTEGER DEFAULT 0;");
            db.execSQL("drop table if exists festivalimagetrace");
            createTable_festivalImageTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isHd INTEGER;");
            db.execSQL("ALTER TABLE Favoritetable ADD COLUMN subTitle TEXT;");
            db.execSQL("ALTER TABLE Favoritetable ADD COLUMN isEnd INTEGER;");
            db.execSQL("ALTER TABLE Favoritetable ADD COLUMN episode INTEGER;");
            db.execSQL("drop table if exists LocalCacherace");
            createTable_LocalCacheTrace(db);
            db.execSQL("drop table if exists DialogMsgTrace");
            createTable_DialogMsgTrace(db);
            db.execSQL("drop table if exists playtable");
            createTable_playTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN file_path TEXT;");
            db.execSQL("ALTER TABLE LiveBookTrace ADD COLUMN launch_mode INTEGER DEFAULT 102;");
            createTable_worldCupTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isWatch INTEGER DEFAULT 1;");
        } else if (oldVersion == 37) {
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN episodeicon TEXT;");
            try {
                db.execSQL("drop table if exists TopChannelsTrace");
            } catch (SQLException e2222222222) {
                e2222222222.printStackTrace();
            }
            createTable_TopChannelsTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isNew INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN btime INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN etime INTEGER DEFAULT 0;");
            db.execSQL("drop table if exists festivalimagetrace");
            createTable_festivalImageTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isHd INTEGER;");
            db.execSQL("ALTER TABLE Favoritetable ADD COLUMN subTitle TEXT;");
            db.execSQL("ALTER TABLE Favoritetable ADD COLUMN isEnd INTEGER;");
            db.execSQL("ALTER TABLE Favoritetable ADD COLUMN episode INTEGER;");
            db.execSQL("drop table if exists LocalCacherace");
            createTable_LocalCacheTrace(db);
            db.execSQL("drop table if exists DialogMsgTrace");
            createTable_DialogMsgTrace(db);
            db.execSQL("drop table if exists playtable");
            createTable_playTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN file_path TEXT;");
            db.execSQL("ALTER TABLE Favoritetable ADD COLUMN isDolby INTEGER;");
            db.execSQL("ALTER TABLE LiveBookTrace ADD COLUMN launch_mode INTEGER DEFAULT 102;");
        } else if (oldVersion == 36) {
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN episodeicon TEXT;");
            try {
                db.execSQL("drop table if exists TopChannelsTrace");
            } catch (SQLException e22222222222) {
                e22222222222.printStackTrace();
            }
            createTable_TopChannelsTrace(db);
            db.execSQL("ALTER TABLE Favoritetable ADD COLUMN subTitle TEXT;");
            db.execSQL("ALTER TABLE Favoritetable ADD COLUMN isEnd INTEGER;");
            db.execSQL("ALTER TABLE Favoritetable ADD COLUMN episode INTEGER;");
            db.execSQL("drop table if exists festivalimagetrace");
            createTable_festivalImageTrace(db);
            db.execSQL("drop table if exists LocalCacherace");
            createTable_LocalCacheTrace(db);
            db.execSQL("drop table if exists DialogMsgTrace");
            createTable_DialogMsgTrace(db);
            createTable_liveBookTrace(db);
            db.execSQL("drop table if exists playtable");
            createTable_playTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isNew INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN btime INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN etime INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isHd INTEGER;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN file_path TEXT;");
            db.execSQL("ALTER TABLE Favoritetable ADD COLUMN isDolby INTEGER;");
            db.execSQL("ALTER TABLE Favoritetable ADD COLUMN aid INTEGER;");
            db.execSQL("ALTER TABLE LiveBookTrace ADD COLUMN launch_mode INTEGER DEFAULT 102;");
        } else if (oldVersion == 35) {
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN episodeicon TEXT;");
            try {
                db.execSQL("drop table if exists TopChannelsTrace");
            } catch (SQLException e222222222222) {
                e222222222222.printStackTrace();
            }
            createTable_TopChannelsTrace(db);
            db.execSQL("drop table if exists LocalCacherace");
            createTable_LocalCacheTrace(db);
            db.execSQL("drop table if exists DialogMsgTrace");
            createTable_DialogMsgTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isNew INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN btime INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN etime INTEGER DEFAULT 0;");
            db.execSQL("drop table if exists festivalimagetrace");
            createTable_festivalImageTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isHd INTEGER;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN file_path TEXT;");
            db.execSQL("drop table if exists playtable");
            createTable_playTrace(db);
            db.execSQL("drop table if exists Favoritetable");
            createTable_favoriteTrace(db);
            createTable_liveBookTrace(db);
            db.execSQL("ALTER TABLE LiveBookTrace ADD COLUMN launch_mode INTEGER DEFAULT 102;");
        } else if (oldVersion == 34) {
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN episodeicon TEXT;");
            try {
                db.execSQL("drop table if exists TopChannelsTrace");
            } catch (SQLException e2222222222222) {
                e2222222222222.printStackTrace();
            }
            createTable_TopChannelsTrace(db);
            db.execSQL("drop table if exists LocalCacherace");
            createTable_LocalCacheTrace(db);
            db.execSQL("drop table if exists DialogMsgTrace");
            createTable_DialogMsgTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isNew INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN btime INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN etime INTEGER DEFAULT 0;");
            db.execSQL("drop table if exists festivalimagetrace");
            createTable_festivalImageTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isHd INTEGER;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN file_path TEXT;");
            createTable_localVideoTrace(db);
            db.execSQL("drop table if exists playtable");
            createTable_playTrace(db);
            db.execSQL("drop table if exists Favoritetable");
            createTable_favoriteTrace(db);
            createTable_liveBookTrace(db);
            db.execSQL("ALTER TABLE LiveBookTrace ADD COLUMN launch_mode INTEGER DEFAULT 102;");
        } else if (oldVersion == 33) {
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN episodeicon TEXT;");
            try {
                db.execSQL("drop table if exists TopChannelsTrace");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            createTable_TopChannelsTrace(db);
            db.execSQL("drop table if exists LocalCacherace");
            createTable_LocalCacheTrace(db);
            db.execSQL("drop table if exists DialogMsgTrace");
            createTable_DialogMsgTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isNew INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN btime INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN etime INTEGER DEFAULT 0;");
            db.execSQL("drop table if exists festivalimagetrace");
            createTable_festivalImageTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isHd INTEGER;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN file_path TEXT;");
            try {
                db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN cid INTEGER;");
            } catch (Exception e3) {
            }
            createTable_localVideoTrace(db);
            db.execSQL("drop table if exists playtable");
            createTable_playTrace(db);
            db.execSQL("drop table if exists Favoritetable");
            createTable_favoriteTrace(db);
            createTable_liveBookTrace(db);
            db.execSQL("ALTER TABLE LiveBookTrace ADD COLUMN launch_mode INTEGER DEFAULT 102;");
        } else if (oldVersion == 32) {
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN episodeicon TEXT;");
            try {
                db.execSQL("drop table if exists TopChannelsTrace");
            } catch (SQLException e12) {
                e12.printStackTrace();
            }
            createTable_TopChannelsTrace(db);
            db.execSQL("drop table if exists LocalCacherace");
            createTable_LocalCacheTrace(db);
            db.execSQL("drop table if exists DialogMsgTrace");
            createTable_DialogMsgTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isNew INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN btime INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN etime INTEGER DEFAULT 0;");
            db.execSQL("drop table if exists festivalimagetrace");
            createTable_festivalImageTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isHd INTEGER;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN file_path TEXT;");
            try {
                db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN cid INTEGER;");
            } catch (Exception e4) {
            }
            createTable_localVideoTrace(db);
            db.execSQL("drop table if exists playtable");
            createTable_playTrace(db);
            db.execSQL("drop table if exists Favoritetable");
            createTable_favoriteTrace(db);
            createTable_liveBookTrace(db);
            db.execSQL("ALTER TABLE LiveBookTrace ADD COLUMN launch_mode INTEGER DEFAULT 102;");
        } else if (oldVersion == 31) {
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN episodeicon TEXT;");
            try {
                db.execSQL("drop table if exists TopChannelsTrace");
            } catch (SQLException e22222222222222) {
                e22222222222222.printStackTrace();
            }
            createTable_TopChannelsTrace(db);
            db.execSQL("drop table if exists LocalCacherace");
            createTable_LocalCacheTrace(db);
            db.execSQL("drop table if exists DialogMsgTrace");
            createTable_DialogMsgTrace(db);
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isNew INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN btime INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN etime INTEGER DEFAULT 0;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN isHd INTEGER;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN file_path TEXT;");
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN cid INTEGER;");
            db.execSQL("drop table if exists festivalimagetrace");
            createTable_festivalImageTrace(db);
            createTable_localVideoTrace(db);
            db.execSQL("drop table if exists playtable");
            createTable_playTrace(db);
            db.execSQL("drop table if exists Favoritetable");
            createTable_favoriteTrace(db);
            createTable_liveBookTrace(db);
            db.execSQL("ALTER TABLE LiveBookTrace ADD COLUMN launch_mode INTEGER DEFAULT 102;");
        } else if (oldVersion == 30) {
            db.execSQL("drop table if exists TopChannelsTrace");
            createTable_TopChannelsTrace(db);
            db.execSQL("drop table if exists LocalCacherace");
            createTable_LocalCacheTrace(db);
            db.execSQL("drop table if exists DialogMsgTrace");
            createTable_DialogMsgTrace(db);
            db.execSQL("drop table if exists festivalimagetrace");
            createTable_festivalImageTrace(db);
            db.execSQL("drop table if exists playtable");
            createTable_playTrace(db);
            db.execSQL("drop table if exists downlaodTrace");
            createTable_downloadTrace(db);
            createTable_localVideoTrace(db);
            db.execSQL("drop table if exists playtable");
            createTable_playTrace(db);
            db.execSQL("drop table if exists Favoritetable");
            createTable_favoriteTrace(db);
            createTable_liveBookTrace(db);
            db.execSQL("ALTER TABLE LiveBookTrace ADD COLUMN launch_mode INTEGER DEFAULT 102;");
        } else {
            createTable_searchTrace(db);
            createTable_favoriteTrace(db);
            createTable_playTrace(db);
            createTable_downloadTrace(db);
            createTable_festivalImageTrace(db);
            createTable_localVideoTrace(db);
            createTable_liveBookTrace(db);
            createTable_DialogMsgTrace(db);
            createTable_LocalCacheTrace(db);
            createTable_TopChannelsTrace(db);
        }
        if (oldVersion < 60 && oldVersion > 56) {
            alterChannelListTable(db);
            db.execSQL("ALTER TABLE channellisttable ADD COLUMN channel_type TEXT DEFAULT lunbo;");
            db.execSQL("ALTER TABLE channellisttable ADD COLUMN channelstatus TEXT DEFAULT normal;");
            Log.d("database_", "setTransactionSuccessful 1------------------- alterChannelListTable");
            alterChannelHisTable(db);
            db.execSQL("ALTER TABLE channelhislisttable ADD COLUMN channel_type TEXT DEFAULT lunbo;");
            db.execSQL("ALTER TABLE channelhislisttable ADD COLUMN channelstatus TEXT DEFAULT normal;");
            Log.d("database_", "setTransactionSuccessful 2------------------- alterChannelHisTable");
        }
        if (oldVersion <= 59) {
            db.execSQL("ALTER TABLE downlaodTrace ADD COLUMN duration INTEGER DEFAULT 1;");
            createTable_playTraceWatched(db);
        }
        if (oldVersion < 60) {
            db.execSQL("ALTER TABLE LiveBookTrace ADD COLUMN live_id TEXT;");
        }
        if (oldVersion < 65) {
            createEmojiTable(db);
            createTable_HotTopTrace(db);
            DBManager.getInstance().getFavoriteTrace().migrationOldData();
        }
        if (oldVersion < 66) {
            addColumn2Table(db, ChannelHisListTrace.TABLE_NAME, "signal");
            addColumn2Table(db, ChannelListTrace.TABLE_NAME, "signal");
        }
        if (oldVersion < 67) {
            addColumn2Table(db, ChannelHisListTrace.TABLE_NAME, "channelIcon");
            addColumn2Table(db, ChannelListTrace.TABLE_NAME, "channelIcon");
        }
        if (oldVersion < 68) {
            createLanguageConfigTable(db);
        }
    }

    private void addColumn2Table(SQLiteDatabase db, String tableName, String columnName) {
        if (!isColumnAlreadyExist(db, tableName, columnName)) {
            db.execSQL("ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " TEXT ;");
        }
    }

    private boolean isColumnAlreadyExist(SQLiteDatabase db, String tableName, String columnName) {
        boolean result = false;
        if (!(db == null || TextUtils.isEmpty(tableName) || TextUtils.isEmpty(columnName))) {
            Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " LIMIT 0", null);
            if (!(cursor == null || -1 == cursor.getColumnIndex("signal"))) {
                result = true;
            }
            closeCursor(cursor);
        }
        return result;
    }

    private void closeCursor(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    private void createTable_festivalImageTrace(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS festivalimagetrace(_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,pic TEXT,starttime TEXT,endtime TEXT,orderk INTEGER );");
    }

    private void createTable_channelListSaveTrace(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS channellisttable(_id INTEGER PRIMARY KEY AUTOINCREMENT,channelid TEXT,numericKeys TEXT,name TEXT,ename TEXT,signal TEXT,channelIcon TEXT,channel_type TEXT,hassave INTEGER,channelstatus TEXT DEFAULT normal);";
        System.out.println(sql);
        db.execSQL(sql);
    }

    private void createTable_channelHisListTrace(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS channelhislisttable(_id INTEGER PRIMARY KEY AUTOINCREMENT,channelid TEXT,numericKeys TEXT,name TEXT,ename TEXT,signal TEXT,channelIcon TEXT,channel_type TEXT,isRecord INTEGER,systemmillisecond INTEGER,channelstatus TEXT DEFAULT normal);");
    }

    private void createTable_searchTrace(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS searchtable(_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,timestamp INTEGER );");
    }

    private void createTable_playTrace(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS playtable(_id INTEGER PRIMARY KEY AUTOINCREMENT,cid INTEGER,img300 TEXT,pid INTEGER,vid INTEGER,nvid INTEGER,uid TEXT,vtype INTEGER,playtracefrom INTEGER,vtime INTEGER,htime INTEGER,utime INTEGER,state INTEGER,type INTEGER,videotypekey TEXT,title TEXT,img TEXT,nc TEXT );");
    }

    private void createTable_playTraceWatched(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS playtablewatched(vid INTEGER PRIMARY KEY,pid INTEGER);");
    }

    private void createTable_favoriteTrace(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Favoritetable(_id INTEGER PRIMARY KEY AUTOINCREMENT,timestamp INTEGER,id INTEGER,aid INTEGER,title TEXT,icon TEXT,score REAL,cid INTEGER,year TEXT,count INTEGER,timeLength INTEGER,director TEXT,actor TEXT,area TEXT,subcate TEXT,rcompany TEXT,type INTEGER,at INTEGER,albumtype TEXT,singleprice REAL,allowmonth INTEGER,paydate TEXT,needJump INTEGER,pay INTEGER,isDolby INTEGER,subTitle TEXT,isEnd INTEGER,episode INTEGER,ctime TEXT );");
    }

    private void createTable_downloadTrace(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS downlaodTrace(_id INTEGER PRIMARY KEY AUTOINCREMENT,episodeid INTEGER,albumId INTEGER,cid INTEGER,icon TEXT,episodeicon TEXT,type INTEGER,ord INTEGER,episodetitle TEXT,albumtitle TEXT,totalsize INTEGER,finish INTEGER, timestamp INTEGER,length INTEGER, file_path TEXT,isHd INTEGER,isNew INTEGER DEFAULT 0,btime INTEGER DEFAULT 0,etime INTEGER DEFAULT 0, duration INTEGER DEFAULT 0, isWatch INTEGER DEFAULT 0);");
    }

    private void createTable_localVideoTrace(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS LocalVideoTable(c_path TEXT PRIMARY KEY,c_title TEXT,c_position TEXT DEFAULT \"-1000\",c_filesize TEXT,c_timelength TEXT,c_video_w_h TEXT,c_video_type TEXT,c_createtime TEXT);");
    }

    private void createTable_liveBookTrace(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS LiveBookTrace(md5 TEXT PRIMARY KEY,programName TEXT,channelName TEXT,code TEXT,launch_mode INTEGER,play_time_long INTEGER,is_notify INTEGER,live_id TEXT,play_time TEXT);");
    }

    private void createTable_DialogMsgTrace(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS DialogMsgTrace(msgId TEXT PRIMARY KEY,msgTitle TEXT,message TEXT);");
    }

    private void createTable_LocalCacheTrace(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS LocalCacherace(cacheId TEXT PRIMARY KEY,assistkey TEXT,markid TEXT,cachetime TEXT,cachedata TEXT);");
    }

    private void createTable_TopChannelsTrace(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS TopChannelsTrace(channelId TEXT PRIMARY KEY,channelName TEXT,channelType TEXT,orderk INTEGER);");
    }

    private void createTable_HotTopTrace(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS HotTopTrace(id TEXT PRIMARY KEY,top INTEGER);");
    }

    private void createTable_worldCupTrace(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE worldCupTrace(_id INTEGER PRIMARY KEY AUTOINCREMENT,episodeid INTEGER,albumId INTEGER,cid INTEGER,icon TEXT,episodeicon TEXT,type INTEGER,ord INTEGER,episodetitle TEXT,albumtitle TEXT,totalsize INTEGER,finish INTEGER, timestamp INTEGER,length INTEGER, file_path TEXT,isHd INTEGER,btime INTEGER DEFAULT 0,etime INTEGER DEFAULT 0);");
    }

    private void alterChannelListTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE temp as select _id,channelid,numericKeys,name,ename,hassave from channellisttable");
        db.execSQL("drop table if exists channellisttable");
        db.execSQL("alter table temp rename to channellisttable");
    }

    private void alterChannelHisTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE temp2 as select _id,channelid,numericKeys,name,ename,isRecord,systemmillisecond from channelhislisttable");
        db.execSQL("drop table if exists channelhislisttable");
        db.execSQL("alter table temp2 rename to channelhislisttable");
    }

    private void createEmojiTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS emoji(_id INTEGER PRIMARY KEY AUTOINCREMENT,content TEXT,type INTEGER,message TEXT  );");
    }

    private void createLanguageConfigTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS language_settings(_id INTEGER PRIMARY KEY AUTOINCREMENT,pid INTEGER, audio_track_code TEXT, subtitle_code TEXT );");
    }
}
