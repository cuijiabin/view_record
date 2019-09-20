package com.rose.bob.domain;

import lombok.Data;

import java.util.Date;

@Data
public class FreightInsureOrderLog {
    private Integer id;

    private Integer freightInsureOrderId;

    private Integer type;

    private String operator;

    private String description;

    private Integer createUserId;

    private Date createTime;

}