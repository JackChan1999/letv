package com.flurry.sdk;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class hu<T> {
    private static final String a = hu.class.getSimpleName();
    private final File b;
    private final iv<T> c;

    public hu(File file, String str, int i, iy<T> iyVar) {
        this.b = file;
        this.c = new it(new ix(str, i, iyVar));
    }

    public T a() {
        Closeable fileInputStream;
        Throwable e;
        Throwable th;
        T t = null;
        if (this.b != null) {
            if (this.b.exists()) {
                Object obj = null;
                try {
                    fileInputStream = new FileInputStream(this.b);
                    try {
                        t = this.c.b(fileInputStream);
                        jn.a(fileInputStream);
                    } catch (Exception e2) {
                        e = e2;
                        try {
                            ib.a(3, a, "Error reading data file:" + this.b.getName(), e);
                            obj = 1;
                            jn.a(fileInputStream);
                            if (obj != null) {
                                ib.a(3, a, "Deleting data file:" + this.b.getName());
                                this.b.delete();
                            }
                            return t;
                        } catch (Throwable th2) {
                            th = th2;
                            jn.a(fileInputStream);
                            throw th;
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                    fileInputStream = t;
                    ib.a(3, a, "Error reading data file:" + this.b.getName(), e);
                    obj = 1;
                    jn.a(fileInputStream);
                    if (obj != null) {
                        ib.a(3, a, "Deleting data file:" + this.b.getName());
                        this.b.delete();
                    }
                    return t;
                } catch (Throwable e4) {
                    fileInputStream = t;
                    th = e4;
                    jn.a(fileInputStream);
                    throw th;
                }
                if (obj != null) {
                    ib.a(3, a, "Deleting data file:" + this.b.getName());
                    this.b.delete();
                }
            } else {
                ib.a(5, a, "No data to read for file:" + this.b.getName());
            }
        }
        return t;
    }

    public void a(T t) {
        Throwable e;
        int i;
        Object obj = null;
        Closeable closeable = null;
        if (t == null) {
            ib.a(3, a, "No data to write for file:" + this.b.getName());
            obj = 1;
        } else {
            try {
                if (jm.a(this.b)) {
                    Closeable fileOutputStream = new FileOutputStream(this.b);
                    try {
                        this.c.a(fileOutputStream, t);
                        jn.a(fileOutputStream);
                    } catch (Exception e2) {
                        e = e2;
                        closeable = fileOutputStream;
                        try {
                            ib.a(3, a, "Error writing data file:" + this.b.getName(), e);
                            jn.a(closeable);
                            i = 1;
                            if (obj == null) {
                                ib.a(3, a, "Deleting data file:" + this.b.getName());
                                this.b.delete();
                            }
                        } catch (Throwable th) {
                            e = th;
                            jn.a(closeable);
                            throw e;
                        }
                    } catch (Throwable th2) {
                        e = th2;
                        closeable = fileOutputStream;
                        jn.a(closeable);
                        throw e;
                    }
                }
                throw new IOException("Cannot create parent directory!");
            } catch (Exception e3) {
                e = e3;
                ib.a(3, a, "Error writing data file:" + this.b.getName(), e);
                jn.a(closeable);
                i = 1;
                if (obj == null) {
                    ib.a(3, a, "Deleting data file:" + this.b.getName());
                    this.b.delete();
                }
            }
        }
        if (obj == null) {
            ib.a(3, a, "Deleting data file:" + this.b.getName());
            this.b.delete();
        }
    }

    public boolean b() {
        if (this.b == null) {
            return false;
        }
        return this.b.delete();
    }
}
