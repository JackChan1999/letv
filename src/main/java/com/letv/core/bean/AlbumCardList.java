package com.letv.core.bean;

public class AlbumCardList implements LetvBaseBean {
    public AlbumInfo albumInfo = new AlbumInfo();
    public String cardOrder = "";
    public CardArrayList<CmsOperateCardBean> cmsOperateList = new CardArrayList();
    public CardArrayList<AlbumInfo> fullVersionList = new CardArrayList();
    public BaseIntroductionBean intro;
    public boolean mIsAlbum = true;
    public CardArrayList<MusicCardBean> musicList = new CardArrayList();
    public CardArrayList<VideoBean> outList = new CardArrayList();
    public RedPacketBean redPacket;
    public RelateBean relateBean = new RelateBean();
    public CardArrayList<StarCardBean> starList = new CardArrayList();
    public CardArrayList<AlbumInfo> topicAlbumList = new CardArrayList();
    public VideoBean videoInfo = new VideoBean();
    public VideoListCardBean videoList = new VideoListCardBean();
    public CardArrayList<VideoBean> videoRelateList = new CardArrayList();
    public VoteNumBean voteNumBean = new VoteNumBean();
    public CardArrayList<VideoBean> yourLikeList = new CardArrayList();
}
