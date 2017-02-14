package com.letv.business.flow.star;

import android.content.Context;
import android.text.TextUtils;
import com.letv.business.flow.star.StarFlowCallback.StarFollowType;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.api.MediaAssetApi;
import com.letv.core.bean.BookedProgramsSetBean;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.FollowStatusMapBean;
import com.letv.core.bean.PlayVoteListBean.PlayVoteResultBean;
import com.letv.core.bean.PushBookLive;
import com.letv.core.bean.ResultBean;
import com.letv.core.bean.StarInfoBean;
import com.letv.core.db.DBManager;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyRequestQueue.RequestFilter;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.listener.OnEntryResponse;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyDbCache;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.PlayVoteParser;
import com.letv.core.parser.StarFollowStatusParser;
import com.letv.core.parser.StarParser;
import com.letv.core.parser.StarSingleFollowStatusParser;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LogInfo;
import java.util.ArrayList;
import java.util.List;

public class StarFlow {
    private static final int MAX_VIDEO_NUM = 4;
    private static final String REQUEST_BOOKED_PROGRAMS = "tag_star_request_booked_programs";
    private static final String REQUEST_FOLLOW = "tag_star_request_follow";
    private static final String REQUEST_FOLLOW_STATUS = "tag_star_request_follow_status";
    private static final String REQUEST_STAR_INFO = "tag_star_request_star_info";
    private static final String REQUEST_VOTE = "tag_star_request_vote";
    private static final String TAG_STAR = "tag_star_";
    private Context mContext;

    private class RequestBookedPrograms {
        private StarBookCallback callback;

        public RequestBookedPrograms(StarBookCallback callback) {
            this.callback = callback;
        }

        public void start() {
            LogInfo.log("clf", "onBooked RequestBookedPrograms");
            new LetvRequest().setTag(StarFlow.REQUEST_BOOKED_PROGRAMS).setCache(new VolleyDbCache<BookedProgramsSetBean>() {
                public BookedProgramsSetBean get(VolleyRequest<?> volleyRequest) {
                    BookedProgramsSetBean bean = new BookedProgramsSetBean();
                    ArrayList<PushBookLive> list = DBManager.getInstance().getLiveBookTrace().getAllTrace();
                    int i = 0;
                    while (list != null && i < list.size()) {
                        PushBookLive book = (PushBookLive) list.get(i);
                        bean.bookedPrograms.add(book.md5_id);
                        LogInfo.log("clf", "onBooked RequestBookedPrograms book.md5_id=" + book.md5_id);
                        i++;
                    }
                    return bean;
                }

                public void add(VolleyRequest<?> volleyRequest, BookedProgramsSetBean response) {
                }
            }).setCallback(new SimpleResponse<BookedProgramsSetBean>() {
                public void onCacheResponse(VolleyRequest<BookedProgramsSetBean> volleyRequest, BookedProgramsSetBean result, DataHull hull, CacheResponseState state) {
                    LogInfo.log("clf", "onBooked RequestBookedPrograms callback result.bookedPrograms=" + result.bookedPrograms);
                    if (RequestBookedPrograms.this.callback != null) {
                        RequestBookedPrograms.this.callback.onBookedPrograms(result.bookedPrograms);
                    }
                }
            }).add();
        }
    }

    private class RequestFollow {
        private StarFlowCallback callback;
        private String starId;
        private int type;

        public RequestFollow(StarFlowCallback callback, String starId, int type) {
            this.callback = callback;
            this.starId = starId;
            this.type = type;
        }

