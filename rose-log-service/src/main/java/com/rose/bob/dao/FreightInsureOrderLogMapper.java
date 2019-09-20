package com.rose.bob.dao;


import com.rose.bob.domain.FreightInsureOrderLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FreightInsureOrderLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FreightInsureOrderLog record);

    int insertSelective(FreightInsureOrderLog record);

    FreightInsureOrderLog selectByPrimaryKey(Integer id);

    int batchInsert(List<FreightInsureOrderLog> list);

    int countByInsureOrderIdAndType(@Param("insureOrderId") Integer insureOrderId, @Param("type") Integer type);
}