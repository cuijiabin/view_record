package com.jiabin.record.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.jiabin.extract.util.ExtractUtil;

/**
 * URL抽取规则
 * 初始化：1、从配置管理web服务器获取完整的规则集合 2、抽取规则 3、构造规则查找结构
 * 
 * @ClassName: ExtractRegular
 * @Description: URL抽取规则
 * @author cuijiabin
 * @date 2016年1月4日 下午6:18:30
 *
 */
public class ExtractRegular {

	private static Logger logger = Logger.getLogger(ExtractRegular.class);

	private static final ObjectMapper MAPPER = new ObjectMapper();

	private static ExtractRegular extractRegular = null;

	private volatile Map<String, List<UrlPattern>> urlPatternMap = null;

	// 私有构造函数
	private ExtractRegular() {}

	//获取抽取规则实例
	public static ExtractRegular getInstance(List<UrlPattern> urlPatterns) {
		if (extractRegular != null) {
			return extractRegular;
		}

		synchronized (ExtractRegular.class) {
			if (extractRegular == null) {
				extractRegular = new ExtractRegular();
				extractRegular.init(urlPatterns);
			}
		}
		return extractRegular;
	}

	
	//初始化： 构造抽取规则查找结构
	private synchronized void init(List<UrlPattern> urlPatterns) {
		logger.info("开始初始化URL抽取规则");
		Map<String, List<UrlPattern>> newUrlPatterns = ExtractUtil.toMap(urlPatterns);
		if (!newUrlPatterns.isEmpty()) {
			Map<String, List<UrlPattern>> oldUrlPatterns = urlPatternMap;
			urlPatternMap = newUrlPatterns;
			// 清空之前的抽取规则查找结构（如果有）
			if (oldUrlPatterns != null) {
				for (List<UrlPattern> list : oldUrlPatterns.values()) {
					list.clear();
				}
				oldUrlPatterns.clear();
			}
		}
		logger.info("完成初始化URL抽取规则");
	}

	//获取抽取规则实例
	public static ExtractRegular getInstance(String serverUrl, String redisHost, int redisPort) {
		if (extractRegular != null) {
			return extractRegular;
		}
		synchronized (ExtractRegular.class) {
			if (extractRegular == null) {
				extractRegular = new ExtractRegular();
				extractRegular.init(serverUrl);
			}
		}
		return extractRegular;
	}

	/**
	 * 初始化： 1、从配置管理web服务器获取完整的抽取规则的json表示 2、抽取json，构造对应的java对象结构
	 * 
	 * @param serverUrl 配置管理WEB服务器的抽取规则下载地址
	 */
	private synchronized void init(String serverUrl) {
		logger.info("开始下载URL抽取规则");
		logger.info("serverUrl: " + serverUrl);
		// 从配置管理web服务器获取完整的抽取规则
		String json = ExtractUtil.downJson(serverUrl);
		logger.info("完成下载URL抽取规则");
		// 抽取规则
		logger.info("开始解析URL抽取规则");
		List<UrlPattern> urlPatterns = ExtractUtil.parseJson(json, MAPPER);
		logger.info("完成解析URL抽取规则");
		init(urlPatterns);
	}

	//动态增加URL模式（批量）
	public void addUrlPatterns(List<UrlPattern> urlPatterns) {
		for (UrlPattern urlPattern : urlPatterns) {
			addUrlPattern(urlPattern);
		}
	}

	//动态增加URL模式（单个）
	public void addUrlPattern(UrlPattern urlPattern) {
		try {
			URL url = new URL(urlPattern.getUrlPattern());
			String key = ExtractUtil.urlPrefix(url);
			List<UrlPattern> value = urlPatternMap.get(key);
			if (value == null) {
				value = new ArrayList<>();
				urlPatternMap.put(key, value);
			}
			value.add(urlPattern);
		} catch (Exception e) {
			logger.error("URL规则添加失败：" + urlPattern.getUrlPattern(), e);
		}
	}

	public void removeUrlPattern(String urlPattern) {
		try {
			URL url = new URL(urlPattern);
			String key = ExtractUtil.urlPrefix(url);
			urlPatternMap.remove(key);
		} catch (Exception e) {
			logger.error("URL规则删除失败：" + urlPattern, e);
		}
	}

	//获取一个可以用来抽取特定URL的页面模板集合
	public List<HtmlTemplate> getHtmlTemplate(String urlString) {
		List<HtmlTemplate> pageTemplates = new ArrayList<>();
		if (urlPatternMap != null) {
			try {
				URL url = new URL(urlString);
				String key = ExtractUtil.urlPrefix(url);
				List<UrlPattern> patterns = urlPatternMap.get(key);
				for (UrlPattern urlPattern : patterns) {
					Matcher matcher = urlPattern.getRegexPattern().matcher(urlString);
					if (matcher.find()) {
						pageTemplates.addAll(urlPattern.getHtmlTemplates());
					}
				}
			} catch (Exception e) {
				logger.error("获取URL抽取规则失败：" + urlString, e);
			}
		}
		return pageTemplates;
	}

}
