package com.google.gson;

import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.internal.bind.JsonTreeWriter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public abstract class TypeAdapter<T> {
    public abstract T read(JsonReader jsonReader) throws IOException;

    public abstract void write(JsonWriter jsonWriter, T t) throws IOException;

    final void toJson(Writer out, T value) throws IOException {
        write(new JsonWriter(out), value);
    }

    public final TypeAdapter<T> nullSafe() {
        return new TypeAdapter<T>() {
            public void write(JsonWriter out, T value) throws IOException {
                if (value == null) {
                    out.nullValue();
                } else {
                    TypeAdapter.this.write(out, value);
                }
            }

            public T read(JsonReader reader) throws IOException {
                if (reader.peek() != JsonToken.NULL) {
                    return TypeAdapter.this.read(reader);
                }
                reader.nextNull();
                return null;
            }
        };
    }

    final String toJson(T value) throws IOException {
        StringWriter stringWriter = new StringWriter();
        toJson(stringWriter, value);
        return stringWriter.toString();
    }

    final JsonElement toJsonTree(T value) {
        try {
            JsonTreeWriter jsonWriter = new JsonTreeWriter();
            jsonWriter.setLenient(true);
            write(jsonWriter, value);
            return jsonWriter.get();
        } catch (Throwable e) {
            throw new JsonIOException(e);
        }
    }

    final T fromJson(Reader in) throws IOException {
        JsonReader reader = new JsonReader(in);
        reader.setLenient(true);
        return read(reader);
    }

    final T fromJson(String json) throws IOException {
        return fromJson(new StringReader(json));
    }

    final T fromJsonTree(JsonElement jsonTree) {
        try {
            JsonReader jsonReader = new JsonTreeReader(jsonTree);
            jsonReader.setLenient(true);
            return read(jsonReader);
        } catch (Throwable e) {
            throw new JsonIOException(e);
        }
    }
}
