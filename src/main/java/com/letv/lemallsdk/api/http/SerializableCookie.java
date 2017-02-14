package com.letv.lemallsdk.api.http;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

public class SerializableCookie implements Serializable {
    private static final long serialVersionUID = 1933780518550558083L;
    private transient BasicClientCookie clientCookie;
    private final transient Cookie cookie;

    public SerializableCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public Cookie getCookie() {
        Cookie bestCookie = this.cookie;
        if (this.clientCookie != null) {
            return this.clientCookie;
        }
        return bestCookie;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(this.cookie.getName());
        out.writeObject(this.cookie.getValue());
        out.writeObject(this.cookie.getComment());
        out.writeObject(this.cookie.getDomain());
        out.writeObject(this.cookie.getExpiryDate());
        out.writeObject(this.cookie.getPath());
        out.writeInt(this.cookie.getVersion());
        out.writeBoolean(this.cookie.isSecure());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.clientCookie = new BasicClientCookie((String) in.readObject(), (String) in.readObject());
        this.clientCookie.setComment((String) in.readObject());
        this.clientCookie.setDomain((String) in.readObject());
        this.clientCookie.setExpiryDate((Date) in.readObject());
        this.clientCookie.setPath((String) in.readObject());
        this.clientCookie.setVersion(in.readInt());
        this.clientCookie.setSecure(in.readBoolean());
    }
}
