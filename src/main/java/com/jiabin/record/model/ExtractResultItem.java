package com.jiabin.record.model;

/**
 * 
 * @ClassName: ExtractResultItem 
 * @Description: 网页结构化信息抽取结果项
 * @author cuijiabin 
 * @date 2016年1月4日 下午5:23:24 
 *
 */
public class ExtractResultItem {
    /**
     * 抽取结果项保存到那个字段
     */
    private String field;
    /**
     * 抽取结果项的值
     */
    private String value;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ExtractResultItem [\nfield=" + field + ", \nvalue=" + value + "]";
    }
}
