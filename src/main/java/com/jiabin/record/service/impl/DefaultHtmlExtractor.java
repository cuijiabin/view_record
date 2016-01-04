package com.jiabin.record.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jiabin.extract.util.ExtractFunctionExecutor;
import com.jiabin.record.model.CssPath;
import com.jiabin.record.model.ExtractFailLog;
import com.jiabin.record.model.ExtractFunction;
import com.jiabin.record.model.ExtractRegular;
import com.jiabin.record.model.ExtractResult;
import com.jiabin.record.model.ExtractResultItem;
import com.jiabin.record.model.HtmlTemplate;
import com.jiabin.record.service.HtmlExtractor;

import java.util.*;

/**
 * @ClassName: DefaultHtmlExtractor 
 * @Description: 网页抽取工具 根据URL模式、页面模板、CSS路径、抽取函数，抽取HTML页面 
 * @author cuijiabin 
 * @date 2016年1月4日 下午6:23:05 
 *
 */
public class DefaultHtmlExtractor implements HtmlExtractor {

	private static Logger logger = Logger.getLogger(DefaultHtmlExtractor.class);
	private ExtractRegular extractRegular;

	public DefaultHtmlExtractor(ExtractRegular extractRegular) {
		this.extractRegular = extractRegular;
	}

	/**
	 * 抽取信息
	 * 
	 * @param url
	 *            URL
	 * @param html
	 *            HTML
	 * @return 抽取结果
	 */
	@Override
	public List<ExtractResult> extract(String url, String html) {
		List<ExtractResult> extractResults = new ArrayList<>();
		// 同一个URL可能会有多个页面模板
		List<HtmlTemplate> htmlTemplates = extractRegular.getHtmlTemplate(url);
		if (htmlTemplates.isEmpty()) {
			logger.debug("没有为此URL指定模板：" + url);
			return extractResults;
		}
		try {
			Document doc = Jsoup.parse(html);
			Elements metas = doc.select("meta");
			String keywords = "";
			String description = "";
			for (Element meta : metas) {
				String name = meta.attr("name");
				if ("keywords".equals(name)) {
					keywords = meta.attr("content");
				}
				if ("description".equals(name)) {
					description = meta.attr("content");
				}
			}
			Set<String> tableNames = new HashSet<>();
			for (HtmlTemplate htmlTemplate : htmlTemplates) {
				if (tableNames.contains(htmlTemplate.getTableName())) {
					logger.debug("多个模板定义的tableName重复，这有可能会导致数据丢失，检查UrlPattern下定义的模板：" + htmlTemplate.getUrlPattern().getUrlPattern());
					logger.debug(htmlTemplates.toString());
				}
				tableNames.add(htmlTemplate.getTableName());
				try {
					// 按页面模板的定义对网页进行抽取
					ExtractResult extractResult = extractHtmlTemplate(url, htmlTemplate, doc);
					// 同样的URL模式，因为改版等原因，可能会对应多套模板，多套模板中我们一般只需要抽取一套
					if (!extractResult.getExtractFailLogs().isEmpty() || !extractResult.getExtractResultItems().isEmpty()) {
						extractResult.setContent(html.getBytes("utf-8"));
						extractResult.setEncoding("utf-8");
						extractResult.setKeywords(keywords);
						extractResult.setDescription(description);
						extractResults.add(extractResult);
					} else {
						logger.debug(url + " 的模板 " + htmlTemplate.getTemplateName() + " 未抽取到");
					}
				} catch (Exception e) {
					logger.error("页面模板抽取失败：" + htmlTemplate.getTemplateName(), e);
				}
			}
		} catch (Exception e) {
			logger.error("抽取网页出错: " + url, e);
		}
		return extractResults;
	}

