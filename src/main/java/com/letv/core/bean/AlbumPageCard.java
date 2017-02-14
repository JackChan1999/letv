package com.letv.core.bean;

import android.text.TextUtils;
import com.letv.core.api.LetvRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.datastatistics.util.DataConstant.ERROR;
import com.letv.pp.utils.NetworkUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlbumPageCard implements LetvBaseBean {
    private static final int TOPIC_CID = 9527;
    public AlbumPageCardBlock adCard1 = new AlbumPageCardBlock();
    public AlbumPageCardBlock adCard2 = new AlbumPageCardBlock();
    public Map<String, List<AlbumPageCardBlock>> blockMap = new HashMap();
    public AlbumPageCardBlock cmsOperateCard = new AlbumPageCardBlock();
    public AlbumPageCardBlock fullVersionCard = new AlbumPageCardBlock();
    public GeneralCard generalCard = new GeneralCard();
    public AlbumPageCardBlock gridCard = new AlbumPageCardBlock();
    public IntroCard introCard = new IntroCard();
    public AlbumPageCardBlock listCard = new AlbumPageCardBlock();
    public AlbumPageCardBlock musicCard = new AlbumPageCardBlock();
    public Map<String, CardOrder> orderMap = new HashMap();
    public int pcversion;
    public AlbumPageCardBlock periodsCard = new AlbumPageCardBlock();
    public AlbumPageCardBlock redPacketCard = new AlbumPageCardBlock();
    public AlbumPageCardBlock relateCard = new AlbumPageCardBlock();
    public AlbumPageCardBlock starCard = new AlbumPageCardBlock();
    public AlbumPageCardBlock surroundingCard = new AlbumPageCardBlock();
    public AlbumPageCardBlock topicAlbumCard = new AlbumPageCardBlock();
    public AlbumPageCardBlock topicVideoCard = new AlbumPageCardBlock();
    public AlbumPageCardBlock videoRelateCard = new AlbumPageCardBlock();
    public AlbumPageCardBlock vipCard = new AlbumPageCardBlock();
    public AlbumPageCardBlock voteCard = new AlbumPageCardBlock();
    public AlbumPageCardBlock watchCard = new AlbumPageCardBlock();
    public AlbumPageCardBlock yourLikeCard = new AlbumPageCardBlock();

    public void reOrderCards(AlbumCardList albumCardList, AlbumCardBuildCallBack callBack) {
        if (albumCardList != null && callBack != null) {
            new LetvRequest().setRequestType(RequestManner.CACHE_ONLY).setCache(new 2(this, albumCardList)).setCallback(new 1(this, callBack)).add();
        }
    }

    private void doReOrderCards(AlbumCardList albumCardList) {
        if (albumCardList != null) {
            List<AlbumPageCardBlock> blockList;
            for (String key : this.blockMap.keySet()) {
                blockList = (List) BaseTypeUtils.getElementFromMap(this.blockMap, key);
                if (!BaseTypeUtils.isListEmpty(blockList)) {
                    for (AlbumPageCardBlock block : blockList) {
                        block.position = -1;
                    }
                }
            }
            int cid = -1;
            boolean isPositive = isPostiveVideo(albumCardList, null);
            if (!albumCardList.mIsAlbum) {
                cid = TOPIC_CID;
            } else if (albumCardList.videoInfo != null) {
                cid = albumCardList.videoInfo.cid;
            }
            String order;
            if (TextUtils.isEmpty(albumCardList.cardOrder)) {
                CardOrder cardOrder = (CardOrder) BaseTypeUtils.getElementFromMap(this.orderMap, cid + "");
                if (cardOrder != null) {
                    order = isPositive ? cardOrder.a : cardOrder.b;
                } else {
                    cardOrder = (CardOrder) BaseTypeUtils.getElementFromMap(this.orderMap, "-1");
                    order = isPositive ? cardOrder.a : cardOrder.b;
                }
            } else {
                order = albumCardList.cardOrder;
            }
            int position = 0;
            for (String id : order.split(NetworkUtils.DELIMITER_LINE)) {
                String id2;
                if ("16".equals(id2)) {
                    id2 = "4";
                }
                blockList = (List) BaseTypeUtils.getElementFromMap(this.blockMap, id2);
                if (!BaseTypeUtils.isListEmpty(blockList)) {
                    for (AlbumPageCardBlock block2 : blockList) {
                        block2.position = position;
                    }
                }
                position++;
            }
        }
    }

    public static boolean isPostiveVideo(AlbumCardList cardList, VideoBean video) {
        if (cardList == null) {
            return false;
        }
        boolean isPositive = false;
        if (cardList.mIsAlbum) {
            if (video == null) {
                video = cardList.videoInfo;
            }
            AlbumInfo album = cardList.albumInfo;
            if (video == null) {
                return false;
            }
            int cid = video.cid;
            if (cid == 2 || cid == 1 || cid == 5 || cid == 11 || cid == 1000 || cid == 16 || cid == 1021 || cid == 12 || cid == 34) {
                if (video.isFeature()) {
                    isPositive = true;
                }
            } else if (album != null && album.varietyShow == 1) {
                boolean specialChannel;
                if (cid == 4 || cid == 22 || cid == 17) {
                    specialChannel = true;
                } else {
                    specialChannel = false;
                }
                isPositive = !specialChannel || video.isFeature();
            }
        } else {
            isPositive = !BaseTypeUtils.isListEmpty(cardList.topicAlbumList);
        }
        if (video != null && TextUtils.equals(video.videoTypeKey, "182202") && video.cid == 11 && cardList.videoList.containThisFragment(video.vid)) {
            isPositive = true;
        }
        return isPositive;
    }

    public static boolean isLegalCardStyle(String style) {
        if (!TextUtils.isEmpty(style)) {
            boolean z = true;
            switch (style.hashCode()) {
                case 48627:
                    if (style.equals("102")) {
                        z = false;
                        break;
                    }
                    break;
                case 48628:
                    if (style.equals("103")) {
                        z = true;
                        break;
                    }
                    break;
                case 48629:
                    if (style.equals("104")) {
                        z = true;
                        break;
                    }
                    break;
                case 48630:
                    if (style.equals("105")) {
                        z = true;
                        break;
                    }
                    break;
                case 48631:
                    if (style.equals("106")) {
                        z = true;
                        break;
                    }
                    break;
                case 48632:
                    if (style.equals("107")) {
                        z = true;
                        break;
                    }
                    break;
                case 48633:
                    if (style.equals("108")) {
                        z = true;
                        break;
                    }
                    break;
                case 48634:
                    if (style.equals("109")) {
                        z = true;
                        break;
                    }
                    break;
                case 48656:
                    if (style.equals("110")) {
                        z = true;
                        break;
                    }
                    break;
                case 48657:
                    if (style.equals(ERROR.DOWNLOAD_ERROR_D)) {
                        z = true;
                        break;
                    }
                    break;
                case 48658:
                    if (style.equals("112")) {
                        z = true;
                        break;
                    }
                    break;
                case 48660:
                    if (style.equals("114")) {
                        z = true;
                        break;
                    }
                    break;
                case 48661:
                    if (style.equals("115")) {
                        z = true;
                        break;
                    }
                    break;
                case 48662:
                    if (style.equals("116")) {
                        z = true;
                        break;
                    }
                    break;
                case 48691:
                    if (style.equals("124")) {
                        z = true;
                        break;
                    }
                    break;
                case 48692:
                    if (style.equals("125")) {
                        z = true;
                        break;
                    }
                    break;
                case 48693:
                    if (style.equals("126")) {
                        z = true;
                        break;
                    }
                    break;
                case 48694:
                    if (style.equals("127")) {
                        z = true;
                        break;
                    }
                    break;
                case 48695:
                    if (style.equals("128")) {
                        z = true;
                        break;
                    }
                    break;
                case 48696:
                    if (style.equals("129")) {
                        z = true;
                        break;
                    }
                    break;
                case 48718:
                    if (style.equals("130")) {
                        z = true;
                        break;
                    }
                    break;
                case 48719:
                    if (style.equals("131")) {
                        z = true;
                        break;
                    }
                    break;
                case 48720:
                    if (style.equals("132")) {
                        z = true;
                        break;
                    }
                    break;
                case 48721:
                    if (style.equals("133")) {
                        z = true;
                        break;
                    }
                    break;
                case 48722:
                    if (style.equals("134")) {
                        z = true;
                        break;
                    }
                    break;
                case 48723:
                    if (style.equals("135")) {
                        z = true;
                        break;
                    }
                    break;
                case 48724:
                    if (style.equals("136")) {
                        z = true;
                        break;
                    }
                    break;
                case 48725:
                    if (style.equals("137")) {
                        z = true;
                        break;
                    }
                    break;
                case 48726:
                    if (style.equals("138")) {
                        z = true;
                        break;
                    }
                    break;
                case 48749:
                    if (style.equals("140")) {
                        z = true;
                        break;
                    }
                    break;
                case 48750:
                    if (style.equals("141")) {
                        z = true;
                        break;
                    }
                    break;
                case 48814:
                    if (style.equals("163")) {
                        z = true;
                        break;
                    }
                    break;
            }
            switch (z) {
                case false:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                    return true;
            }
        }
        return false;
    }
}
