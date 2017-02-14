package com.letv.core.bean;

import com.facebook.internal.NativeProtocol;
import com.letv.base.R;
import com.letv.core.BaseApplication;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.TipUtils;

public class CommentAddBean implements LetvBaseBean {
    public static final int CHECK_THEN_OPEN = 1;
    public static final int OPEN_THEN_CHECK = 2;
    private static final long serialVersionUID = 1;
    public String img;
    public String result;
    public int rule;
    public String status;
    public int total;

    public String toString() {
        return "LetvUser [status=" + this.status + ", total=" + this.total + ", rule=" + this.rule + "]";
    }

    public String getResultToastString(boolean isComment) {
        if (this.result != null) {
            LogInfo.log("songhang", "result: " + this.result);
            if (this.result.equals("200")) {
                if (isComment) {
                    return TipUtils.getTipMessage(DialogMsgConstantId.COMMENT_SUCCESS, R.string.detail_comment_toast_commit_success);
                }
                return BaseApplication.getInstance().getString(R.string.detail_reply_toast_commit_success);
            } else if (this.result.equals("fail")) {
                if (isComment) {
                    return TipUtils.getTipMessage(DialogMsgConstantId.COMMENT_FAIL, R.string.detail_comment_toast_commit_error_fail);
                }
                return BaseApplication.getInstance().getString(R.string.detail_reply_toast_commit_error_fail);
            } else if (this.result.equals(NativeProtocol.BRIDGE_ARG_ERROR_BUNDLE)) {
                if (isComment) {
                    return TipUtils.getTipMessage(DialogMsgConstantId.COMMENT_ERROR, R.string.detail_comment_toast_commit_error_fail);
                }
                return BaseApplication.getInstance().getString(R.string.detail_reply_toast_commit_error_fail);
            } else if (this.result.equals("repeat")) {
                return TipUtils.getTipMessage(DialogMsgConstantId.COMMENT_REPEAT, R.string.detail_comment_toast_commit_report);
            } else {
                if (this.result.equals("notlogged")) {
                    if (isComment) {
                        return TipUtils.getTipMessage(DialogMsgConstantId.COMMENT_NOTLOGGED, R.string.detail_comment_toast_commit_error_need_login);
                    }
                    return BaseApplication.getInstance().getString(R.string.detail_reply_toast_commit_error_need_login);
                } else if (this.result.equals("time")) {
                    return TipUtils.getTipMessage(DialogMsgConstantId.COMMENT_TIME, R.string.detail_comment_toast_commit_error_time);
                } else {
                    if (this.result.equals("more")) {
                        return TipUtils.getTipMessage(DialogMsgConstantId.COMMENT_MORE, R.string.detail_comment_toast_commit_error_time);
                    }
                    if (this.result.equals("forbidIP")) {
                        return TipUtils.getTipMessage(DialogMsgConstantId.COMMENT_FORBIDIP, R.string.detail_comment_toast_commit_error_permission_define);
                    }
                    if (this.result.equals("forbidUser")) {
                        return TipUtils.getTipMessage(DialogMsgConstantId.COMMENT_FORBIDUSER, R.string.detail_comment_toast_commit_error_permission_define);
                    }
                    if (this.result.equals("short")) {
                        return TipUtils.getTipMessage(DialogMsgConstantId.COMMENT_SHORT);
                    }
                    if (this.result.equals("long")) {
                        return TipUtils.getTipMessage(DialogMsgConstantId.COMMENT_LONG);
                    }
                }
            }
        }
        if (isComment) {
            return BaseApplication.getInstance().getString(R.string.detail_comment_toast_commit_error);
        }
        return BaseApplication.getInstance().getString(R.string.detail_reply_toast_commit_error);
    }
}