	/**
	 * 根据模板的定义抽取信息
	 * 
	 * @param url
	 *            html页面路径
	 * @param htmlTemplate
	 *            html页面模板
	 * @param doc
	 *            jsoup文档
	 * @return 抽取结果
	 */
	private ExtractResult extractHtmlTemplate(String url, HtmlTemplate htmlTemplate, Document doc) {
		// 一个页面模板对应一个抽取结果
		ExtractResult extractResult = new ExtractResult();
		extractResult.setUrl(url);
		extractResult.setTableName(htmlTemplate.getTableName());
		List<CssPath> cssPaths = htmlTemplate.getCssPaths();
		// 页面模板中定义的所有CSS路径和抽取表达式全部抽取成功，才算抽取成功
		// 只要有一个CSS路径或抽取表达式失败，就是抽取失败
		for (CssPath cssPath : cssPaths) {
			// 抽取一条CSS PATH
			Elements elements = doc.select(cssPath.getCssPath());
			// 如果CSS路径匹配多个元素，则抽取字段为多值
			for (Element element : elements) {
				String text = null;
				if (StringUtils.isBlank(cssPath.getAttr())) {
					// 提取文本
					text = element.text();
				} else {
					// 提取属性
					text = element.attr(cssPath.getAttr());
				}
				if (StringUtils.isNotBlank(text)) {
					// 成功提取文本
					if (cssPath.hasExtractFunction()) {
						// 使用CSS路径下的抽取函数做进一步抽取
						for (ExtractFunction pf : cssPath.getExtractFunctions()) {
							text = ExtractFunctionExecutor.execute(text, doc, cssPath, pf.getExtractExpression());
							if (text != null) {
								ExtractResultItem extractResultItem = new ExtractResultItem();
								extractResultItem.setField(pf.getFieldName());
								extractResultItem.setValue(text);
								extractResult.addExtractResultItem(extractResultItem);
							} else {
								ExtractFailLog extractFailLog = new ExtractFailLog();
								extractFailLog.setUrl(url);
								extractFailLog.setUrlPattern(htmlTemplate.getUrlPattern().getUrlPattern());
								extractFailLog.setTemplateName(htmlTemplate.getTemplateName());
								extractFailLog.setCssPath(cssPath.getCssPath());
								extractFailLog.setExtractExpression(pf.getExtractExpression());
								extractFailLog.setTableName(htmlTemplate.getTableName());
								extractFailLog.setFieldName(pf.getFieldName());
								extractFailLog.setFieldDescription(pf.getFieldDescription());
								extractResult.addExtractFailLog(extractFailLog);
								// 未抽取到结果，保存抽取失败日志并停止抽取，抽取失败
								// 快速失败模式
								// 如果要记录所有失败日志，则去除下面一行返回的代码
								return extractResult;
							}
						}
					} else {
						// 使用CSS路径抽取的结果
						ExtractResultItem extractResultItem = new ExtractResultItem();
						extractResultItem.setField(cssPath.getFieldName());
						extractResultItem.setValue(text);
						extractResult.addExtractResultItem(extractResultItem);
					}
				} else {
					// 未抽取到结果，保存抽取失败日志并停止抽取，抽取失败
					ExtractFailLog extractFailLog = new ExtractFailLog();
					extractFailLog.setUrl(url);
					extractFailLog.setUrlPattern(htmlTemplate.getUrlPattern().getUrlPattern());
					extractFailLog.setTemplateName(htmlTemplate.getTemplateName());
					extractFailLog.setCssPath(cssPath.getCssPath());
					extractFailLog.setExtractExpression("");
					extractFailLog.setTableName(htmlTemplate.getTableName());
					extractFailLog.setFieldName(cssPath.getFieldName());
					extractFailLog.setFieldDescription(cssPath.getFieldDescription());
					extractResult.addExtractFailLog(extractFailLog);
					// 未抽取到结果，保存抽取失败日志并停止抽取，抽取失败
					// 快速失败模式
					// 如果要记录所有失败日志，则去除下面一行返回的代码
					return extractResult;
				}
			}
		}
		return extractResult;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 下面的三种方法代表了3种不同的使用模式，只能单独使用
	}
}
