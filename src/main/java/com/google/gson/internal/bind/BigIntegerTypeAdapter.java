package com.google.gson.internal.bind;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.math.BigInteger;

public final class BigIntegerTypeAdapter extends TypeAdapter<BigInteger> {
    public BigInteger read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        try {
            return new BigInteger(in.nextString());
        } catch (Throwable e) {
            throw new JsonSyntaxException(e);
        }
    }

    public void write(JsonWriter out, BigInteger value) throws IOException {
        out.value((Number) value);
    }
}
