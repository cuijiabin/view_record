package com.jiabin.extract.util;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.jiabin.record.model.CssPath;
import com.jiabin.record.model.ExtractFunction;
import com.jiabin.record.model.HtmlTemplate;
import com.jiabin.record.model.UrlPattern;

public class ExtractUtil {

	// 多个url模式可能会有相同的url前缀 map结构+url前缀定位 用于快速查找一个url需要匹配的模式
	public static Map<String, List<UrlPattern>> toMap(List<UrlPattern> urlPatterns) {
		Map<String, List<UrlPattern>> map = new ConcurrentHashMap<String, List<UrlPattern>>();
		for (UrlPattern urlPattern : urlPatterns) {
			try {
				URL url = new URL(urlPattern.getUrlPattern());
				String key = urlPrefix(url);
				List<UrlPattern> value = map.get(key);
				if (value == null) {
					value = new ArrayList<>();
					map.put(key, value);
				}
				value.add(urlPattern);
			} catch (Exception e) {
				System.out.println("URL规则初始化失败：" + urlPattern.getUrlPattern());
				e.printStackTrace();
			}
		}
		return map;
	}

	// 获取一个url的前缀表示，用于快速定位URL模式 规则为： 协议+域名（去掉.)+端口（可选）
	public static String urlPrefix(URL url) {
		StringBuilder result = new StringBuilder();
		result.append(url.getProtocol());
		String[] splits = StringUtils.split(url.getHost(), '.');
		if (splits.length > 0) {
			for (String split : splits) {
				result.append(split);
			}
		}
		if (url.getPort() > -1) {
			result.append(Integer.toString(url.getPort()));
		}
		return result.toString();
	}

	// 从配置管理WEB服务器下载规则（json表示）
	public static String downJson(String url) {
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		// 创建GET方法的实例
		GetMethod method = new GetMethod(url);
		try {
			// 执行GetMethod
			int statusCode = httpClient.executeMethod(method);
			System.out.println("响应代码：" + statusCode);
			if (statusCode != HttpStatus.SC_OK) {
				System.out.println("请求失败: " + method.getStatusLine());
			}
			// 读取内容
			String responseBody = new String(method.getResponseBody(), "utf-8");
			return responseBody;
		} catch (IOException e) {
			System.out.println("检查请求的路径：" + url);
			e.printStackTrace();
		} finally {
			// 释放连接
			method.releaseConnection();
		}
		return "";
	}

	/**
	 * 将json格式的URL模式转换为JAVA对象表示
	 *
	 * @param json
	 *            URL模式的JSON表示
	 * @return URL模式的JAVA对象表示
	 */
	public static List<UrlPattern> parseJson(String json, ObjectMapper Mapper) {
		
		List<UrlPattern> urlPatterns = new ArrayList<>();
		try {
			
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> ups = Mapper.readValue(json, List.class);
			
			for (Map<String, Object> up : ups) {
				UrlPattern urlPattern = new UrlPattern();
				urlPatterns.add(urlPattern);
				urlPattern.setUrlPattern(up.get("urlPattern").toString());
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> pageTemplates = (List<Map<String, Object>>) up.get("pageTemplates");
				
				for (Map<String, Object> pt : pageTemplates) {
					HtmlTemplate htmlTemplate = new HtmlTemplate();
					urlPattern.addHtmlTemplate(htmlTemplate);
					htmlTemplate.setTemplateName(pt.get("templateName").toString());
					htmlTemplate.setTableName(pt.get("tableName").toString());
					@SuppressWarnings("unchecked")
					List<Map<String, Object>> cssPaths = (List<Map<String, Object>>) pt.get("cssPaths");
					
					for (Map<String, Object> cp : cssPaths) {
						CssPath cssPath = new CssPath();
						htmlTemplate.addCssPath(cssPath);
						cssPath.setCssPath(cp.get("cssPath").toString());
						cssPath.setFieldName(cp.get("fieldName").toString());
						cssPath.setFieldDescription(cp.get("fieldDescription").toString());
						@SuppressWarnings("unchecked")
						List<Map<String, Object>> extractFunctions = (List<Map<String, Object>>) cp.get("extractFunctions");
						
						for (Map<String, Object> pf : extractFunctions) {
							ExtractFunction extractFunction = new ExtractFunction();
							cssPath.addExtractFunction(extractFunction);
							extractFunction.setExtractExpression(pf.get("extractExpression").toString());
							extractFunction.setFieldName(pf.get("fieldName").toString());
							extractFunction.setFieldDescription(pf.get("fieldDescription").toString());
						}

					}

				}

			}
		} catch (Exception e) {
			System.out.println("JSON抽取失败");
			e.printStackTrace();
		}
		return urlPatterns;
	}

}
