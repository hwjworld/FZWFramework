/**
 * 
 */
package com.fzw.utils;

import java.text.ParseException;
import java.util.Date;

/**
 * @author hwj
 * 
 */
public class DateUtil extends org.apache.commons.lang.time.DateUtils {
	private static String[] parsePatterns = { "yyyy-MM-dd HH:mm:ss.SSS",
			"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH",
			"yyyy-MM-dd", "MM-dd HH:mm", "MM-dd", "yyyyMMdd HH:mm:ss",
			"yyyyMMdd HH:mm", "yyyyMMdd HH", "yyyyMMdd", "MMdd HH:mm", "MMdd",
			"yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM/dd HH",
			"yyyy/MM/dd", "MM/dd HH:mm", "MM/dd", "yyyy年MM月dd日 HH时mm分ss秒",
			"yyyy年MM月dd日 HH:mm:ss", "yyyy年MM月dd日 HH:mm", "yyyy年MM月dd日 HH",
			"MM月dd日 HH:mm:ss", "MM月dd日 HH:mm", "MM月dd日" };

    public static Date parseDate(String str) throws ParseException {
    	return org.apache.commons.lang.time.DateUtils.parseDate(str, parsePatterns);
    }
}
