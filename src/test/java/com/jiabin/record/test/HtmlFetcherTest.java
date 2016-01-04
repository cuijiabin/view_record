package com.jiabin.record.test;

import org.junit.Test;

import com.jiabin.record.service.impl.HtmlUnitHtmlFetcher;
import com.jiabin.record.service.impl.JSoupHtmlFetcher;
import com.jiabin.record.service.impl.SeleniumHtmlFetcher;

public class HtmlFetcherTest {

	@Test
	public void testHtmlUnitHtmlFetcher(){
		HtmlUnitHtmlFetcher fetcher = new HtmlUnitHtmlFetcher();
		fetcher.fetch("http://baike.baidu.com/subview/92173/16416553.htm");
	}
	
	@Test
	public void testJSoupHtmlFetcher(){
		JSoupHtmlFetcher fetcher = new JSoupHtmlFetcher();
		String html = fetcher.fetch("http://baike.baidu.com/subview/92173/16416553.htm");
		System.out.println("html:"+html);
	}
	
	@Test
	public void testSeleniumHtmlFetcher(){
		SeleniumHtmlFetcher fetcher = new SeleniumHtmlFetcher();
		fetcher.fetch("http://www.cnblogs.com/zyw-205520/p/3421687.html");
	}
}
