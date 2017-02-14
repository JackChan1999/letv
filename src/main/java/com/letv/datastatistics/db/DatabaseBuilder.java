package com.letv.datastatistics.db;

import android.content.ContentValues;
import android.database.Cursor;

public abstract class DatabaseBuilder<T> {
    public abstract T build(Cursor cursor);

    public abstract ContentValues deconstruct(T t);
}
