package com.dong.yiping.utils;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bei on 2015/7/15.
 */
public class FormatUtils {

    /**
     * phone
     */
    public static boolean isMobileNO(String mobiles) {
		/*
		 * ：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * ：130、131、132、152、155、156、185、186
		 * ：133、153、180、189、（1349）
		 *
		 */
        String telRegex = "[1][34578]\\d{9}";
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * eamil
     * @param post
     * @return
     */
    public static boolean isZip(String post) {
        if (post.matches("[1-9]\\d{5}(?!\\d)")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")) {
            return true;
        } else {
            return false;
        }
    }

    public static String getMD5(String paramString) {
        String returnStr;
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramString.getBytes());
            returnStr = byteToHexString(localMessageDigest.digest());
            return returnStr;
        } catch (Exception e) {
            return paramString;
        }
    }
    /**
     * 将指定byte数组转换成16进制字符串
     *
     * @param b
     * @return
     */
    public static String byteToHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toLowerCase());
        }
        return hexString.toString();
    }

    //时间戳转时间格式
    public static String timestampToDatetime(String seconds){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = "";
        try {
            time = simpleDateFormat.format(new Date(Long.valueOf(seconds+"000")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }


    //时间格式转时间戳
    public static long datetimeToTimestamp(String time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long timestamp = 0L;
        try {
            timestamp = simpleDateFormat.parse(time).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd HH:mm:ss
     */

    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
}
