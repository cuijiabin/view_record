package com.jiabin.record.model;


/**
 * 抽取函数
 * 抽取函数是页面模板的二级元素
 * 可以精准地控制抽取的内容
 * 
 * @ClassName: ExtractFunction 
 * @Description: 抽取函数 
 * @author cuijiabin 
 * @date 2016年1月4日 下午5:22:16 
 *
 */
public class ExtractFunction {
    /**
     * 抽取函数对应的CSS路径
     */
    private CssPath cssPath;
    /**
     * 抽取函数（只能使用系统内置支持的函数）
     */
    private String extractExpression;
    /**
     * 抽取函数提取出的文本存储到哪个字段
     */
    private String fieldName;
    /**
     * 抽取函数提取出的字段的中文含义，仅仅起注释作用，利于理解
     */
    private String fieldDescription;

    public CssPath getCssPath() {
        return cssPath;
    }

    public void setCssPath(CssPath cssPath) {
        this.cssPath = cssPath;
    }

    public String getExtractExpression() {
        return extractExpression;
    }

    public void setExtractExpression(String extractExpression) {
        this.extractExpression = extractExpression;
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
        StringBuilder str = new StringBuilder();
        str.append(this.extractExpression).append("\n");
        str.append(this.fieldName).append("\n");
        str.append(this.fieldDescription).append("\n");
        return str.toString();
    }
}
