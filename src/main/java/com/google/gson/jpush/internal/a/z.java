package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.al;
import com.google.gson.jpush.am;
import com.google.gson.jpush.w;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.util.BitSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.UUID;

public final class z {
    public static final al<StringBuffer> A = new ai();
    public static final am B = a(StringBuffer.class, A);
    public static final al<URL> C = new aj();
    public static final am D = a(URL.class, C);
    public static final al<URI> E = new ak();
    public static final am F = a(URI.class, E);
    public static final al<InetAddress> G = new am();
    public static final am H = b(InetAddress.class, G);
    public static final al<UUID> I = new an();
    public static final am J = a(UUID.class, I);
    public static final am K = new ao();
    public static final al<Calendar> L = new aq();
    public static final am M = new ax(Calendar.class, GregorianCalendar.class, L);
    public static final al<Locale> N = new ar();
    public static final am O = a(Locale.class, N);
    public static final al<w> P = new as();
    public static final am Q = b(w.class, P);
    public static final am R = new at();
    public static final al<Class> a = new aa();
    public static final am b = a(Class.class, a);
    public static final al<BitSet> c = new al();
    public static final am d = a(BitSet.class, c);
    public static final al<Boolean> e = new aw();
    public static final al<Boolean> f = new ba();
    public static final am g = a(Boolean.TYPE, Boolean.class, e);
    public static final al<Number> h = new bb();
    public static final am i = a(Byte.TYPE, Byte.class, h);
    public static final al<Number> j = new bc();
    public static final am k = a(Short.TYPE, Short.class, j);
    public static final al<Number> l = new bd();
    public static final am m = a(Integer.TYPE, Integer.class, l);
    public static final al<Number> n = new be();
    public static final al<Number> o = new bf();
    public static final al<Number> p = new ab();
    public static final al<Number> q = new ac();
    public static final am r = a(Number.class, q);
    public static final al<Character> s = new ad();
    public static final am t = a(Character.TYPE, Character.class, s);
    public static final al<String> u = new ae();
    public static final al<BigDecimal> v = new af();
    public static final al<BigInteger> w = new ag();
    public static final am x = a(String.class, u);
    public static final al<StringBuilder> y = new ah();
    public static final am z = a(StringBuilder.class, y);

    public static <TT> am a(Class<TT> cls, al<TT> alVar) {
        return new au(cls, alVar);
    }

    public static <TT> am a(Class<TT> cls, Class<TT> cls2, al<? super TT> alVar) {
        return new av(cls, cls2, alVar);
    }

    private static <TT> am b(Class<TT> cls, al<TT> alVar) {
        return new ay(cls, alVar);
    }
}
