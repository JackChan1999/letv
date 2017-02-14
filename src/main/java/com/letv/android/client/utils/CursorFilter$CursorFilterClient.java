package com.letv.android.client.utils;

import android.database.Cursor;

interface CursorFilter$CursorFilterClient {
    void changeCursor(Cursor cursor);

    CharSequence convertToString(Cursor cursor);

    Cursor getCursor();

    Cursor runQueryOnBackgroundThread(CharSequence charSequence);
}
