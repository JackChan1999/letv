package rrrrrr;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;

public class ccrcrc {
    public static final int CACHE_MAX_LENGTH = 200;
    public static int b04250425ХХ04250425 = 1;
    public static int bХ04250425Х04250425 = 0;
    public static int bХ0425ХХ04250425 = 63;
    public static int bХХ0425Х04250425 = 2;
    private ArrayBlockingQueue b0414ДД0414ДД;
    private String bДДД0414ДД;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ccrcrc(java.lang.String r5) {
        /*
        r4 = this;
        r0 = 0;
        r4.<init>();
        r4.bДДД0414ДД = r5;
    L_0x0006:
        switch(r0) {
            case 0: goto L_0x000d;
            case 1: goto L_0x0006;
            default: goto L_0x0009;
        };
    L_0x0009:
        switch(r0) {
            case 0: goto L_0x000d;
            case 1: goto L_0x0006;
            default: goto L_0x000c;
        };
    L_0x000c:
        goto L_0x0009;
    L_0x000d:
        r0 = new java.io.File;
        r1 = r4.bДДД0414ДД;
        r0.<init>(r1);
        r1 = r0.exists();
        if (r1 == 0) goto L_0x003a;
    L_0x001a:
        r0 = r0.length();
        r2 = 0;
        r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        r1 = bХ0425ХХ04250425;
        r2 = b04250425ХХ04250425;
        r2 = r2 + r1;
        r1 = r1 * r2;
        r2 = bХХ0425Х04250425;
        r1 = r1 % r2;
        switch(r1) {
            case 0: goto L_0x0038;
            default: goto L_0x002e;
        };
    L_0x002e:
        r1 = 84;
        bХ0425ХХ04250425 = r1;
        r1 = b0425Х0425Х04250425();
        b04250425ХХ04250425 = r1;
    L_0x0038:
        if (r0 != 0) goto L_0x0044;
    L_0x003a:
        r0 = new java.util.concurrent.ArrayBlockingQueue;
        r1 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        r0.<init>(r1);
        r4.b0414ДД0414ДД = r0;
    L_0x0043:
        return;
    L_0x0044:
        r0 = r4.bДДД0414ДД;
        r0 = r4.b04490449щ044904490449(r0);
        r4.b0414ДД0414ДД = r0;
        goto L_0x0043;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.ccrcrc.<init>(java.lang.String):void");
    }

    public static int b042504250425Х04250425() {
        return 1;
    }

    public static int b0425Х0425Х04250425() {
        return 51;
    }

    public static int b0425ХХ042504250425() {
        return 2;
    }

    private ArrayBlockingQueue b04490449щ044904490449(String str) {
        int i = 5;
        try {
            ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(200);
            try {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(str), "UTF-8"));
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        arrayBlockingQueue.add(readLine);
                        while (true) {
                            try {
                                int[] iArr = new int[-1];
                            } catch (Exception e) {
                                while (true) {
                                    try {
                                        i /= 0;
                                    } catch (Exception e2) {
                                    }
                                }
                            }
                        }
                    }
                    bufferedReader.close();
                    return arrayBlockingQueue;
                } catch (Exception e3) {
                    throw e3;
                }
            } catch (IOException e4) {
            }
        } catch (Exception e32) {
            throw e32;
        }
    }

    public static int bХХХ042504250425() {
        return 0;
    }

    public void load() {
        this.b0414ДД0414ДД = b04490449щ044904490449(this.bДДД0414ДД);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String peek() {
        /*
        r2 = this;
    L_0x0000:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0009;
            case 1: goto L_0x0000;
            default: goto L_0x0004;
        };
    L_0x0004:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0000;
            case 1: goto L_0x0009;
            default: goto L_0x0008;
        };
    L_0x0008:
        goto L_0x0004;
    L_0x0009:
        r0 = bХ0425ХХ04250425;
        r1 = b04250425ХХ04250425;
        r0 = r0 + r1;
        r1 = bХ0425ХХ04250425;
        r0 = r0 * r1;
        r1 = bХХ0425Х04250425;
        r0 = r0 % r1;
        r1 = bХХХ042504250425();
        if (r0 == r1) goto L_0x0026;
    L_0x001a:
        r0 = b0425Х0425Х04250425();
        bХ0425ХХ04250425 = r0;
        r0 = b0425Х0425Х04250425();
        bХ04250425Х04250425 = r0;
    L_0x0026:
        r0 = r2.b0414ДД0414ДД;
        r0 = r0.peek();
        r0 = (java.lang.String) r0;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.ccrcrc.peek():java.lang.String");
    }

    public void push(String str) {
        try {
            this.b0414ДД0414ДД.add(str);
        } catch (IllegalStateException e) {
            try {
                this.b0414ДД0414ДД.remove();
                int i = bХ0425ХХ04250425;
                switch ((i * (b042504250425Х04250425() + i)) % bХХ0425Х04250425) {
                    case 0:
                        break;
                    default:
                        bХ0425ХХ04250425 = 99;
                        bХ04250425Х04250425 = b0425Х0425Х04250425();
                        break;
                }
                try {
                    push(str);
                } catch (Exception e2) {
                    throw e2;
                }
            } catch (Exception e22) {
                throw e22;
            }
        }
    }

    public String remove() {
        try {
            ArrayBlockingQueue arrayBlockingQueue = this.b0414ДД0414ДД;
            int i = bХ0425ХХ04250425;
            switch ((i * (b04250425ХХ04250425 + i)) % b0425ХХ042504250425()) {
                case 0:
                    break;
                default:
                    bХ0425ХХ04250425 = b0425Х0425Х04250425();
                    bХ04250425Х04250425 = 94;
                    break;
            }
            String str = (String) arrayBlockingQueue.remove();
            while (true) {
                switch (1) {
                    case null:
                        break;
                    case 1:
                        return str;
                    default:
                        while (true) {
                            switch (1) {
                                case null:
                                    break;
                                case 1:
                                    return str;
                                default:
                            }
                        }
                }
            }
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void save() {
        /*
        r4 = this;
        r0 = new java.io.FileOutputStream;	 Catch:{ IOException -> 0x0059, Exception -> 0x0064 }
        r1 = r4.bДДД0414ДД;	 Catch:{ IOException -> 0x0059, Exception -> 0x0064 }
        r0.<init>(r1);	 Catch:{ IOException -> 0x0059, Exception -> 0x0064 }
        r1 = new java.io.BufferedWriter;	 Catch:{ IOException -> 0x0059, Exception -> 0x0064 }
        r2 = new java.io.OutputStreamWriter;	 Catch:{ IOException -> 0x0059, Exception -> 0x0064 }
        r2.<init>(r0);	 Catch:{ IOException -> 0x0059, Exception -> 0x0064 }
        r1.<init>(r2);	 Catch:{ IOException -> 0x0059, Exception -> 0x0064 }
        r0 = r4.b0414ДД0414ДД;	 Catch:{ IOException -> 0x0059, Exception -> 0x0064 }
        r2 = r0.iterator();	 Catch:{ IOException -> 0x0059, Exception -> 0x0064 }
    L_0x0017:
        r0 = bХ0425ХХ04250425;
        r3 = b04250425ХХ04250425;
        r0 = r0 + r3;
        r3 = bХ0425ХХ04250425;
        r0 = r0 * r3;
        r3 = bХХ0425Х04250425;
        r0 = r0 % r3;
        r3 = bХ04250425Х04250425;
        if (r0 == r3) goto L_0x0032;
    L_0x0026:
        r0 = b0425Х0425Х04250425();
        bХ0425ХХ04250425 = r0;
        r0 = b0425Х0425Х04250425();
        bХ04250425Х04250425 = r0;
    L_0x0032:
        r0 = r2.hasNext();	 Catch:{ IOException -> 0x0059, Exception -> 0x0064 }
        if (r0 == 0) goto L_0x005b;
    L_0x0038:
        r0 = r2.next();	 Catch:{ IOException -> 0x0059, Exception -> 0x0064 }
        r0 = (java.lang.String) r0;	 Catch:{ IOException -> 0x0059, Exception -> 0x0064 }
        r3 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0059, Exception -> 0x0064 }
        r3.<init>();	 Catch:{ IOException -> 0x0059, Exception -> 0x0064 }
        r0 = r3.append(r0);	 Catch:{ IOException -> 0x0059, Exception -> 0x0062 }
        r3 = "line.separator";
        r3 = java.lang.System.getProperty(r3);	 Catch:{ IOException -> 0x0059, Exception -> 0x0062 }
        r0 = r0.append(r3);	 Catch:{ IOException -> 0x0059, Exception -> 0x0062 }
        r0 = r0.toString();	 Catch:{ IOException -> 0x0059, Exception -> 0x0062 }
        r1.append(r0);	 Catch:{ IOException -> 0x0059, Exception -> 0x0062 }
        goto L_0x0017;
    L_0x0059:
        r0 = move-exception;
    L_0x005a:
        return;
    L_0x005b:
        r1.flush();	 Catch:{ IOException -> 0x0059, Exception -> 0x0062 }
        r1.close();	 Catch:{ IOException -> 0x0059, Exception -> 0x0062 }
        goto L_0x005a;
    L_0x0062:
        r0 = move-exception;
        throw r0;
    L_0x0064:
        r0 = move-exception;
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.ccrcrc.save():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int size() {
        /*
        r2 = this;
        r0 = 1;
    L_0x0001:
        switch(r0) {
            case 0: goto L_0x0001;
            case 1: goto L_0x0008;
            default: goto L_0x0004;
        };
    L_0x0004:
        switch(r0) {
            case 0: goto L_0x0001;
            case 1: goto L_0x0008;
            default: goto L_0x0007;
        };
    L_0x0007:
        goto L_0x0004;
    L_0x0008:
        r0 = bХ0425ХХ04250425;
        r1 = b04250425ХХ04250425;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = bХХ0425Х04250425;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x001d;
            default: goto L_0x0014;
        };
    L_0x0014:
        r0 = b0425Х0425Х04250425();
        bХ0425ХХ04250425 = r0;
        r0 = 0;
        bХ04250425Х04250425 = r0;
    L_0x001d:
        r0 = r2.b0414ДД0414ДД;
        r0 = r0.size();
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: rrrrrr.ccrcrc.size():int");
    }
}
