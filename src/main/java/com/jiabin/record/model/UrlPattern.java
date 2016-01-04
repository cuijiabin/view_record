package com.jiabin.record.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * URL模式（使用正则表达式实现） 用正则表达式的方式来指定一组有共同页面布局的网页 这样就可以对这组页面指定一套模板来抽取信息
 * 
 * @ClassName: UrlPattern
 * @Description: URL模式（使用正则表达式实现）
 * @author cuijiabin
 * @date 2016年1月4日 下午5:24:54
 *
 */
public class UrlPattern {
	/**
	 * URL模式（使用正则表达式实现）
	 */
	private String urlPattern;
	/**
	 * URL模式（编译好的正则表达式）
	 */
	private Pattern regexPattern;
	/**
	 * 多个网页模板
	 */
	private List<HtmlTemplate> htmlTemplates = new ArrayList<>();

	public String getUrlPattern() {
		return urlPattern;
	}

	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
		try {
			regexPattern = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
		} catch (Exception e) {
			System.err.println("编译正则表达式[" + urlPattern + "]失败：");
			e.printStackTrace();
		}
	}

	public Pattern getRegexPattern() {
		return regexPattern;
	}

	public List<HtmlTemplate> getHtmlTemplates() {
		return htmlTemplates;
	}

	public void setHtmlTemplates(List<HtmlTemplate> htmlTemplates) {
		this.htmlTemplates = htmlTemplates;
		for (HtmlTemplate htmlTemplate : this.htmlTemplates) {
			htmlTemplate.setUrlPattern(this);
		}
	}

	public boolean hasHtmlTemplate() {
		return !htmlTemplates.isEmpty();
	}

	public void addHtmlTemplate(HtmlTemplate htmlTemplate) {
		htmlTemplates.add(htmlTemplate);
		htmlTemplate.setUrlPattern(this);
	}
}
