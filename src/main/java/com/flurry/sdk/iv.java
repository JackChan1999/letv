package com.flurry.sdk;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface iv<ObjectType> {
    void a(OutputStream outputStream, ObjectType objectType) throws IOException;

    ObjectType b(InputStream inputStream) throws IOException;
}
