package com.jiabin.record.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ReadUtil {
	public static String[][] extractCsv(String csvFilePath) throws Exception {

		CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName("GBK"));

		List<String[]> list = new ArrayList<String[]>();
		while (reader.readRecord()) {
			list.add(reader.getValues());
		}
		String[][] datas = new String[list.size()][];
		for (int i = 0; i < list.size(); i++) {
			datas[i] = list.get(i);
		}

		return datas;
	}

	public static List<String> getFilePath(String month) {
		String inputFilePath = "F:\\BaiduYunDownload\\网页浏览记录\\" + month + "月\\15-" + month + "-";
		List<String> list = new ArrayList<String>();
		for (int i = 0; i <= 31; i++) {
			String fileName = null;
			if (i < 10) {
				fileName = inputFilePath + 0 + i + ".csv";
			} else {
				fileName = inputFilePath + i + ".csv";
			}
			try {
				new InputStreamReader(new FileInputStream(fileName), "GBK");
			} catch (UnsupportedEncodingException | FileNotFoundException e) {
				//System.out.println("没有找到" + fileName);
				continue;
			}
			list.add(fileName);
		}
		return list;
	}

}
