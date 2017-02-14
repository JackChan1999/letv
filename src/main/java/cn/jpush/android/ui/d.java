package cn.jpush.android.ui;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import cn.jpush.android.data.c;
import cn.jpush.android.util.a;
import java.util.List;

final class d implements OnItemClickListener {
    final /* synthetic */ List a;
    final /* synthetic */ ListViewActivity b;

    d(ListViewActivity listViewActivity, List list) {
        this.b = listViewActivity;
        this.a = list;
    }

    public final void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        c cVar = (c) this.a.get(i);
        cVar.p = false;
        this.b.startActivity(a.a(this.b, cVar, false));
    }
}
