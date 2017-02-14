package com.letv.lepaysdk.utils;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import java.util.Calendar;
import java.util.regex.Pattern;

public class StringUtil {
    public static boolean hasDecimalNumber(String number) {
        return Pattern.compile("^([1-9]+[0-9]*|0)(\\.[\\d]+)?$").matcher(number).find();
    }

    public static boolean hasNumber(String number) {
        return Pattern.compile("^[0-9]*$").matcher(number).find();
    }

    public static boolean hasMobile(String mobiles) {
        return Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$").matcher(mobiles).matches();
    }

    public static boolean hasValidVehicleNum(String vehicleNum) {
        return Pattern.compile("^[一-龥]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9一-龥]{1}$").matcher(vehicleNum).matches();
    }

    public static boolean hasCardNo(String mobiles) {
        return Pattern.compile("(^d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)").matcher(mobiles).matches();
    }

    public static SpannableStringBuilder textSpan(Context context, String textResId, String subText, String colorResId, String fontSizeResId, int start) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(context.getString(ResourceUtil.getStringResource(context, textResId), new Object[]{subText}));
        spannableStringBuilder.setSpan(new ForegroundColorSpan(context.getResources().getColor(ResourceUtil.getColorResource(context, colorResId))), start, subText.length() + start, 34);
        if (fontSizeResId != null) {
            spannableStringBuilder.setSpan(new AbsoluteSizeSpan((int) context.getResources().getDimension(ResourceUtil.getDimenResource(context, fontSizeResId))), start, subText.length() + start, 33);
        }
        return spannableStringBuilder;
    }

    public static int getYear() {
        return Calendar.getInstance().get(1);
    }

    public static int getSuffixYear() {
        String years = String.valueOf(getYear());
        return Integer.valueOf(years.substring(2, years.length())).intValue();
    }
}
