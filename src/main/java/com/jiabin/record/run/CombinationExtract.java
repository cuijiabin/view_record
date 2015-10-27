package com.jiabin.record.run;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jiabin.record.util.ReadUtil;
import com.jiabin.record.util.SqlUtil;
import com.jiabin.record.util.TxtUtil;

public class CombinationExtract {

	public static void extractRun(String month) throws Exception {
		File outFile = new File("F:\\BaiduYunDownload\\网页浏览记录\\" + month + "月\\15-" + month + ".sql");
		List<String> list = ReadUtil.getFilePath(month);
		for (String path : list) {
			String[][] datas = ReadUtil.extractCsv(path);
			for (int i = 0; i < datas.length; i++) {
				String[] data = datas[i];
				String sql = SqlUtil.getVisitRecordSql(data);
				if (StringUtils.isBlank(sql)) {
					for (int j = 0; j < data.length; j++)
						System.out.print(data[j].toString() + "\t");
					continue;
				}
				TxtUtil.writeTxtFile(sql, outFile);
			}
		}
	}

	public static void extractKeys(String month) throws Exception {
		List<String> list = ReadUtil.getFilePath(month);
		for (String path : list) {
			String[][] datas = ReadUtil.extractCsv(path);
			for (int i = 0; i < datas.length; i++) {
				String[] data = datas[i];
				SqlUtil.getVisitRecordKeys(data);
			}
		}
	}
}
