package com.jiabin.record.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网页结构化信息抽取结果 
 * 一个网页模板对应一个抽取结果 
 * 如果一个网页有多个网页模板 
 * 每个模板都抽取成功 
 * 只要这些模板保存在不同的表中 
 * URL作为主键就不会冲突
 * 
 * @ClassName: ExtractResult 
 * @Description: 网页结构化信息抽取结果  
 * @author cuijiabin 
 * @date 2016年1月4日 下午5:22:53 
 *
 */
public class ExtractResult {
    /**
     * 网页对应的URL
     */
    private String url;
    /**
     * 网页原始内容
     */
    private byte[] content;
    /**
     * 网页编码
     */
    private String encoding;
    /**
     * 网页关键词元数据
     */
    private String keywords;
    /**
     * 网页描述元数据
     */
    private String description;
    /**
     * 网页提取出的文本存储到哪个表
     */
    private String tableName;
    /**
     * 一个网页可能有多个抽取结果项，至少要一个
     */
    private final Map<String, List<ExtractResultItem>> extractResultItems = new HashMap<>();
    /**
     * 抽取失败日志
     */
    private final List<ExtractFailLog> extractFailLogs = new ArrayList<>();

    public boolean isSuccess() {
        return extractFailLogs.isEmpty() && !extractResultItems.isEmpty();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, List<ExtractResultItem>> getExtractResultItems() {
        return extractResultItems;
    }

    public void addExtractResultItem(ExtractResultItem extractResultItem) {
        List<ExtractResultItem> list = extractResultItems.get(extractResultItem.getField());
        if(list == null){
            list = new ArrayList<>();
            extractResultItems.put(extractResultItem.getField(), list);
        }
        list.add(extractResultItem);
    }

    public List<ExtractFailLog> getExtractFailLogs() {
        return extractFailLogs;
    }

    public void addExtractFailLog(ExtractFailLog extractFailLog) {
        this.extractFailLogs.add(extractFailLog);
        extractFailLog.setExtractResult(this);
    }

    @Override
    public String toString() {
        return "ExtractResult [\nurl=" + url + ", \ntableName=" + tableName
                + ", \nextractResultItems=" + extractResultItems + ", \nextractFailLogs=" + extractFailLogs + "]";
    }
}
