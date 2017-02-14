package com.letv.plugin.pluginloader.util;

import android.content.Context;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class JarXmlParser {
    private Context context;

    public class JarEntity {
        private String desc;
        private String name;
        private String packagename;
        private int status;
        private String title;
        private int type;
        private int version;

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPackagename() {
            return this.packagename;
        }

        public void setPackagename(String packagename) {
            this.packagename = packagename;
        }

        public int getType() {
            return this.type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getVersion() {
            return this.version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return this.desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return this.status;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Entity info: ");
            sb.append("name = " + this.name);
            sb.append(" type = " + this.type);
            sb.append(" version = " + this.version);
            sb.append(" title = " + this.title);
            sb.append(" desc = " + this.desc);
            sb.append(" status = " + this.status);
            return sb.toString();
        }
    }

    public JarXmlParser(Context context) {
        this.context = context;
    }

    public ArrayList<JarEntity> parseJars() {
        ArrayList<JarEntity> arrayList = null;
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            JarHandler handler = new JarHandler(this, null);
            parser.parse(this.context.getResources().getAssets().open("jars/jarconfig.xml"), handler);
            arrayList = handler.getList();
        } catch (Exception e) {
        }
        return arrayList;
    }
}
