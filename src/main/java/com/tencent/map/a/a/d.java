package com.tencent.map.a.a;

import com.tencent.map.b.h;
import java.util.ArrayList;
import java.util.Iterator;

public class d {
    private long A;
    public int a;
    public double b;
    public double c;
    public double d;
    public double e;
    public double f;
    public double g;
    public int h;
    public String i;
    public String j;
    public String k;
    public String l;
    public String m;
    public String n;
    public String o;
    public String p;
    public String q;
    public String r;
    public String s;
    public String t;
    public String u;
    public String v;
    public ArrayList<c> w;
    public boolean x;
    public int y;
    public int z;

    public d() {
        this.a = 1;
        this.b = 0.0d;
        this.c = 0.0d;
        this.d = -1.0d;
        this.e = 0.0d;
        this.f = 0.0d;
        this.g = 0.0d;
        this.h = 0;
        this.i = null;
        this.j = null;
        this.k = null;
        this.l = null;
        this.m = null;
        this.n = null;
        this.o = null;
        this.p = null;
        this.q = null;
        this.r = null;
        this.s = null;
        this.t = null;
        this.u = null;
        this.v = null;
        this.w = null;
        this.x = false;
        this.y = 0;
        this.z = -1;
        this.A = -1;
        this.e = 0.0d;
        this.d = 0.0d;
        this.c = 0.0d;
        this.b = 0.0d;
        this.p = null;
        this.o = null;
        this.n = null;
        this.m = null;
        this.x = false;
        this.A = System.currentTimeMillis();
        this.y = 0;
        this.z = -1;
        this.w = null;
    }

    public d(d dVar) {
        this.a = 1;
        this.b = 0.0d;
        this.c = 0.0d;
        this.d = -1.0d;
        this.e = 0.0d;
        this.f = 0.0d;
        this.g = 0.0d;
        this.h = 0;
        this.i = null;
        this.j = null;
        this.k = null;
        this.l = null;
        this.m = null;
        this.n = null;
        this.o = null;
        this.p = null;
        this.q = null;
        this.r = null;
        this.s = null;
        this.t = null;
        this.u = null;
        this.v = null;
        this.w = null;
        this.x = false;
        this.y = 0;
        this.z = -1;
        this.A = -1;
        this.a = dVar.a;
        this.b = dVar.b;
        this.c = dVar.c;
        this.d = dVar.d;
        this.e = dVar.e;
        this.x = dVar.x;
        this.i = dVar.i;
        this.h = 0;
        this.j = dVar.j;
        this.k = dVar.k;
        this.l = dVar.l;
        this.m = dVar.m;
        this.n = dVar.n;
        this.o = dVar.o;
        this.p = dVar.p;
        this.q = dVar.q;
        this.r = dVar.r;
        this.s = dVar.s;
        this.t = dVar.t;
        this.u = dVar.u;
        this.v = dVar.v;
        this.A = dVar.a();
        this.y = dVar.y;
        this.z = dVar.z;
        this.w = null;
        if (dVar.w != null) {
            this.w = new ArrayList();
            Iterator it = dVar.w.iterator();
            while (it.hasNext()) {
                this.w.add((c) it.next());
            }
        }
    }

    public long a() {
        return this.A;
    }

    public void a(String str) {
        String str2 = "Unknown";
        this.l = str2;
        this.k = str2;
        this.j = str2;
        this.i = str2;
        if (str != null) {
            String[] split = str.split(",");
            if (split != null) {
                int length = split.length;
                if (length > 0) {
                    this.i = split[0];
                }
                if (length > 1) {
                    this.j = split[1];
                }
                if (length == 3) {
                    this.k = split[1];
                } else if (length > 3) {
                    this.k = split[2];
                }
                if (length == 3) {
                    this.l = split[2];
                } else if (length > 3) {
                    this.l = split[3];
                }
            }
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.z).append(" ").append(this.y).append(" ").append(this.x ? "Mars" : "WGS84").append(" ").append(this.a == 0 ? "GPS" : "Network").append("\n");
        stringBuilder.append(this.b).append(" ").append(this.c).append("\n");
        stringBuilder.append(this.d).append(" ").append(this.e).append("\n");
        stringBuilder.append(this.f).append(" ").append(this.g).append("\n");
        if (this.z == 3 || this.z == 4) {
            stringBuilder.append(this.i).append(" ").append(this.j).append(" ").append(this.k).append(" ").append(this.l).append(" ").append(this.m).append(" ").append(this.n).append(" ").append(this.o).append(" ").append(this.p).append("\n");
        }
        if (this.z == 4 && this.w != null) {
            stringBuilder.append(this.w.size()).append("\n");
            Iterator it = this.w.iterator();
            while (it.hasNext()) {
                c cVar = (c) it.next();
                stringBuilder.append(cVar.a).append(",").append(cVar.b).append(",").append(cVar.c).append(",").append(cVar.d).append(",").append(cVar.e).append(",").append(cVar.f).append("\n");
            }
        }
        if (this.z == 7) {
            if (this.h == 0) {
                stringBuilder.append(this.i).append(" ").append(this.j).append(" ").append(this.k).append(" ").append(this.l).append(" ").append(this.m).append(" ").append(this.n).append(" ").append(this.o).append(" ").append(this.p).append("\n");
            } else if (this.h == 1) {
                stringBuilder.append(this.i).append(" ").append(this.q).append(" ").append(this.r).append(" ").append(this.s).append(" ").append(this.t).append(" ").append(this.u).append(" ").append(this.v).append("\n");
            }
        }
        h.a(stringBuilder.toString());
        return stringBuilder.toString();
    }
}
