package com.jiabin.record.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.jiabin.record.model.CssPath;
import com.jiabin.record.model.ExtractFailLog;
import com.jiabin.record.model.ExtractRegular;
import com.jiabin.record.model.ExtractResult;
import com.jiabin.record.model.ExtractResultItem;
import com.jiabin.record.model.HtmlTemplate;
import com.jiabin.record.model.UrlPattern;
import com.jiabin.record.service.HtmlExtractor;
import com.jiabin.record.service.HtmlFetcher;
import com.jiabin.record.service.impl.DefaultHtmlExtractor;
import com.jiabin.record.service.impl.JSoupHtmlFetcher;
import com.jiabin.record.service.impl.SeleniumHtmlFetcher;

public class HtmlExtractorTest {

	@Test
	public void usage() {
		
		HtmlFetcher htmlFetcher = new JSoupHtmlFetcher();
		// 1、构造抽取规则
		List<UrlPattern> urlPatterns = new ArrayList<>();
		// 1.1、构造URL模式
		UrlPattern urlPattern = new UrlPattern();
		urlPattern.setUrlPattern("http://money.163.com/\\d{2}/\\d{4}/\\d{2}/[0-9A-Z]{16}.html");
		// 1.2、构造HTML模板
		HtmlTemplate htmlTemplate = new HtmlTemplate();
		htmlTemplate.setTemplateName("网易财经频道");
		htmlTemplate.setTableName("finance");
		// 1.3、将URL模式和HTML模板建立关联
		urlPattern.addHtmlTemplate(htmlTemplate);
		// 1.4、构造CSS路径
		CssPath cssPath = new CssPath();
		cssPath.setCssPath("h1");
		cssPath.setFieldName("title");
		cssPath.setFieldDescription("标题");
		// 1.5、将CSS路径和模板建立关联
		htmlTemplate.addCssPath(cssPath);
		// 1.6、构造CSS路径
		cssPath = new CssPath();
		cssPath.setCssPath("div#endText");
		cssPath.setFieldName("content");
		cssPath.setFieldDescription("正文");
		// 1.7、将CSS路径和模板建立关联
		htmlTemplate.addCssPath(cssPath);
		// 可象上面那样构造多个URLURL模式
		urlPatterns.add(urlPattern);
		// 2、获取抽取规则对象
		ExtractRegular extractRegular = ExtractRegular.getInstance(urlPatterns);
		// 3、获取HTML抽取工具
		HtmlExtractor htmlExtractor = new DefaultHtmlExtractor(extractRegular);
		// 4、抽取网页
		String url = "http://money.163.com/16/0104/16/BCGEUL0400254IU1.html";
		String html = htmlFetcher.fetch(url);
		List<ExtractResult> extractResults = htmlExtractor.extract(url, html);
		// 5、输出结果
		int i = 1;
		for (ExtractResult extractResult : extractResults) {
			System.out.println((i++) + "、网页 " + extractResult.getUrl() + " 的抽取结果");
			if (!extractResult.isSuccess()) {
				System.out.println("抽取失败：");
				for (ExtractFailLog extractFailLog : extractResult.getExtractFailLogs()) {
					System.out.println("\turl:" + extractFailLog.getUrl());
					System.out.println("\turlPattern:" + extractFailLog.getUrlPattern());
					System.out.println("\ttemplateName:" + extractFailLog.getTemplateName());
					System.out.println("\tfieldName:" + extractFailLog.getFieldName());
					System.out.println("\tfieldDescription:" + extractFailLog.getFieldDescription());
					System.out.println("\tcssPath:" + extractFailLog.getCssPath());
					if (extractFailLog.getExtractExpression() != null) {
						System.out.println("\textractExpression:" + extractFailLog.getExtractExpression());
					}
				}
				continue;
			}
			Map<String, List<ExtractResultItem>> extractResultItems = extractResult.getExtractResultItems();
			for (String field : extractResultItems.keySet()) {
				List<ExtractResultItem> values = extractResultItems.get(field);
				if (values.size() > 1) {
					int j = 1;
					System.out.println("\t多值字段:" + field);
					for (ExtractResultItem item : values) {
						System.out.println("\t\t" + (j++) + "、" + field + " = " + item.getValue());
					}
				} else {
					System.out.println("\t" + field + " = " + values.get(0).getValue());
				}
			}
			System.out.println("\tdescription = " + extractResult.getDescription());
			System.out.println("\tkeywords = " + extractResult.getKeywords());
		}
	}
	
