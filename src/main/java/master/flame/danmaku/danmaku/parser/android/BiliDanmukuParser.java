package master.flame.danmaku.danmaku.parser.android;

import android.text.TextUtils;
import com.letv.pp.utils.NetworkUtils;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Locale;
import master.flame.danmaku.danmaku.model.AlphaValue;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.Duration;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.DanmakuFactory;
import master.flame.danmaku.danmaku.util.DanmakuUtils;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;
import org.json.JSONArray;
import org.json.JSONException;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class BiliDanmukuParser extends BaseDanmakuParser {
    private float mDispScaleX;
    private float mDispScaleY;

    public class XmlContentHandler extends DefaultHandler {
        private static final String TRUE_STRING = "true";
        public boolean completed = false;
        public int index = 0;
        public BaseDanmaku item = null;
        public Danmakus result = null;

        public Danmakus getResult() {
            return this.result;
        }

        public void startDocument() throws SAXException {
            this.result = new Danmakus();
        }

        public void endDocument() throws SAXException {
            this.completed = true;
        }

        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            String tagName;
            if (localName.length() != 0) {
                tagName = localName;
            } else {
                tagName = qName;
            }
            if (tagName.toLowerCase(Locale.getDefault()).trim().equals("d")) {
                String[] values = attributes.getValue("p").split(",");
                if (values.length > 0) {
                    long time = (long) (Float.parseFloat(values[0]) * 1000.0f);
                    int type = Integer.parseInt(values[1]);
                    float textSize = Float.parseFloat(values[2]);
                    int color = Integer.parseInt(values[3]) | -16777216;
                    this.item = BiliDanmukuParser.this.mContext.mDanmakuFactory.createDanmaku(type, BiliDanmukuParser.this.mContext);
                    if (this.item != null) {
                        this.item.time = time;
                        this.item.textSize = (BiliDanmukuParser.this.mDispDensity - 0.6f) * textSize;
                        this.item.textColor = color;
                        this.item.textShadowColor = color <= -16777216 ? -1 : -16777216;
                        this.item.zanNum = "4321";
                        this.item.priority = (byte) 0;
                    }
                }
            }
        }

        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (this.item != null) {
                if (this.item.duration != null) {
                    if ((localName.length() != 0 ? localName : qName).equalsIgnoreCase("d")) {
                        this.item.setTimer(BiliDanmukuParser.this.mTimer);
                        this.result.addItem(this.item);
                    }
                }
                this.item = null;
            }
        }

        public void characters(char[] ch, int start, int length) {
            if (this.item != null) {
                DanmakuUtils.fillText(this.item, decodeXmlString(new String(ch, start, length)));
                BaseDanmaku baseDanmaku = this.item;
                int i = this.index;
                this.index = i + 1;
                baseDanmaku.index = i;
                String text = String.valueOf(this.item.text).trim();
                if (this.item.getType() == 7) {
                    if (text.startsWith("[")) {
                        if (text.endsWith("]")) {
                            int i2;
                            String[] textArr = null;
                            try {
                                JSONArray jSONArray = new JSONArray(text);
                                textArr = new String[jSONArray.length()];
                                for (i2 = 0; i2 < textArr.length; i2++) {
                                    textArr[i2] = jSONArray.getString(i2);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (textArr == null || textArr.length < 5) {
                                this.item = null;
                                return;
                            }
                            this.item.text = textArr[4];
                            float beginX = Float.parseFloat(textArr[0]);
                            float beginY = Float.parseFloat(textArr[1]);
                            float endX = beginX;
                            float endY = beginY;
                            String[] alphaArr = textArr[2].split(NetworkUtils.DELIMITER_LINE);
                            int beginAlpha = (int) (((float) AlphaValue.MAX) * Float.parseFloat(alphaArr[0]));
                            int endAlpha = beginAlpha;
                            if (alphaArr.length > 1) {
                                endAlpha = (int) (((float) AlphaValue.MAX) * Float.parseFloat(alphaArr[1]));
                            }
                            long alphaDuraion = (long) (Float.parseFloat(textArr[3]) * 1000.0f);
                            long translationDuration = alphaDuraion;
                            long translationStartDelay = 0;
                            float rotateY = 0.0f;
                            float rotateZ = 0.0f;
                            if (textArr.length >= 7) {
                                rotateZ = Float.parseFloat(textArr[5]);
                                rotateY = Float.parseFloat(textArr[6]);
                            }
                            if (textArr.length >= 11) {
                                endX = Float.parseFloat(textArr[7]);
                                endY = Float.parseFloat(textArr[8]);
                                if (!"".equals(textArr[9])) {
                                    translationDuration = (long) Integer.parseInt(textArr[9]);
                                }
                                if (!"".equals(textArr[10])) {
                                    translationStartDelay = (long) Float.parseFloat(textArr[10]);
                                }
                            }
                            this.item.duration = new Duration(alphaDuraion);
                            this.item.rotationZ = rotateZ;
                            this.item.rotationY = rotateY;
                            BiliDanmukuParser.this.mContext.mDanmakuFactory.fillTranslationData(this.item, beginX, beginY, endX, endY, translationDuration, translationStartDelay, BiliDanmukuParser.this.mDispScaleX, BiliDanmukuParser.this.mDispScaleY);
                            BiliDanmukuParser.this.mContext.mDanmakuFactory.fillAlphaData(this.item, beginAlpha, endAlpha, alphaDuraion);
                            if (textArr.length >= 12 && !TextUtils.isEmpty(textArr[11]) && "true".equals(textArr[11])) {
                                this.item.textShadowColor = 0;
                            }
                            if (textArr.length >= 13) {
                            }
                            if (textArr.length >= 14) {
                            }
                            if (textArr.length >= 15 && !"".equals(textArr[14])) {
                                String[] pointStrArray = textArr[14].substring(1).split("L");
                                if (pointStrArray != null && pointStrArray.length > 0) {
                                    float[][] points = (float[][]) Array.newInstance(Float.TYPE, new int[]{pointStrArray.length, 2});
                                    for (i2 = 0; i2 < pointStrArray.length; i2++) {
                                        String[] pointArray = pointStrArray[i2].split(",");
                                        points[i2][0] = Float.parseFloat(pointArray[0]);
                                        points[i2][1] = Float.parseFloat(pointArray[1]);
                                    }
                                    DanmakuFactory.fillLinePathData(this.item, points, BiliDanmukuParser.this.mDispScaleX, BiliDanmukuParser.this.mDispScaleY);
                                }
                            }
                        }
                    }
                }
            }
        }

        private String decodeXmlString(String title) {
            if (title.contains("&amp;")) {
                title = title.replace("&amp;", "&");
            }
            if (title.contains("&quot;")) {
                title = title.replace("&quot;", "\"");
            }
            if (title.contains("&gt;")) {
                title = title.replace("&gt;", SearchCriteria.GT);
            }
            if (title.contains("&lt;")) {
                return title.replace("&lt;", SearchCriteria.LT);
            }
            return title;
        }
    }

    static {
        System.setProperty("org.xml.sax.driver", "org.xmlpull.v1.sax2.Driver");
    }

    public Danmakus parse() {
        if (this.mDataSource != null) {
            AndroidFileSource source = this.mDataSource;
            try {
                XMLReader xmlReader = XMLReaderFactory.createXMLReader();
                XmlContentHandler contentHandler = new XmlContentHandler();
                xmlReader.setContentHandler(contentHandler);
                xmlReader.parse(new InputSource(source.data()));
                return contentHandler.getResult();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    public BaseDanmakuParser setDisplayer(IDisplayer disp) {
        super.setDisplayer(disp);
        this.mDispScaleX = ((float) this.mDispWidth) / DanmakuFactory.BILI_PLAYER_WIDTH;
        this.mDispScaleY = ((float) this.mDispHeight) / DanmakuFactory.BILI_PLAYER_HEIGHT;
        return this;
    }
}
