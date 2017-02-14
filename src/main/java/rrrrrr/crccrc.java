package rrrrrr;

import com.immersion.aws.analytics.AnalyticsDataCollector;
import com.immersion.aws.analytics.AnalyticsDataPair;
import com.immersion.hapticmediasdk.controllers.MemoryAlignedFileReader;
import org.json.JSONObject;

public class crccrc extends AnalyticsDataCollector {
    public static int b0411Б04110411ББ = 1;
    public static int bБ041104110411ББ = 2;
    public static int bББ04110411ББ = 90;
    public String b0414041404140414ДД;
    public final /* synthetic */ MemoryAlignedFileReader bДДДД0414Д;

    public crccrc(MemoryAlignedFileReader memoryAlignedFileReader) {
        this.bДДДД0414Д = memoryAlignedFileReader;
        int i = bББ04110411ББ;
        switch ((i * (b0411Б04110411ББ + i)) % bБ041104110411ББ) {
            case 0:
                break;
            default:
                bББ04110411ББ = 25;
                b0411Б04110411ББ = b0411041104110411ББ();
                break;
        }
        super("content_id");
        this.b0414041404140414ДД = null;
    }

    public static int b0411041104110411ББ() {
        return 51;
    }

    public static int bББББ0411Б() {
        return 1;
    }

    public JSONObject getData() {
        try {
            int i = bББ04110411ББ;
            switch ((i * (bББББ0411Б() + i)) % bБ041104110411ББ) {
                case 0:
                    break;
                default:
                    bББ04110411ББ = 38;
                    b0411Б04110411ББ = 33;
                    break;
            }
            return new AnalyticsDataPair(super.getColumnName(), this.b0414041404140414ДД).getJson();
        } catch (Exception e) {
            throw e;
        }
    }

    public void setContentId(String str) {
        if (this.b0414041404140414ДД == null) {
            int i = bББ04110411ББ;
            switch ((i * (b0411Б04110411ББ + i)) % bБ041104110411ББ) {
                case 0:
                    break;
                default:
                    bББ04110411ББ = b0411041104110411ББ();
                    b0411Б04110411ББ = 28;
                    break;
            }
            this.b0414041404140414ДД = str;
        }
        while (true) {
            switch (1) {
                case null:
                    break;
                case 1:
                    return;
                default:
                    while (true) {
                        switch (1) {
                            case null:
                                break;
                            case 1:
                                return;
                            default:
                        }
                    }
            }
        }
    }
}
