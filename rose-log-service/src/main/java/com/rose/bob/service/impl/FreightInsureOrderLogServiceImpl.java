package com.rose.bob.service.impl;

import com.rose.bob.dao.FreightInsureOrderLogMapper;
import com.rose.bob.domain.FreightInsureOrderLog;
import com.rose.bob.service.FreightInsureOrderLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FreightInsureOrderLogServiceImpl implements FreightInsureOrderLogService {

    @Resource
    private FreightInsureOrderLogMapper freightInsureOrderLogMapper;

    @Override
    public FreightInsureOrderLog selectByPrimaryKey(Integer id) {
        return freightInsureOrderLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public FreightInsureOrderLog copyByPrimaryKey(Integer id) {
        FreightInsureOrderLog freightInsureOrderLog = freightInsureOrderLogMapper.selectByPrimaryKey(id);
        freightInsureOrderLogMapper.insert(freightInsureOrderLog);
        return freightInsureOrderLog;
    }

    @Override
    public FreightInsureOrderLog insertByPrimaryKey(Integer id) {
        FreightInsureOrderLog freightInsureOrderLog = freightInsureOrderLogMapper.selectByPrimaryKey(id);
        freightInsureOrderLogMapper.insert(freightInsureOrderLog);
        return freightInsureOrderLog;
    }
}
