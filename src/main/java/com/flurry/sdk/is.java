package com.flurry.sdk;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class is<ObjectType> implements iv<ObjectType> {
    protected final iv<ObjectType> a;

    public is(iv<ObjectType> ivVar) {
        this.a = ivVar;
    }

    public void a(OutputStream outputStream, ObjectType objectType) throws IOException {
        if (this.a != null && outputStream != null && objectType != null) {
            this.a.a(outputStream, objectType);
        }
    }

    public ObjectType b(InputStream inputStream) throws IOException {
        if (this.a == null || inputStream == null) {
            return null;
        }
        return this.a.b(inputStream);
    }
}
