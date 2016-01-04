package com.jiabin.record.service.impl;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.jiabin.record.service.HtmlFetcher;

/**
 * 
 * @ClassName: SeleniumHtmlFetcher 
 * @Description: 使用selenium执行JS动态渲染网页获取页面内容 
 * @author cuijiabin 
 * @date 2016年1月4日 下午5:49:55 
 *
 */
public class SeleniumHtmlFetcher implements HtmlFetcher {
	
	private static Logger logger = Logger.getLogger(SeleniumHtmlFetcher.class);

    //火狐浏览器
    private static final WebDriver WEB_DRIVER = new FirefoxDriver();

    /**
     * 使用HtmlUnit获取页面内容，HtmlUnit能执行JS，动态渲染网页，但不是所有JS都能渲染，需要测试
     * @param url html页面路径
     * @return
     */
    @Override
    public String fetch(String url) {
        try{
        	logger.info("url:" + url);
            WEB_DRIVER.get(url);
            String html = WEB_DRIVER.getPageSource();
            logger.debug("html:" + html);
            return html;
        }catch (Exception e) {
        	logger.error("获取URL：" + url + "页面出错", e);
        }
        return "";
    }
    
}
