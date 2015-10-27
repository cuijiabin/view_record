package com.jiabin.record.util;

import org.apache.commons.lang.StringUtils;

public class SqlUtil {
	private static String visitRecordSqlTemplate = "INSERT INTO `visit_record` (`url`, `title`, `visit_time`, `visit_count`, `visit_from`, `web_browser`, `url_length`) VALUES (";
	private static String newline = System.getProperty("line.separator");

	public static String getVisitRecordSql(String[] row) {
		StringBuffer strBuff = new StringBuffer(visitRecordSqlTemplate);
		String url = row[0];
		if (StringUtils.isBlank(url)) {
			return null;
		}
		url = url.replaceAll("'", "").replaceAll("\\\\", "");
		String title = row[1];
		title = title.replaceAll("'", "").replaceAll("\\\\", "");
		if (title.contains("'")) {
			System.out.println(title);
		}

		String visitTime = row[2];
		visitTime = visitTime.replaceAll("'", "");
		String visitCount = row[3];
		if (!StringUtils.isNumeric(visitCount)) {
			return null;
		}

		String visitFrom = row[4];
		visitFrom = visitFrom.replaceAll("'", "").replaceAll("\\\\", "");
		String webBrowser = row[5];
		webBrowser = webBrowser.replaceAll("'", "");
		String urlLength = row[8];
		if (!StringUtils.isNumeric(urlLength)) {
			return null;
		}

		strBuff.append("'").append(url).append("',");
		strBuff.append("'").append(title).append("',");
		strBuff.append("'").append(visitTime).append("',");
		strBuff.append(visitCount).append(",");
		strBuff.append("'").append(visitFrom).append("',");
		strBuff.append("'").append(webBrowser).append("',");
		strBuff.append(urlLength).append(");").append(newline);
		return strBuff.toString();
	}

	public static void getVisitRecordKeys(String[] row) {
		String url = row[0];
		if (StringUtils.isBlank(url)) {
			return;
		}
		url = url.replaceAll("'", "").replaceAll("\\\\", "");
		String visitTime = row[2];
		visitTime = visitTime.replaceAll("'", "");
		String keyWord = KeyWordUtil.getKeyword(url);
		if (!StringUtils.isBlank(keyWord)) {
			System.out.println("日期：" + visitTime + " 关键字：" + keyWord);
		}

		return;
	}

}