        public void start() {
            Volley.getQueue().cancelWithTag(StarFlow.this.mContext.toString() + StarFlow.REQUEST_FOLLOW);
            String url = "";
            if (this.type == 1) {
                url = MediaAssetApi.getInstance().getStarFollowUrl(this.starId);
            } else {
                url = MediaAssetApi.getInstance().getStarUnFollowUrl(this.starId);
            }
            new LetvRequest().setTag(StarFlow.REQUEST_FOLLOW_STATUS).setUrl(url).setParser(new StarSingleFollowStatusParser()).setCache(new VolleyNoCache()).setCallback(new SimpleResponse<ResultBean>() {
                public void onNetworkResponse(VolleyRequest request, ResultBean result, DataHull hull, NetworkResponseState state) {
                    switch (state) {
                        case SUCCESS:
                            if (RequestFollow.this.callback == null) {
                                return;
                            }
                            if (result != null) {
                                LogInfo.log("clf", "type=" + RequestFollow.this.type + ",result.code=" + result.code);
                                if (RequestFollow.this.type == 0 && result.code == 200) {
                                    RequestFollow.this.callback.onStarFollow(true, RequestFollow.this.type);
                                    return;
                                } else if (RequestFollow.this.type == 1 && result.code == 200) {
                                    RequestFollow.this.callback.onStarFollow(true, RequestFollow.this.type);
                                    return;
                                } else {
                                    RequestFollow.this.callback.onStarFollow(false, RequestFollow.this.type);
                                    return;
                                }
                            }
                            RequestFollow.this.callback.onStarFollow(false, RequestFollow.this.type);
                            return;
                        case NETWORK_ERROR:
                        case NETWORK_NOT_AVAILABLE:
                            RequestFollow.this.callback.netError(StarFollowType.FOLLOW);
                            return;
                        case RESULT_ERROR:
                            RequestFollow.this.callback.onStarFollow(false, RequestFollow.this.type);
                            return;
                        default:
                            return;
                    }
                }
            }).add();
        }
    }

    private class RequestFollowStatus {
        private StarFlowCallback callback;
        private String starIds = "";

        public RequestFollowStatus(StarFlowCallback callback, List<String> idsList) {
            this.callback = callback;
            if (!BaseTypeUtils.isListEmpty(idsList)) {
                StringBuffer stringBuffer = new StringBuffer();
                for (String id : idsList) {
                    stringBuffer.append("," + id);
                }
                stringBuffer.delete(0, 1);
                this.starIds = stringBuffer.toString();
            }
        }

