package com.letv.ltpbdata;

import com.letv.ltpbdata.LTHeaderModelPBOuterClass.LTHeaderModelPB;
import java.util.ArrayList;
import java.util.List;

public class LTStarRankModelDetailPBPKGOuterClass {

    public static final class LTStarRankModelDetailPB {
        public String headimg = "";
        public String id = "";
        public String nickname = "";
        public String num = "";
        public String ranking = "";

        public void buildFromData(byte[] data) {
            LTStarRankModelDetailPBPKGOuterClass.objFromData(this, "LTStarRankModelDetailPB", data);
        }

        public byte[] toData() {
            return LTStarRankModelDetailPBPKGOuterClass.serializeToData(this, "LTStarRankModelDetailPB");
        }

        public String toString(String strIndent) {
            return (((((("" + strIndent + "#########LTStarRankModelDetailPB#######\n") + strIndent + "id = " + this.id + "\n") + strIndent + "num = " + this.num + "\n") + strIndent + "nickname = " + this.nickname + "\n") + strIndent + "headimg = " + this.headimg + "\n") + strIndent + "ranking = " + this.ranking + "\n") + strIndent + "\n";
        }
    }

    public static final class LTStarRankModelDetailPBPKG {
        public LTStarRankModelPB data = new LTStarRankModelPB();
        public LTHeaderModelPB header = new LTHeaderModelPB();

        public void buildFromData(byte[] data) {
            LTStarRankModelDetailPBPKGOuterClass.objFromData(this, "LTStarRankModelDetailPBPKG", data);
        }

        public byte[] toData() {
            return LTStarRankModelDetailPBPKGOuterClass.serializeToData(this, "LTStarRankModelDetailPBPKG");
        }

        public String toString(String strIndent) {
            return ((((("" + strIndent + "#########LTStarRankModelDetailPBPKG#######\n") + strIndent + "header :\n") + this.header.toString(strIndent + "    ")) + strIndent + "data :\n") + this.data.toString(strIndent + "    ")) + strIndent + "\n";
        }
    }

    public static final class LTStarRankModelPB {
        public String code = "";
        public List data = new ArrayList();
        public String is_rank = "";

        public void buildFromData(byte[] data) {
            LTStarRankModelDetailPBPKGOuterClass.objFromData(this, "LTStarRankModelPB", data);
        }

        public byte[] toData() {
            return LTStarRankModelDetailPBPKGOuterClass.serializeToData(this, "LTStarRankModelPB");
        }

        public String toString(String strIndent) {
            String str = (("" + strIndent + "#########LTStarRankModelPB#######\n") + strIndent + "is_rank = " + this.is_rank + "\n") + strIndent + "code = " + this.code + "\n";
            for (int i = 0; i < this.data.size(); i++) {
                str = (str + strIndent + "data[" + String.valueOf(i) + "] : \n") + ((LTStarRankModelDetailPB) this.data.get(i)).toString(strIndent + "    ");
            }
            return str + strIndent + "\n";
        }
    }

    public static native void objFromData(Object obj, String str, byte[] bArr);

    public static native byte[] serializeToData(Object obj, String str);

    static {
        System.loadLibrary("pbjni");
    }
}
