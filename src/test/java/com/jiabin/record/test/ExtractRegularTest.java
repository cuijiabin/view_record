package com.jiabin.record.test;

import java.util.List;

import org.junit.Test;

import com.jiabin.record.model.ExtractRegular;
import com.jiabin.record.model.HtmlTemplate;

public class ExtractRegularTest {

	@Test
	public void testExtractRegular(){
		ExtractRegular extractRegular = ExtractRegular.getInstance("http://localhost:8080/api/all_extract_regular.jsp", null, -1);

		List<HtmlTemplate> pageTemplates = extractRegular.getHtmlTemplate("http://money.163.com/14/0529/19/9TEGPK5T00252G50.html");
		for (HtmlTemplate pageTemplate : pageTemplates) {
			System.out.println(pageTemplate);
		}

		pageTemplates = extractRegular.getHtmlTemplate("http://finance.qq.com/a/20140530/004254.htm");
		for (HtmlTemplate pageTemplate : pageTemplates) {
			System.out.println(pageTemplate);
		}
	}
}
