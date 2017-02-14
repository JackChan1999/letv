package com.google.gson;

import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.io.EOFException;
import java.io.Reader;
import java.io.StringReader;

public final class JsonParser {
    public JsonElement parse(String json) throws JsonSyntaxException {
        return parse(new StringReader(json));
    }

    public JsonElement parse(Reader json) throws JsonIOException, JsonSyntaxException {
        try {
            JsonReader jsonReader = new JsonReader(json);
            JsonElement element = parse(jsonReader);
            if (element.isJsonNull() || jsonReader.peek() == JsonToken.END_DOCUMENT) {
                return element;
            }
            throw new JsonSyntaxException("Did not consume the entire document.");
        } catch (Throwable e) {
            throw new JsonSyntaxException(e);
        } catch (Throwable e2) {
            throw new JsonIOException(e2);
        } catch (Throwable e22) {
            throw new JsonSyntaxException(e22);
        }
    }

    public JsonElement parse(JsonReader json) throws JsonIOException, JsonSyntaxException {
        JsonElement parse;
        boolean lenient = json.isLenient();
        json.setLenient(true);
        try {
            parse = Streams.parse(json);
            json.setLenient(lenient);
        } catch (StackOverflowError e) {
            throw new JsonParseException("Failed parsing JSON source: " + json + " to Json", e);
        } catch (OutOfMemoryError e2) {
            throw new JsonParseException("Failed parsing JSON source: " + json + " to Json", e2);
        } catch (JsonParseException e3) {
            if (e3.getCause() instanceof EOFException) {
                parse = JsonNull.INSTANCE;
                json.setLenient(lenient);
            } else {
                throw e3;
            }
        } catch (Throwable th) {
            json.setLenient(lenient);
        }
        return parse;
    }
}
