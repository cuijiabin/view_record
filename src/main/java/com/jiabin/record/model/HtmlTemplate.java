package com.jiabin.record.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 网页模板 
 * 一个URL模式会有一到多个网页模板
 * 一套网页模板指定了如何精准地抽取网页信息
 * 
 * @ClassName: HtmlTemplate 
 * @Description: 网页模板  
 * @author cuijiabin 
 * @date 2016年1月4日 下午5:24:00 
 *
 */
public class HtmlTemplate {
    /**
     * 网页模板名称，仅仅注释作用
     */
    private String templateName;
    /**
     * 网页提取出的文本存储到哪个表
     */
    private String tableName;
    /**
     * URL模式
     */
    private UrlPattern urlPattern;
    /**
     * 多个CSS路径
     */
    private List<CssPath> cssPaths = new ArrayList<>();

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public UrlPattern getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(UrlPattern urlPattern) {
        this.urlPattern = urlPattern;
    }

    public List<CssPath> getCssPaths() {
        return cssPaths;
    }

    public void setCssPaths(List<CssPath> cssPaths) {
        this.cssPaths = cssPaths;
        for (CssPath cssPath : this.cssPaths) {
            cssPath.setPageTemplate(this);
        }
    }

    public boolean hasCssPath() {
        return !cssPaths.isEmpty();
    }

    public void addCssPath(CssPath cssPath) {
        cssPaths.add(cssPath);
        cssPath.setPageTemplate(this);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("网页模板：").append(this.templateName).append("，存储表：").append(this.tableName).append("\n\n");
        int i = 1;
        for (CssPath cssPath : cssPaths) {
            str.append(i++).append("、").append(cssPath.toString()).append("\n");
        }
        return str.toString();
    }
}
