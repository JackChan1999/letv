package com.letv.core.utils.external.alipay;

import android.text.TextUtils;

public class PayResult {
    private String memo;
    private String result;
    private String resultStatus;

    public PayResult(String rawResult) {
        if (!TextUtils.isEmpty(rawResult)) {
            for (String resultParam : rawResult.split(";")) {
                if (resultParam.startsWith("resultStatus")) {
                    this.resultStatus = gatValue(resultParam, "resultStatus");
                }
                if (resultParam.startsWith("result")) {
                    this.result = gatValue(resultParam, "result");
                }
                if (resultParam.startsWith("memo")) {
                    this.memo = gatValue(resultParam, "memo");
                }
            }
        }
    }

    public String toString() {
        return "resultStatus={" + this.resultStatus + "};memo={" + this.memo + "};result={" + this.result + "}";
    }

    private String gatValue(String content, String key) {
        String prefix = key + "={";
        return content.substring(content.indexOf(prefix) + prefix.length(), content.lastIndexOf("}"));
    }

    public String getResultStatus() {
        return this.resultStatus;
    }

    public String getMemo() {
        return this.memo;
    }

    public String getResult() {
        return this.result;
    }
}