	@Test
	public void usage3() {
		HtmlFetcher htmlFetcher = new SeleniumHtmlFetcher();
		// 1、构造抽取规则
		List<UrlPattern> urlPatterns = new ArrayList<>();
		// 1.1、构造URL模式
		UrlPattern urlPattern = new UrlPattern();
		urlPattern.setUrlPattern("http://list.jd.com/list.html\\?cat=([\\d,]+)");
		// 1.2、构造HTML模板
		HtmlTemplate htmlTemplate = new HtmlTemplate();
		htmlTemplate.setTemplateName("京东商品");
		htmlTemplate.setTableName("jd_goods");
		// 1.3、将URL模式和HTML模板建立关联
		urlPattern.addHtmlTemplate(htmlTemplate);
		// 1.4、构造CSS路径
		CssPath cssPath = new CssPath();
		cssPath.setCssPath("html body div div div ul li div div.p-name");
		cssPath.setFieldName("name");
		cssPath.setFieldDescription("名称");
		// 1.5、将CSS路径和模板建立关联
		htmlTemplate.addCssPath(cssPath);
		// 1.6、构造CSS路径
		cssPath = new CssPath();
		cssPath.setCssPath("html body div div div ul li div div.p-name a");
		cssPath.setAttr("href");
		cssPath.setFieldName("link");
		cssPath.setFieldDescription("链接");
		// 1.7、将CSS路径和模板建立关联
		htmlTemplate.addCssPath(cssPath);
		// 1.8、构造CSS路径
		cssPath = new CssPath();
		cssPath.setCssPath("html body div div div ul li div div.p-price strong");
		cssPath.setFieldName("price");
		cssPath.setFieldDescription("价格");
		// 1.9、将CSS路径和模板建立关联
		htmlTemplate.addCssPath(cssPath);
		// 可象上面那样构造多个URLURL模式
		urlPatterns.add(urlPattern);
		// 2、获取抽取规则对象
		ExtractRegular extractRegular = ExtractRegular.getInstance(urlPatterns);
		// 注意：可通过如下3个方法动态地改变抽取规则
		// extractRegular.addUrlPatterns(urlPatterns);
		// extractRegular.addUrlPattern(urlPattern);
		// extractRegular.removeUrlPattern(urlPattern.getUrlPattern());
		// 3、获取HTML抽取工具
		HtmlExtractor htmlExtractor = new DefaultHtmlExtractor(extractRegular);
		// 4、抽取网页
		String url = "http://list.jd.com/list.html?cat=670,671,672";
		String html = htmlFetcher.fetch(url);
		List<ExtractResult> extractResults = htmlExtractor.extract(url, html);
		// 5、输出结果
		int i = 1;
		for (ExtractResult extractResult : extractResults) {
			System.out.println((i++) + "、网页 " + extractResult.getUrl() + " 的抽取结果");
			if (!extractResult.isSuccess()) {
				System.out.println("抽取失败：");
				for (ExtractFailLog extractFailLog : extractResult.getExtractFailLogs()) {
					System.out.println("\turl:" + extractFailLog.getUrl());
					System.out.println("\turlPattern:" + extractFailLog.getUrlPattern());
					System.out.println("\ttemplateName:" + extractFailLog.getTemplateName());
					System.out.println("\tfieldName:" + extractFailLog.getFieldName());
					System.out.println("\tfieldDescription:" + extractFailLog.getFieldDescription());
					System.out.println("\tcssPath:" + extractFailLog.getCssPath());
					if (extractFailLog.getExtractExpression() != null) {
						System.out.println("\textractExpression:" + extractFailLog.getExtractExpression());
					}
				}
				continue;
			}
			Map<String, List<ExtractResultItem>> extractResultItems = extractResult.getExtractResultItems();
			for (String field : extractResultItems.keySet()) {
				List<ExtractResultItem> values = extractResultItems.get(field);
				if (values.size() > 1) {
					int j = 1;
					System.out.println("\t多值字段:" + field);
					for (ExtractResultItem item : values) {
						System.out.println("\t\t" + (j++) + "、" + field + " = " + item.getValue());
					}
				} else {
					System.out.println("\t" + field + " = " + values.get(0).getValue());
				}
			}
			System.out.println("\tdescription = " + extractResult.getDescription());
			System.out.println("\tkeywords = " + extractResult.getKeywords());
		}
	}
}
