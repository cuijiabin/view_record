package com.jiabin.record.model;


/**
 * 
 * @ClassName: ExtractFailLog 
 * @Description: 网页结构化信息抽取失败日志
 * @author cuijiabin 
 * @date 2016年1月4日 下午5:21:37 
 *
 */
public class ExtractFailLog {
    /**
     * 网页结构化信息抽取结果
     */
    private ExtractResult extractResult;
    /**
     * 网页的URL
     */
    private String url;
    /**
     * 网页的URL模式
     */
    private String urlPattern;
    /**
     * 网页模板
     */
    private String templateName;
    /**
     * CSS路径
     */
    private String cssPath;
    /**
     * CSS路径下的抽取函数
     */
    private String extractExpression;
    /**
     * 抽取出的内容保存到的表的名称
     */
    private String tableName;
    /**
     * 抽取出的内容保存到的字段名称
     */
    private String fieldName;
    /**
     * 抽取出的内容保存到的字段描述，仅作注释使用
     */
    private String fieldDescription;

    public ExtractResult getExtractResult() {
        return extractResult;
    }

    public void setExtractResult(ExtractResult extractResult) {
        this.extractResult = extractResult;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getCssPath() {
        return cssPath;
    }

    public void setCssPath(String cssPath) {
        this.cssPath = cssPath;
    }

    public String getExtractExpression() {
        return extractExpression;
    }

    public void setExtractExpression(String extractExpression) {
        this.extractExpression = extractExpression;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }

    public void setFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    @Override
    public String toString() {
        return "ExtractFailLog [\nurl=" + url + ", \nurlPattern=" + urlPattern
                + ", \ntemplateName=" + templateName + ", \ncssPath=" + cssPath
                + ", \nextractExpression=" + extractExpression + ", \ntableName="
                + tableName + ", \nfieldName=" + fieldName
                + ", \nfieldDescription=" + fieldDescription + "]";
    }
}
