package com.letv.android.client.utils;

import android.content.Context;
import android.text.TextUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.MD5;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

public class IniFile {
    public static final String APPKEY = "appkey";
    public static final String PCODE = "pcode";
    public static final String RSA = "rsa";
    private String charSet;
    private boolean checkOK;
    private boolean checked;
    private File file;
    private String line_separator;
    private Map<String, Section> sections;

    public void setLineSeparator(String line_separator) {
        this.line_separator = line_separator;
    }

    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }

    public void set(String section, String key, Object value) {
        Section sectionObject = (Section) this.sections.get(section);
        if (sectionObject == null) {
            sectionObject = new Section(this);
        }
        Section.access$002(sectionObject, section);
        sectionObject.set(key, value);
        this.sections.put(section, sectionObject);
    }

    public Section get(String section) {
        return (Section) this.sections.get(section);
    }

    public Object get(Context context, String section, String key) {
        return get(section, key, null);
    }

    private Object get(String section, String key, String defaultValue) {
        Section sectionObject = (Section) this.sections.get(section);
        if (sectionObject == null) {
            return null;
        }
        Object value = sectionObject.get(key);
        if (value == null || value.toString().trim().equals("")) {
            return defaultValue;
        }
        return value;
    }

    public void remove(String section) {
        this.sections.remove(section);
    }

    public void remove(String section, String key) {
        Section sectionObject = (Section) this.sections.get(section);
        if (sectionObject != null) {
            sectionObject.getValues().remove(key);
        }
    }

    public IniFile() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.line_separator = null;
        this.charSet = "UTF-8";
        this.sections = new LinkedHashMap();
        this.file = null;
        this.checked = false;
        this.checkOK = false;
    }

    public IniFile(File file) {
        this.line_separator = null;
        this.charSet = "UTF-8";
        this.sections = new LinkedHashMap();
        this.file = null;
        this.checked = false;
        this.checkOK = false;
        this.file = file;
        initFromFile(file);
    }

    public void init(Context context) {
        if (this.file.exists()) {
            String deviceID = LetvUtils.generateDeviceId(context);
            String appVersion = LetvUtils.getClientVersionName();
            LogInfo.log("letv.ini", "init deviceID :" + deviceID + "  appVersion :" + appVersion);
            if (!TextUtils.isEmpty(deviceID) && !TextUtils.isEmpty(appVersion)) {
                if ("5.9.6".equals(appVersion)) {
                    LogInfo.log("letv.ini", "5.9.6 写入加密数据");
                    set("config", RSA, MD5.toMd5(deviceID));
                    save();
                    return;
                }
                LogInfo.log("letv.ini", "非5.9.6 读取加密数据");
                return;
            }
            return;
        }
        LogInfo.log("letv.ini", "init file no exsits");
    }

    public boolean check(Context context) {
        if (this.checked) {
            return this.checkOK;
        }
        String deviceID = LetvUtils.generateDeviceId(context);
        Section section = get("config");
        if (section == null) {
            this.checked = true;
            return this.checkOK;
        }
        String rsa = (String) section.get(RSA);
        String md5 = MD5.toMd5(deviceID);
        if (TextUtils.isEmpty(rsa) || !md5.equals(rsa)) {
            LogInfo.log("letv.ini", "加密数据校验失败");
            this.checkOK = false;
        } else {
            LogInfo.log("letv.ini", "加密数据校验成功");
            this.checkOK = true;
        }
        this.checked = true;
        return this.checkOK;
    }

    public IniFile(InputStream inputStream) {
        this.line_separator = null;
        this.charSet = "UTF-8";
        this.sections = new LinkedHashMap();
        this.file = null;
        this.checked = false;
        this.checkOK = false;
        initFromInputStream(inputStream);
    }

    public void load(File file) {
        this.file = file;
        initFromFile(file);
    }

    public void load(InputStream inputStream) {
        initFromInputStream(inputStream);
    }

    public void save(OutputStream outputStream) {
        try {
            saveConfig(new BufferedWriter(new OutputStreamWriter(outputStream, this.charSet)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void save(File file) {
        try {
            saveConfig(new BufferedWriter(new FileWriter(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        save(this.file);
    }

    private void initFromInputStream(InputStream inputStream) {
        try {
            toIniFile(new BufferedReader(new InputStreamReader(inputStream, this.charSet)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void initFromFile(File file) {
        try {
            toIniFile(new BufferedReader(new FileReader(file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void toIniFile(BufferedReader bufferedReader) {
        IOException e;
        Pattern p = Pattern.compile("^\\[.*\\]$");
        Section section = null;
        while (true) {
            Section section2;
            try {
                String strLine = bufferedReader.readLine();
                if (strLine == null) {
                    bufferedReader.close();
                    section2 = section;
                    return;
                } else if (p.matcher(strLine).matches()) {
                    strLine = strLine.trim();
                    section2 = new Section(this);
                    try {
                        Section.access$002(section2, strLine.substring(1, strLine.length() - 1));
                        this.sections.put(Section.access$000(section2), section2);
                        section = section2;
                    } catch (IOException e2) {
                        e = e2;
                    }
                } else {
                    String[] keyValue = strLine.split(SearchCriteria.EQ);
                    if (keyValue.length == 2) {
                        section.set(keyValue[0], keyValue[1]);
                    }
                }
            } catch (IOException e3) {
                e = e3;
                section2 = section;
            }
        }
        e.printStackTrace();
    }

    private void saveConfig(BufferedWriter bufferedWriter) {
        boolean line_spe = false;
        try {
            if (this.line_separator == null || this.line_separator.trim().equals("")) {
                line_spe = false;
            }
            for (Section section : this.sections.values()) {
                if (!(section.getName().equals("") || section.getName() == null)) {
                    bufferedWriter.write("[" + section.getName() + "]");
                    if (line_spe) {
                        bufferedWriter.write(this.line_separator);
                    } else {
                        bufferedWriter.newLine();
                    }
                }
                for (Entry<String, Object> entry : section.getValues().entrySet()) {
                    bufferedWriter.write((String) entry.getKey());
                    bufferedWriter.write(SearchCriteria.EQ);
                    bufferedWriter.write(entry.getValue().toString());
                    if (line_spe) {
                        bufferedWriter.write(this.line_separator);
                    } else {
                        bufferedWriter.newLine();
                    }
                }
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
