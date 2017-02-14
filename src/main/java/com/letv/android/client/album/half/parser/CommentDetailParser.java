package com.letv.android.client.album.half.parser;

import com.letv.core.bean.CommentDetailBean;
import com.letv.core.bean.CommentDetailBean.Source;
import com.letv.core.bean.CommentDetailBean.User;
import com.letv.core.constant.DownloadConstant.BroadcaseIntentParams;
import com.letv.core.constant.PlayConstant;
import com.letv.core.parser.LetvMobileParser;
import com.letv.core.utils.LogInfo;
import com.sina.weibo.sdk.component.ShareRequestParam;
import com.sina.weibo.sdk.component.WidgetRequestParam;
import java.util.LinkedList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CommentDetailParser extends LetvMobileParser<CommentDetailBean> {
    protected final String BODY = "body";
    private final String CID = "cid";
    private final String CITY = "city";
    private final String COMMENT = "comment";
    private final String COMMENT_ID = "commentid";
    private final String CONTENT = WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT;
    private final String CTIME = "ctime";
    protected final String DATA = ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA;
    private final String DETAIL = "detail";
    private final String FLAG = "flag";
    protected final String HEADER = "header";
    private final String HTIME = PlayConstant.HTIME;
    private final String ID = "id";
    private final String IMG = "img";
    private final String IMG180 = "180";
    private final String IMG310 = "310";
    private final String IMGORIGINAL = "o";
    private final String IMGPACK = "imgPack";
    private final String ISLIKE = "isLike";
    private final String ISSTAR = "isStar";
    private final String ISVIP = "isvip";
    private final String LIKE = "like";
    private final String LINK = "link";
    private final String PHOTO = "photo";
    private final String PID = "pid";
    private final String REPLYNUM = "replynum";
    private final String REPLYS = "replys";
    protected final String RESULT = "result";
    private final String RULE = "rule";
    private final String SHARE = "share";
    private final String SOURCE = "source";
    private final String SSOUID = "ssouid";
    protected final String STATUS = "status";
    private final String TITLE = "title";
    protected final String TOTAL = BroadcaseIntentParams.KEY_TOTAL;
    private final String UID = "uid";
    private final String USER = "user";
    private final String USERNAME = "username";
    private final String VOTE_FLAG = "voteFlag";
    private final String VTIME = "vtime";
    private final String XID = "xid";
    private final String _ID = "_id";
    private int status;

    protected boolean canParse(String data) {
        LogInfo.log("fornia", "canParse 进入 object = " + data.toString());
        try {
            JSONObject object = new JSONObject(data);
            if (object.has("header")) {
                this.status = getInt(object.getJSONObject("header"), "status");
                if (this.status == 3) {
                    LogInfo.log("fornia", "canParse object =EXCEPTION ");
                    return false;
                } else if (this.status != 1) {
                    return false;
                } else {
                    LogInfo.log("fornia", "canParse object = NORMAL");
                    return true;
                }
            }
            LogInfo.log("fornia", "canParse object = " + data.toString());
            return false;
        } catch (JSONException e) {
            LogInfo.log("fornia", "canParse ~~~~~~~~~e");
            e.printStackTrace();
            return true;
        }
    }

    protected CommentDetailBean parse(JSONObject object) throws Exception {
        int j;
        boolean z = true;
        CommentDetailBean commentDetail = new CommentDetailBean();
        if (has(object, ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA)) {
            JSONArray commentReplyArray = getJSONArray(object, ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA);
            if (commentReplyArray != null && commentReplyArray.length() > 0) {
                commentDetail.data = new LinkedList();
                for (j = 0; j < commentReplyArray.length(); j++) {
                    commentDetail.data.add(new ReplyParser().parse(commentReplyArray.getJSONObject(j)));
                }
            }
        }
        if (has(object, "comment")) {
            object = getJSONObject(object, "comment");
        }
        if (object == null) {
            return null;
        }
        if (this.status != 1) {
            return commentDetail;
        }
        commentDetail._id = getString(object, "_id");
        commentDetail.commentid = getString(object, "commentid");
        commentDetail.cid = getLong(object, "cid");
        commentDetail.city = getString(object, "city");
        commentDetail.content = getString(object, WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT);
        commentDetail.ctime = getLong(object, "ctime");
        commentDetail.flag = getInt(object, "flag");
        commentDetail.voteFlag = getInt(object, "voteFlag");
        commentDetail.htime = getInt(object, PlayConstant.HTIME);
        commentDetail.img = getString(object, "img");
        commentDetail.isLike = getBoolean(object, "isLike");
        commentDetail.like = getInt(object, "like");
        commentDetail.pid = getLong(object, "pid");
        commentDetail.replynum = getInt(object, "replynum");
        commentDetail.share = getInt(object, "share");
        commentDetail.title = getString(object, "title");
        commentDetail.vtime = getString(object, "vtime");
        commentDetail.vid = getLong(object, "xid");
        commentDetail.user = new User();
        JSONObject userObj = getJSONObject(object, "user");
        if (userObj != null) {
            commentDetail.user.username = getString(userObj, "username");
            commentDetail.user.ssouid = getString(userObj, "ssouid");
            commentDetail.user.photo = getString(userObj, "photo");
            commentDetail.user.isvip = getInt(userObj, "isvip");
            User user = commentDetail.user;
            if (getInt(userObj, "isStar") != 1) {
                z = false;
            }
            user.isStar = z;
        }
        commentDetail.source = new Source();
        JSONObject sourceObj = getJSONObject(object, "source");
        if (sourceObj != null) {
            commentDetail.source.detail = getString(sourceObj, "detail");
            commentDetail.source.link = getString(sourceObj, "link");
            commentDetail.source.id = getInt(sourceObj, "id");
        }
        if (has(object, "replys")) {
            JSONArray replyArray = getJSONArray(object, "replys");
            if (replyArray != null && replyArray.length() > 0) {
                commentDetail.replys = new LinkedList();
                for (j = 0; j < replyArray.length(); j++) {
                    commentDetail.replys.add(new ReplyParser().parse(replyArray.getJSONObject(j)));
                }
            }
        }
        if (!has(object, "imgPack")) {
            return commentDetail;
        }
        JSONObject imgObj = getJSONObject(object, "imgPack");
        if (imgObj == null) {
            return commentDetail;
        }
        LogInfo.log("fornia", "评论详情 commentDetail has sourceObj：" + imgObj);
        commentDetail.img180 = imgObj.has("180") ? getString(imgObj, "180") : "";
        commentDetail.img310 = imgObj.has("310") ? getString(imgObj, "310") : "";
        commentDetail.imgOri = imgObj.has("o") ? getString(imgObj, "o") : "";
        return commentDetail;
    }

    protected JSONObject getData(String data) throws Exception {
        if (this.status == 1) {
            return getJSONObject(new JSONObject(data), "body");
        }
        return null;
    }
}
