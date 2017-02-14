package com.letv.mobile.http.parameter;

import java.util.HashMap;

public abstract class LetvBaseParameter extends HashMap<String, Object> {
    private static final long serialVersionUID = 1;

    public abstract LetvBaseParameter combineParams();
}
