package com.jiabin.record.service.impl;

import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.jiabin.record.service.HtmlFetcher;


/**
 * 使用HtmlUnit获取页面内容，HtmlUnit能执行JS 动态渲染网页，但不是所有JS都能渲染，需要测试
 * 
 * @ClassName: HtmlUnitHtmlFetcher 
 * @Description: 使用HtmlUnit获取页面内容
 * @author cuijiabin 
 * @date 2016年1月4日 下午5:34:08 
 *
 */
public class HtmlUnitHtmlFetcher implements HtmlFetcher {
	
	private static Logger logger = Logger.getLogger(HtmlUnitHtmlFetcher.class);
	
	private static final WebClient WEB_CLIENT = new WebClient(BrowserVersion.INTERNET_EXPLORER_11);

	/**
	 * 使用HtmlUnit获取页面内容，HtmlUnit能执行JS，动态渲染网页，但不是所有JS都能渲染，需要测试
	 * 
	 * @param url html页面路径
	 * @return
	 */
	@Override
	public String fetch(String url) {
		try {
			logger.info("url:" + url);
			HtmlPage htmlPage = WEB_CLIENT.getPage(url);
			String html = htmlPage.getBody().asXml();
			logger.debug("html:" + html);
			return html;
		} catch (Exception e) {
			logger.error("获取URL：" + url + "页面出错", e);
		}
		return "";
	}
	
}
