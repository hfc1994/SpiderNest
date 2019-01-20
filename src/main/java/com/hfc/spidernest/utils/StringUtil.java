package com.hfc.spidernest.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by user-hfc on 2019/1/14.
 */
public class StringUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtil.class.getName());

    /**
     * 处理内容，以免带有emoji的内容无法写入到数据库中
     * @param str 可能带有未被编码的emoji
     * @return
     */
    public static String encodeToUtf8(String str) {
        String newStr = null;
        try {
            newStr = URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            newStr = "";
            LOGGER.warn("str encode fail");
        }
        return newStr;
    }

    /**
     * 处理内容，以免带有emoji的内容不能正常显示
     * @param str 可能带有被编码的emoji
     */
    public static String decodeFromUtf8(String str) {
        String newStr = null;
        try {
            newStr = URLDecoder.decode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            newStr = "";
            LOGGER.warn("str decode fail");
        }
        return newStr;
    }

    /**
     * @return 返回一个空字符串
     */
    public static String blank() {
        return "";
    }
}
