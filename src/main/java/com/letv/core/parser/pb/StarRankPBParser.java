package com.letv.core.parser.pb;

import com.letv.core.parser.LetvPBParser;
import com.letv.ltpbdata.LTHeaderModelPBOuterClass.LTHeaderModelPB;
import com.letv.ltpbdata.LTStarRankModelDetailPBPKGOuterClass.LTStarRankModelDetailPBPKG;

public class StarRankPBParser extends LetvPBParser<LTStarRankModelDetailPBPKG> {
    protected LTHeaderModelPB getHeader(LTStarRankModelDetailPBPKG ltStarRankModelDetailPBPKG) {
        return ltStarRankModelDetailPBPKG.header;
    }

    protected LTStarRankModelDetailPBPKG parse(byte[] data) throws Exception {
        LTStarRankModelDetailPBPKG star = new LTStarRankModelDetailPBPKG();
        star.buildFromData(data);
        return star;
    }
}
