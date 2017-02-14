package com.soundink.lib;

import android.content.Context;
import android.os.AsyncTask;
import com.soundink.lib.b.b;
import java.io.IOException;
import org.json.JSONException;

final class a extends AsyncTask<Void, Void, String> {
    static String a = "";
    private d b = new d(this.c);
    private String c = com.soundink.lib.c.a.b().toString();
    private String d;

    protected final /* synthetic */ Object doInBackground(Object... objArr) {
        return a();
    }

    protected final /* synthetic */ void onPostExecute(Object obj) {
        String str = (String) obj;
        if (str != null) {
            try {
                a = e.a(str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public a(Context context) {
        com.soundink.lib.c.a aVar = new com.soundink.lib.c.a(context);
    }

    protected final void onPreExecute() {
    }

    private String a() {
        try {
            this.d = this.b.a(SoundInkInterface.getAppKey(), this.c);
        } catch (com.soundink.lib.b.a e) {
            e.printStackTrace();
        } catch (b e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        } catch (JSONException e4) {
            e4.printStackTrace();
        }
        return this.d;
    }
}
