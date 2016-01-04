package com.jiabin.record.service.impl;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import com.jiabin.record.service.HtmlFetcher;

import java.net.URL;

/**
 * 
 * @ClassName: JSoupHtmlFetcher 
 * @Description: 使用JSoup获取网页内容
 * @author cuijiabin 
 * @date 2016年1月4日 下午5:34:08 
 *
 */

public class JSoupHtmlFetcher implements HtmlFetcher {
	
	private static Logger logger = Logger.getLogger(JSoupHtmlFetcher.class);

    private static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    private static final String ENCODING = "gzip, deflate";
    private static final String LANGUAGE = "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3";
    private static final String CONNECTION = "keep-alive";
    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:36.0) Gecko/20100101 Firefox/36.0";

    @Override
    public String fetch(String url) {
        try {
        	logger.info("url:" + url);
            String host = new URL(url).getHost();
            Connection conn = Jsoup.connect(url)
                    .header("Accept", ACCEPT)
                    .header("Accept-Encoding", ENCODING)
                    .header("Accept-Language", LANGUAGE)
                    .header("Connection", CONNECTION)
                    .header("Referer", "http://"+host)
                    .header("Host", host)
                    .header("User-Agent", USER_AGENT)
                    .ignoreContentType(true);
            String html = conn.get().html();
            logger.debug("html:" + html);
            return html;
        }catch (Exception e){
        	logger.error("获取URL：" + url + "页面出错", e);
        }
        return "";
    }
    
}
