package com.google.gson.jpush.internal;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

public final class b {
    static final Type[] a = new Type[0];
    private static final String[] z;

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r2 = 1;
        r1 = 0;
        r0 = 3;
        r4 = new java.lang.String[r0];
        r3 = "\u0019f\u001cw:({\b28|]\u0000s*/2LB8.\u0001w-9l\u0005h<8J\u0015b<p>\u0003`y\u001b{\u0002w+5}-`+=g8k)92Lp,(>P";
        r0 = -1;
        r5 = r4;
        r6 = r4;
        r4 = r1;
    L_0x000b:
        r3 = r3.toCharArray();
        r7 = r3.length;
        if (r7 > r2) goto L_0x0065;
    L_0x0012:
        r8 = r1;
    L_0x0013:
        r9 = r3;
        r10 = r8;
        r13 = r7;
        r7 = r3;
        r3 = r13;
    L_0x0018:
        r12 = r7[r8];
        r11 = r10 % 5;
        switch(r11) {
            case 0: goto L_0x0059;
            case 1: goto L_0x005c;
            case 2: goto L_0x005f;
            case 3: goto L_0x0062;
            default: goto L_0x001f;
        };
    L_0x001f:
        r11 = 89;
    L_0x0021:
        r11 = r11 ^ r12;
        r11 = (char) r11;
        r7[r8] = r11;
        r8 = r10 + 1;
        if (r3 != 0) goto L_0x002d;
    L_0x0029:
        r7 = r9;
        r10 = r8;
        r8 = r3;
        goto L_0x0018;
    L_0x002d:
        r7 = r3;
        r3 = r9;
    L_0x002f:
        if (r7 > r8) goto L_0x0013;
    L_0x0031:
        r7 = new java.lang.String;
        r7.<init>(r3);
        r3 = r7.intern();
        switch(r0) {
            case 0: goto L_0x0046;
            case 1: goto L_0x0050;
            default: goto L_0x003d;
        };
    L_0x003d:
        r5[r4] = r3;
        r0 = "2k\u0000~";
        r3 = r0;
        r4 = r2;
        r5 = r6;
        r0 = r1;
        goto L_0x000b;
    L_0x0046:
        r5[r4] = r3;
        r3 = 2;
        r0 = "b>\u0005ay3xLf ,{L";
        r4 = r3;
        r5 = r6;
        r3 = r0;
        r0 = r2;
        goto L_0x000b;
    L_0x0050:
        r5[r4] = r3;
        z = r6;
        r0 = new java.lang.reflect.Type[r1];
        a = r0;
        return;
    L_0x0059:
        r11 = 92;
        goto L_0x0021;
    L_0x005c:
        r11 = 30;
        goto L_0x0021;
    L_0x005f:
        r11 = 108; // 0x6c float:1.51E-43 double:5.34E-322;
        goto L_0x0021;
    L_0x0062:
        r11 = 18;
        goto L_0x0021;
    L_0x0065:
        r8 = r1;
        goto L_0x002f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.internal.b.<clinit>():void");
    }

    static /* synthetic */ int a(Object obj) {
        return obj != null ? obj.hashCode() : 0;
    }

    public static Type a(Type type) {
        if (type instanceof Class) {
            c cVar;
            Class cls = (Class) type;
            if (cls.isArray()) {
                cVar = new c(a(cls.getComponentType()));
            } else {
                Object obj = cls;
            }
            return cVar;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return new d(parameterizedType.getOwnerType(), parameterizedType.getRawType(), parameterizedType.getActualTypeArguments());
        } else if (type instanceof GenericArrayType) {
            return new c(((GenericArrayType) type).getGenericComponentType());
        } else {
            if (!(type instanceof WildcardType)) {
                return type;
            }
            WildcardType wildcardType = (WildcardType) type;
            return new e(wildcardType.getUpperBounds(), wildcardType.getLowerBounds());
        }
    }

    public static Type a(Type type, Class<?> cls) {
        Type b = b(type, cls, Collection.class);
        if (b instanceof WildcardType) {
            b = ((WildcardType) b).getUpperBounds()[0];
        }
        return b instanceof ParameterizedType ? ((ParameterizedType) b).getActualTypeArguments()[0] : Object.class;
    }

    private static Type a(Type type, Class<?> cls, Class<?> cls2) {
        Class cls3 = cls;
        Type type2 = type;
        while (cls2 != cls3) {
            if (cls2.isInterface()) {
                Class[] interfaces = cls3.getInterfaces();
                int length = interfaces.length;
                for (int i = 0; i < length; i++) {
                    if (interfaces[i] == cls2) {
                        return cls3.getGenericInterfaces()[i];
                    }
                    if (cls2.isAssignableFrom(interfaces[i])) {
                        type = cls3.getGenericInterfaces()[i];
                        cls3 = interfaces[i];
                        type2 = type;
                        break;
                    }
                }
            }
            if (cls3.isInterface()) {
                return cls2;
            }
            while (cls3 != Object.class) {
                Class<?> superclass = cls3.getSuperclass();
                if (superclass == cls2) {
                    return cls3.getGenericSuperclass();
                }
                if (cls2.isAssignableFrom(superclass)) {
                    type = cls3.getGenericSuperclass();
                    cls3 = superclass;
                    type2 = type;
                } else {
                    Class<?> cls4 = superclass;
                }
            }
            return cls2;
        }
        return type2;
    }

    public static Type a(Type type, Class<?> cls, Type type2) {
        int i = 0;
        Type type3 = type2;
        while (type3 instanceof TypeVariable) {
            Type type4 = (TypeVariable) type3;
            GenericDeclaration genericDeclaration = type4.getGenericDeclaration();
            Class cls2 = genericDeclaration instanceof Class ? (Class) genericDeclaration : null;
            if (cls2 != null) {
                type3 = a(type, (Class) cls, cls2);
                if (type3 instanceof ParameterizedType) {
                    TypeVariable[] typeParameters = cls2.getTypeParameters();
                    for (int i2 = 0; i2 < typeParameters.length; i2++) {
                        if (type4.equals(typeParameters[i2])) {
                            type3 = ((ParameterizedType) type3).getActualTypeArguments()[i2];
                            continue;
                            if (type3 == type4) {
                                return type3;
                            }
                        }
                    }
                    throw new NoSuchElementException();
                }
            }
            type3 = type4;
            continue;
            if (type3 == type4) {
                return type3;
            }
        }
        Type a;
        if ((type3 instanceof Class) && ((Class) type3).isArray()) {
            Class cls3 = (Class) type3;
            type4 = cls3.getComponentType();
            a = a(type, (Class) cls, type4);
            return type4 != a ? f(a) : cls3;
        } else if (type3 instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType) type3;
            type4 = genericArrayType.getGenericComponentType();
            a = a(type, (Class) cls, type4);
            return type4 != a ? f(a) : genericArrayType;
        } else if (type3 instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type3;
            type4 = parameterizedType.getOwnerType();
            Type a2 = a(type, (Class) cls, type4);
            int i3 = a2 != type4 ? 1 : 0;
            r2 = parameterizedType.getActualTypeArguments();
            int length = r2.length;
            while (i < length) {
                Type a3 = a(type, (Class) cls, r2[i]);
                if (a3 != r2[i]) {
                    if (i3 == 0) {
                        r2 = (Type[]) r2.clone();
                        i3 = 1;
                    }
                    r2[i] = a3;
                }
                i++;
            }
            return i3 != 0 ? new d(a2, parameterizedType.getRawType(), r2) : parameterizedType;
        } else if (!(type3 instanceof WildcardType)) {
            return type3;
        } else {
            WildcardType wildcardType = (WildcardType) type3;
            Type[] lowerBounds = wildcardType.getLowerBounds();
            r2 = wildcardType.getUpperBounds();
            if (lowerBounds.length == 1) {
                if (a(type, (Class) cls, lowerBounds[0]) == lowerBounds[0]) {
                    return wildcardType;
                }
                return new e(new Type[]{Object.class}, new Type[]{a(type, (Class) cls, lowerBounds[0])});
            } else if (r2.length != 1 || a(type, (Class) cls, r2[0]) == r2[0]) {
                return wildcardType;
            } else {
                return new e(new Type[]{a(type, (Class) cls, r2[0])}, a);
            }
        }
    }

    public static boolean a(Type type, Type type2) {
        Object obj = type2;
        Object obj2 = type;
        while (obj2 != obj) {
            if (obj2 instanceof Class) {
                return obj2.equals(obj);
            }
            if (obj2 instanceof ParameterizedType) {
                if (!(obj instanceof ParameterizedType)) {
                    return false;
                }
                ParameterizedType parameterizedType = (ParameterizedType) obj2;
                ParameterizedType parameterizedType2 = (ParameterizedType) obj;
                Type ownerType = parameterizedType.getOwnerType();
                Type ownerType2 = parameterizedType2.getOwnerType();
                Object obj3 = (ownerType == ownerType2 || (ownerType != null && ownerType.equals(ownerType2))) ? 1 : null;
                return obj3 != null && parameterizedType.getRawType().equals(parameterizedType2.getRawType()) && Arrays.equals(parameterizedType.getActualTypeArguments(), parameterizedType2.getActualTypeArguments());
            } else if (obj2 instanceof GenericArrayType) {
                if (!(obj instanceof GenericArrayType)) {
                    return false;
                }
                GenericArrayType genericArrayType = (GenericArrayType) obj;
                obj2 = ((GenericArrayType) obj2).getGenericComponentType();
                obj = genericArrayType.getGenericComponentType();
            } else if (obj2 instanceof WildcardType) {
                if (!(obj instanceof WildcardType)) {
                    return false;
                }
                WildcardType wildcardType = (WildcardType) obj2;
                WildcardType wildcardType2 = (WildcardType) obj;
                return Arrays.equals(wildcardType.getUpperBounds(), wildcardType2.getUpperBounds()) && Arrays.equals(wildcardType.getLowerBounds(), wildcardType2.getLowerBounds());
            } else if (!(obj2 instanceof TypeVariable)) {
                return false;
            } else {
                if (!(obj instanceof TypeVariable)) {
                    return false;
                }
                TypeVariable typeVariable = (TypeVariable) obj2;
                TypeVariable typeVariable2 = (TypeVariable) obj;
                return typeVariable.getGenericDeclaration() == typeVariable2.getGenericDeclaration() && typeVariable.getName().equals(typeVariable2.getName());
            }
        }
        return true;
    }

    public static Class<?> b(Type type) {
        Object obj = type;
        while (!(obj instanceof Class)) {
            if (obj instanceof ParameterizedType) {
                Type rawType = ((ParameterizedType) obj).getRawType();
                a.a(rawType instanceof Class);
                return (Class) rawType;
            } else if (obj instanceof GenericArrayType) {
                return Array.newInstance(b(((GenericArrayType) obj).getGenericComponentType()), 0).getClass();
            } else {
                if (obj instanceof TypeVariable) {
                    return Object.class;
                }
                if (obj instanceof WildcardType) {
                    obj = ((WildcardType) obj).getUpperBounds()[0];
                } else {
                    throw new IllegalArgumentException(new StringBuilder(z[0]).append(obj).append(z[2]).append(obj == null ? z[1] : obj.getClass().getName()).toString());
                }
            }
        }
        return (Class) obj;
    }

    private static Type b(Type type, Class<?> cls, Class<?> cls2) {
        a.a(cls2.isAssignableFrom(cls));
        return a(type, (Class) cls, a(type, (Class) cls, (Class) cls2));
    }

    public static Type[] b(Type type, Class<?> cls) {
        if (type == Properties.class) {
            return new Type[]{String.class, String.class};
        }
        Type b = b(type, cls, Map.class);
        if (b instanceof ParameterizedType) {
            return ((ParameterizedType) b).getActualTypeArguments();
        }
        return new Type[]{Object.class, Object.class};
    }

    public static String c(Type type) {
        return type instanceof Class ? ((Class) type).getName() : type.toString();
    }

    public static Type d(Type type) {
        return type instanceof GenericArrayType ? ((GenericArrayType) type).getGenericComponentType() : ((Class) type).getComponentType();
    }

    static /* synthetic */ void e(Type type) {
        boolean z = ((type instanceof Class) && ((Class) type).isPrimitive()) ? false : true;
        a.a(z);
    }

    private static GenericArrayType f(Type type) {
        return new c(type);
    }
}
