package com.hfc.spidernest.utils.httpclients;

import com.hfc.spidernest.utils.Constant;
import com.hfc.spidernest.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;

/**
 * Created by user-hfc on 2019/1/20.
 */
public class ExtractUtil {

    /**
     * 从用户的url中提取用户的id，用户唯一ID可能是数字也可能是字符串
     * @param src 类似https://www.douban.com/people/666/
     * @return 类似666
     */
    public static String extractUserId(String src) {
        if (StringUtils.isNotEmpty(src)) {
            Matcher m = Constant.USER_URL_REGEX.matcher(src);
            if (m.find()) {
                return m.group(3);
            }
        }
        return StringUtil.blank();
    }

    /**
     * 从主题帖的url中提取主题帖的id
     * @param src 类似https://www.douban.com/group/topic/666/
     * @return 类似666
     */
    public static String extractTopicId(String src) {
        if (StringUtils.isNotEmpty(src)) {
            Matcher m = Constant.TOPIC_URL_REGEX.matcher(src);
            if (m.find()) {
                return m.group(3);
            }
        }
        return StringUtil.blank();
    }
}
