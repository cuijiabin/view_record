package com.jiabin.record.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class KeyWordUtil {

	/**
	 * 解析搜索关键字
	 * 
	 * @Title: getKeyword
	 * @Description:
	 * @param @param url
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getKeyword(String url) {

		if (StringUtils.isBlank(url)) {
			return null;
		}
		String keywordReg = "(?:yahoo.+?[\\?|&]p=|openfind.+?query=|google.+?q=|lycos.+?query=|onseek.+?keyword="
				+ "|search\\.tom.+?word=|search\\.qq\\.com.+?word=|zhongsou\\.com.+?word=|search\\.msn\\.com.+?q="
				+ "|yisou\\.com.+?p=|sina.+?word=|sina.+?query=|sina.+?_searchkey=|sohu.+?word=|sohu.+?key_word=|sohu.+?query="
				+ "|163.+?q=|baidu.+?wd=|m.baidu.+?word=|soso.+?w=|haosou.+?q=|sogou.+?query=|3721\\.com.+?p=|Alltheweb.+?q=)([^&]*)";
		String encodeReg = "^(?:[\\x00-\\x7f]|[\\xfc-\\xff][\\x80-\\xbf]{5}|[\\xf8-\\xfb][\\x80-\\xbf]{4}|[\\xf0-\\xf7][\\x80-\\xbf]{3}|[\\xe0-\\xef][\\x80-\\xbf]{2}|[\\xc0-\\xdf][\\x80-\\xbf])+$";
		Pattern keywordPatt = Pattern.compile(keywordReg);
		StringBuffer keyword = new StringBuffer(20);
		Matcher keywordMat = keywordPatt.matcher(url);
		while (keywordMat.find()) {
			keywordMat.appendReplacement(keyword, "$1");
		}
		if (!keyword.toString().equals("")) {
			String keywordsTmp = keyword.toString().replace("http://www.", "");
			keywordsTmp = keywordsTmp.replace("https://www.", "");
			Pattern encodePatt = Pattern.compile(encodeReg);
			String unescapeString = unescape(keywordsTmp);
			Matcher encodeMat = encodePatt.matcher(unescapeString);
			String encodeString = "gbk";
			if (encodeMat.matches())
				encodeString = "utf-8";
			try {
				return URLDecoder.decode(keywordsTmp, encodeString);
			} catch (UnsupportedEncodingException e) {
				return "";
			}
		}
		return "";
	}

	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}
}
