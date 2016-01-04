package com.jiabin.record.service;

import java.util.List;

import com.jiabin.record.model.ExtractResult;


/**
 * 网页抽取工具
 * 根据URL模式、页面模板、CSS路径、抽取函数，抽取HTML页面
 * 
 * @ClassName: HtmlExtractor 
 * @Description: 网页抽取工具
 * @author cuijiabin 
 * @date 2016年1月4日 下午5:26:19 
 *
 */
public interface HtmlExtractor {
    /**
     * 抽取信息
     * @param url URL
     * @param html HTML
     * @return 抽取结果
     */
    public List<ExtractResult> extract(String url, String html);
}