        public void start() {
            Volley.getQueue().cancelWithTag(StarFlow.this.mContext.toString() + StarFlow.REQUEST_FOLLOW_STATUS);
            new LetvRequest().setTag(StarFlow.REQUEST_FOLLOW_STATUS).setUrl(MediaAssetApi.getInstance().getStarFollowStatusUrl(this.starIds)).setParser(new StarFollowStatusParser()).setCache(new VolleyNoCache()).setCallback(new SimpleResponse<FollowStatusMapBean>() {
                public void onNetworkResponse(VolleyRequest request, FollowStatusMapBean result, DataHull hull, NetworkResponseState state) {
                    boolean isFollowed = true;
                    switch (state) {
                        case SUCCESS:
                            if (RequestFollowStatus.this.callback != null) {
                                RequestFollowStatus.this.callback.onAskFollowStatusList(result);
                                if (result.size() == 1) {
                                    if (((Integer) result.get(result.keySet().iterator().next())).intValue() != 1) {
                                        isFollowed = false;
                                    }
                                    RequestFollowStatus.this.callback.onAskFollowStatus(isFollowed);
                                    return;
                                }
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                }
            }).add();
        }
    }

    private class RequestStarInfo {
        private StarFlowCallback callbak;
        private String starId;

        public RequestStarInfo(StarFlowCallback callbak, String starId) {
            this.callbak = callbak;
            this.starId = starId;
        }

        public void start() {
            Volley.getQueue().cancelWithTag(StarFlow.this.mContext.toString() + StarFlow.REQUEST_STAR_INFO);
            new LetvRequest().setUrl(LetvUrlMaker.getStarInfoUrl(this.starId)).setTag(StarFlow.REQUEST_STAR_INFO).setCache(new VolleyNoCache()).setParser(new StarParser()).setCallback(new OnEntryResponse<StarInfoBean>() {
                public void onNetworkResponse(VolleyRequest<StarInfoBean> volleyRequest, StarInfoBean result, DataHull hull, NetworkResponseState state) {
                    switch (state) {
                        case SUCCESS:
                            if (RequestStarInfo.this.callbak != null) {
                                RequestStarInfo.this.callbak.onStarSuccess(result);
                                return;
                            }
                            return;
                        case NETWORK_ERROR:
                        case NETWORK_NOT_AVAILABLE:
                            if (RequestStarInfo.this.callbak != null) {
                                RequestStarInfo.this.callbak.netError(StarFollowType.STAR_INFO);
                                return;
                            }
                            return;
                        case RESULT_ERROR:
                            if (RequestStarInfo.this.callbak != null) {
                                RequestStarInfo.this.callbak.onStarSuccess(null);
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                }

                public void onCacheResponse(VolleyRequest<StarInfoBean> volleyRequest, StarInfoBean result, DataHull hull, CacheResponseState state) {
                }

                public void onErrorReport(VolleyRequest<StarInfoBean> volleyRequest, String errorInfo) {
                }
            }).add();
        }
    }

    private class RequestStarVote {
        private StarFlowCallback callbak;
        private String id;

        public RequestStarVote(String id, StarFlowCallback callbak) {
            this.id = id;
            this.callbak = callbak;
        }

        public void start() {
            Volley.getQueue().cancelWithTag(StarFlow.this.mContext.toString() + StarFlow.REQUEST_VOTE);
            String url = MediaAssetApi.getInstance().getPatchVoteByStarIdUrl(this.id);
            LogInfo.log("clf", "url=" + url);
            new LetvRequest().setRequestType(RequestManner.NETWORK_ONLY).setCache(new VolleyNoCache()).setUrl(url).setParser(new PlayVoteParser()).setCallback(new SimpleResponse<PlayVoteResultBean>() {
                public void onNetworkResponse(VolleyRequest<PlayVoteResultBean> volleyRequest, PlayVoteResultBean result, DataHull hull, NetworkResponseState state) {
                    switch (state) {
                        case SUCCESS:
                            if (result.code == 200) {
                                if (RequestStarVote.this.callbak != null) {
                                    RequestStarVote.this.callbak.onVoteSuccess(result);
                                    return;
                                }
                                return;
                            } else if (RequestStarVote.this.callbak != null) {
                                RequestStarVote.this.callbak.onVoteSuccess(null);
                                return;
                            } else {
                                return;
                            }
                        case NETWORK_ERROR:
                        case NETWORK_NOT_AVAILABLE:
                            if (RequestStarVote.this.callbak != null) {
                                RequestStarVote.this.callbak.netError(StarFollowType.VOTE);
                                return;
                            }
                            return;
                        case RESULT_ERROR:
                            if (RequestStarVote.this.callbak != null) {
                                RequestStarVote.this.callbak.onVoteSuccess(null);
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                }
            }).add();
        }
    }

    public StarFlow(Context context) {
        this.mContext = context;
    }

    public void requestStarInfo(StarFlowCallback callbak, String starId) {
        new RequestStarInfo(callbak, starId).start();
        requestFollowStatus(callbak, starId);
    }

    public void requestStarVote(String id, StarFlowCallback callbak) {
        new RequestStarVote(id, callbak).start();
    }

    public void requestStarFollow(StarFlowCallback callback, String starId, int type) {
        new RequestFollow(callback, starId, type).start();
    }

    public void requestRequestBookedPrograms(StarBookCallback callback) {
        new RequestBookedPrograms(callback).start();
    }

    public void requestFollowStatus(StarFlowCallback callback, String starId) {
        List id = new ArrayList();
        id.add(starId + "");
        requestFollowStatus(callback, id);
    }

    public void requestFollowStatus(StarFlowCallback callback, List<String> ids) {
        new RequestFollowStatus(callback, ids).start();
    }

    public void onDestroy() {
        Volley.getQueue().cancelAll(new RequestFilter() {
            public boolean apply(VolleyRequest<?> request) {
                return (request == null || TextUtils.isEmpty(request.getTag()) || !request.getTag().startsWith(StarFlow.this.mContext.toString() + StarFlow.TAG_STAR)) ? false : true;
            }
        });
    }
}
