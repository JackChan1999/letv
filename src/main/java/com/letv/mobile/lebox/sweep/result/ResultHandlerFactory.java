package com.letv.mobile.lebox.sweep.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;
import com.google.zxing.client.result.ResultParser;
import com.letv.mobile.lebox.sweep.SweepActivity;

public final class ResultHandlerFactory {
    private ResultHandlerFactory() {
    }

    public static ResultHandler makeResultHandler(SweepActivity activity, Result rawResult) {
        ParsedResult result = parseResult(rawResult);
        if (result.getType() == ParsedResultType.URI) {
            return new URIResultHandler(activity, result);
        }
        return new TextResultHandler(activity, result, rawResult);
    }

    private static ParsedResult parseResult(Result rawResult) {
        return ResultParser.parseResult(rawResult);
    }
}
